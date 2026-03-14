package com.worksure.worksure.service;

import com.worksure.worksure.dto.JobRole;
import com.worksure.worksure.entity.JobExperience;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.repository.CertificateRepository;
import com.worksure.worksure.repository.JobExperienceRepository;
import com.worksure.worksure.repository.WorkerRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final WorkerRepository workerRepo;
    private final JobExperienceRepository jobExpRepo;
    private final CertificateRepository certRepo;
    private final ChatIntentResolver intentResolver;
    private final ChatSessionStore sessionStore;

    public ChatService(
            ChatClient.Builder builder,
            WorkerRepository workerRepo,
            JobExperienceRepository jobExpRepo,
            CertificateRepository certRepo,
            ChatIntentResolver intentResolver,
            ChatSessionStore sessionStore
    ) {
        this.chatClient = builder.build();
        this.workerRepo = workerRepo;
        this.jobExpRepo = jobExpRepo;
        this.certRepo = certRepo;
        this.intentResolver = intentResolver;
        this.sessionStore = sessionStore;
    }

    public String generateReply(String systemPrompt, String userMessage) {

        ChatSession session = sessionStore.getSession("default");

        // 1️ Handle thanks
        if (intentResolver.isThanks(userMessage)) {
            return "You’re welcome 😊";
        }

        // 2Detect job role anytime
        JobRole detectedRole = intentResolver.detectJobRole(userMessage);
        if (detectedRole != null) {
            session.setJobRole(detectedRole);
            session.setWaitingForLocation(true);
            session.setWaitingForOtherAreaConfirmation(false);
            session.setDbResultsShown(false);
            session.setNoWorkersFound(false);
            session.setLastWorker(null);

            return "Please tell me your location so I can find a "
                    + detectedRole.name().toLowerCase()
                    + " near you 😊";
        }

        //  User typed something without selecting a job role
        if (session.getJobRole() == null) {
            return "Please select a job type first 😊\n\n"
                    + "You can choose one of these services:\n"
                    + "⚡ Electrician\n"
                    + "🔧 Plumber\n"
                    + "🪚 Carpenter\n"
                    + "🎨 Painter\n"
                    + "🧱 Mason\n"
                    + "🔥 Welder\n"
                    + "🌬 HVAC\n"
                    + "🌿 Landscaper\n"
                    + "🏗 Contractor\n"
                    + "🧹 Cleaner\n"
                    + "🔧 Equipment Repair\n"
                    + "⚙ General Services";
        }

        //  YES after DB results shown
        if (session.isDbResultsShown()
                && "yes".equalsIgnoreCase(userMessage)) {

            session.setWaitingForLocation(true);
            session.setDbResultsShown(false);
            session.setWaitingForOtherAreaConfirmation(false);
            session.setNoWorkersFound(false);
            session.setLastWorker(null);

            return "Great 😊 Please enter another location.";
        }

        //  NO after DB results shown
        if (session.isDbResultsShown()
                && "no".equalsIgnoreCase(userMessage)) {
            session.setJobRole(null);
            session.setDbResultsShown(false);
            session.setLastWorker(null);

            return "Okay 😊 If you need another service, just tell me the job type.";
        }

        // 6️⃣ User types location directly after results shown
        if (session.isDbResultsShown()
                && !"yes".equalsIgnoreCase(userMessage)
                && !"no".equalsIgnoreCase(userMessage)) {

            session.setWaitingForLocation(true);
            session.setDbResultsShown(false);

            return generateReply(systemPrompt, userMessage);
        }

        // 7️⃣ Waiting for location → DB search
        if (session.isWaitingForLocation()) {

            String location = userMessage == null ? null : userMessage.trim();
            JobRole jobRole = session.getJobRole();

            List<Worker> workers =
                    workerRepo.searchByLocAndSkill(location, jobRole);

            // ❌ No workers found
            if (workers.isEmpty()) {
                session.setWaitingForLocation(false);
                session.setWaitingForOtherAreaConfirmation(true);
                session.setNoWorkersFound(true);

                return "Sorry 😔 We couldn’t find any "
                        + jobRole.name().toLowerCase()
                        + "s in " + location + ".\n\n"
                        + "Would you like me to check other areas for available "
                        + jobRole.name().toLowerCase()
                        + "s? (yes / no)";
            }

            // ✅ Workers found
            session.setWaitingForLocation(false);
            session.setDbResultsShown(true);
            session.setNoWorkersFound(false);
            session.setLastWorker(workers.get(0));

            return buildDbContext(workers, jobRole);
        }

        // 8️⃣ YES / NO when no workers found
        if (session.isWaitingForOtherAreaConfirmation()) {

            if ("yes".equalsIgnoreCase(userMessage)) {

                List<Worker> allWorkers =
                        workerRepo.searchByLocAndSkill(null, session.getJobRole());

                session.setWaitingForOtherAreaConfirmation(false);
                session.setDbResultsShown(true);
                session.setNoWorkersFound(false);

                if (allWorkers.isEmpty()) {
                    return "Sorry 😔 No workers are available at the moment.";
                }

                return buildDbContext(allWorkers, session.getJobRole());
            }

            if ("no".equalsIgnoreCase(userMessage)) {
                session.setWaitingForOtherAreaConfirmation(false);
                session.setWaitingForLocation(true);

                return "Okay 😊 Please enter another location.";
            }

            return "Please reply with **yes** or **no** 😊";
        }

        // 9️⃣ Clarification about last worker
        if (intentResolver.isClarificationQuestion(userMessage)
                && session.getLastWorker() != null) {

            Worker w = session.getLastWorker();

            return "Yes 😊 " + w.getFullName()
                    + " mainly works around "
                    + w.getPreferredServiceLocation()
                    + ". Would you like me to check another area?";
        }

        // 🔟 Safe fallback (never breaks UI)
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .call()
                .content();
    }

    // ================= DB RESULT FORMATTER =================

    private String buildDbContext(List<Worker> workers, JobRole jobRole) {

        StringBuilder sb = new StringBuilder();

        sb.append("😊 Here are available ")
                .append(jobRole.name().toLowerCase())
                .append("s on WorkSure:\n\n");

        for (Worker w : workers) {

            int years = jobExpRepo.findByWorkerId(w.getId())
                    .stream()
                    .mapToInt(JobExperience::getYears)
                    .sum();

            boolean certified = certRepo.existsByWorkerId(w.getId());

            sb.append("🔧 ").append(w.getFullName()).append("\n")
                    .append("📍 Location: ").append(w.getPreferredServiceLocation()).append("\n")
                    .append("💼 Experience: ")
                    .append(years == 0 ? "Newly registered" : years + " years").append("\n")
                    .append("📞 Contact: ").append(w.getPhoneNumber()).append("\n")
                    .append(certified ? "✅ Certified" : "❌ Not certified")
                    .append("\n\n");
        }

        sb.append("Let me know if you want to check another area or book a service 😊");

        return sb.toString();
    }
}

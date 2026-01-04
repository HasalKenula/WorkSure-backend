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

        if (intentResolver.isThanks(userMessage)) {
            return "You’re welcome 😊 If you need help finding a worker, just let me know.";
        }

        String sessionId = "default"; // demo
        ChatSession session = sessionStore.getSession(sessionId);

        /* 1️⃣ Detect service */
        JobRole detectedRole = intentResolver.detectJobRole(userMessage);

        if (detectedRole != null) {
            session.setJobRole(detectedRole);
            session.setWaitingForLocation(true);
            session.setDbResultsShown(false);
            session.setNoWorkersFound(false);
            session.setWaitingForOtherAreaConfirmation(false);
            session.setLastWorker(null);

            return "Please tell me your location so I can find a "
                    + detectedRole.name().toLowerCase()
                    + " near you 😊";
        }


        /* 2️⃣ YES → show other-area workers */
        if (intentResolver.isYes(userMessage)
                && session.isWaitingForOtherAreaConfirmation()
                && session.getJobRole() != null) {

            List<Worker> allWorkers =
                    workerRepo.findByJobRole(session.getJobRole());

            session.setWaitingForOtherAreaConfirmation(false);
            session.setDbResultsShown(true);
            session.setLastWorker(allWorkers.get(0));

            String dbContext =
                    buildDbContext(allWorkers, session.getJobRole());

            return dbContext;
        }

        /* 3️⃣ Clarification about last worker */
        if (intentResolver.isClarificationQuestion(userMessage)
                && session.getLastWorker() != null) {

            Worker w = session.getLastWorker();

            return "Yes 😊 " + w.getFullName()
                    + " mainly provides "
                    + w.getJobRole().name().toLowerCase()
                    + " services around "
                    + w.getPreferredServiceLocation()
                    + ". Would you like me to check another area?";
        }

        /* 4️⃣ Waiting for location */
        if (session.isWaitingForLocation()) {

            String location = userMessage.trim();
            JobRole jobRole = session.getJobRole();

            List<Worker> workersInArea =
                    workerRepo.findByJobRoleAndPreferredServiceLocationIgnoreCase(
                            jobRole, location
                    );

            if (!workersInArea.isEmpty()) {

                session.setWaitingForLocation(false);
                session.setDbResultsShown(true);
                session.setLastWorker(workersInArea.get(0));

                return buildDbContext(workersInArea, jobRole);
            }

            List<Worker> allWorkers = workerRepo.findByJobRole(jobRole);

            if (!allWorkers.isEmpty()) {

                session.setWaitingForLocation(false);
                session.setWaitingForOtherAreaConfirmation(true);

                return "😔 I couldn’t find any "
                        + jobRole.name().toLowerCase()
                        + "s in " + location + ".\n\n"
                        + "However, we do have "
                        + jobRole.name().toLowerCase()
                        + "s registered in other areas.\n\n"
                        + "Would you like me to show them?";
            }

            session.setWaitingForLocation(false);
            session.setNoWorkersFound(true);

            return "Sorry 😔 At the moment, we don’t have any "
                    + jobRole.name().toLowerCase()
                    + "s registered on WorkSure.";
        }

        /* 5️⃣ No workers at all → AI guidance */
        if (session.isNoWorkersFound()) {
            return chatClient.prompt()
                    .system(systemPrompt)
                    .system("""
                            CONTEXT:
                            - No workers are registered for this service.
                            - Be empathetic and honest.
                            - Do NOT invent workers.
                            - Suggest trying later or nearby areas.
                            """)
                    .user(userMessage)
                    .call()
                    .content();
        }

        /* 6️⃣ Fallback */
        return "How can I help you today? 😊";
    }

    private String buildDbContext(List<Worker> workers, JobRole jobRole) {

        StringBuilder sb = new StringBuilder();

        sb.append("😊 Here are the ")
                .append(jobRole.name().toLowerCase())
                .append("s currently available on WorkSure:\n\n");

        for (Worker w : workers) {

            int years = jobExpRepo.findByWorkerId(w.getId())
                    .stream()
                    .mapToInt(JobExperience::getYears)
                    .sum();

            boolean certified = certRepo.existsByWorkerId(w.getId());

            sb.append("🔧 ").append(w.getFullName()).append("\n")
                    .append("📍 Location: ").append(w.getPreferredServiceLocation()).append("\n")
                    .append("⏰ Available: ")
                    .append(w.getPreferredStartTime()).append(" – ")
                    .append(w.getPreferredEndTime()).append("\n")
                    .append("💼 Experience: ")
                    .append(years == 0 ? "Newly registered" : years + " years").append("\n")
                    .append("📞 Contact: ").append(w.getPhoneNumber()).append("\n")
                    .append(certified ? "✅ Certified" : "❌ Not certified")
                    .append("\n\n");
        }

        sb.append("Let me know if you’d like help booking a service or checking another area 😊");

        return sb.toString();
    }
}
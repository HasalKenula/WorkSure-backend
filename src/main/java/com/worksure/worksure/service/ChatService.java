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
            return "You’re welcome 😊";
        }

        ChatSession session = sessionStore.getSession("default");


        JobRole detectedRole = intentResolver.detectJobRole(userMessage);

        if (detectedRole != null) {
            session.setJobRole(detectedRole);
            session.setWaitingForLocation(true);
            session.setLastWorker(null);

            return "Please tell me your location so I can find a "
                    + detectedRole.name().toLowerCase()
                    + " near you 😊";
        }

        /* 2️⃣ Waiting for location → FETCH FROM DB */
        if (session.isWaitingForLocation() && session.getJobRole() != null) {

            String location = (userMessage == null || userMessage.trim().isEmpty())
                    ? null
                    : userMessage.trim();

            JobRole jobRole = session.getJobRole();


            List<Worker> workers =
                    workerRepo.searchByLocAndSkill(location, jobRole);


            if (workers.isEmpty()) {
                workers = workerRepo.searchByLocAndSkill(null, jobRole);
            }

            if (!workers.isEmpty()) {
                session.setWaitingForLocation(false);
                session.setLastWorker(workers.get(0));
                return buildDbContext(workers, jobRole);
            }

            return "Sorry 😔 We don’t have any "
                    + jobRole.name().toLowerCase()
                    + "s registered at the moment.";
        }


        if (intentResolver.isClarificationQuestion(userMessage)
                && session.getLastWorker() != null) {

            Worker w = session.getLastWorker();

            return "Yes 😊 " + w.getFullName()
                    + " mainly works around "
                    + w.getPreferredServiceLocation()
                    + ". Would you like me to check another area?";
        }


        return chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .call()
                .content();
    }


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

package com.worksure.worksure.service;

import com.worksure.worksure.dto.JobRole;
import org.springframework.stereotype.Component;

@Component
public class ChatIntentResolver {

    public JobRole detectJobRole(String message) {
        String text = message.toLowerCase();

        if (text.contains("plumber")) return JobRole.PLUMBER;
        if (text.contains("electrician")) return JobRole.ELECTRICIAN;
        if (text.contains("carpenter")) return JobRole.CARPENTER;
        if (text.contains("painter")) return JobRole.PAINTER;
        if (text.contains("cleaner")) return JobRole.CLEANER;

        return null;
    }

    public boolean isClarificationQuestion(String message) {
        String text = message.toLowerCase();

        return text.contains("only")
                || text.contains("near")
                || text.contains("area")
                || text.contains("this worker")
                || text.contains("available");
    }

    public boolean isYes(String message) {
        return message.equalsIgnoreCase("yes")
                || message.equalsIgnoreCase("ok")
                || message.equalsIgnoreCase("okay")
                || message.equalsIgnoreCase("sure");
    }

    public boolean isThanks(String message) {
        String text = message.toLowerCase();
        return text.equals("thanks")
                || text.equals("thank you")
                || text.equals("tq")
                || text.equals("thx");
    }

}

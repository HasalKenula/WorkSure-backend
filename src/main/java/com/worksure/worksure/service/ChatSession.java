package com.worksure.worksure.service;

import com.worksure.worksure.dto.JobRole;
import com.worksure.worksure.entity.Worker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSession {

    private JobRole jobRole;

    private boolean waitingForLocation;

    private boolean waitingForOtherAreaConfirmation;

    private boolean dbResultsShown;

    private boolean noWorkersFound;

    private Worker lastWorker;
}

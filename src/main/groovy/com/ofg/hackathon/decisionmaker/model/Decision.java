package com.ofg.hackathon.decisionmaker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Decision {

    @Id
    private Long applicationId;

    private boolean allowed;

    public Decision() {
    }

    public Decision(Long applicationId, boolean allowed) {
        this.applicationId = applicationId;
        this.allowed = allowed;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}

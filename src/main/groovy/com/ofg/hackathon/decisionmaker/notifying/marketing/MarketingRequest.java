package com.ofg.hackathon.decisionmaker.notifying.marketing;

public class MarketingRequest {

    private Person person;

    private String decision;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}

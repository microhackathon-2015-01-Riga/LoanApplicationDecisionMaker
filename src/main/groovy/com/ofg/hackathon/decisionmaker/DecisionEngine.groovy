package com.ofg.hackathon.decisionmaker

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DecisionEngine {

    @Autowired
    DecisionStorage decisionStorage

    @Async
    void process(Long applicationId, LoanApplication application) {
        decisionStorage.storeDecision(new Decision(applicationId, true))
    }
    
    Decision getDecision(Long applicationId){
        decisionStorage.getDecision(applicationId)
    }
}

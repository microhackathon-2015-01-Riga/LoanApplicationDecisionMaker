package com.ofg.hackathon.decisionmaker

import com.google.common.base.Optional
import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.hackathon.decisionmaker.notifying.DecisionNotifier
import org.apache.commons.lang.math.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DecisionEngine {

    @Autowired
    DecisionStorage decisionStorage

    @Autowired
    DecisionNotifier decisionNotifier

    @Async
    void process(Long applicationId, LoanApplication application) {
        def decision = decision(applicationId, application)
        decisionStorage.storeDecision(decision)
        decisionNotifier.notifyServices(application, decision)
    }

    private Decision decision(Long applicationId, LoanApplication application) {
        def randomInt = RandomUtils.nextInt(1)
        new Decision(applicationId, randomInt != 0)
    }

    Optional<Decision> getDecision(Long applicationId) {
        decisionStorage.getDecision(applicationId)
    }
}

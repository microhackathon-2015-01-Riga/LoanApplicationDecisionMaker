package com.ofg.hackathon.decisionmaker
import com.codahale.metrics.Counter
import com.codahale.metrics.MetricRegistry
import com.google.common.base.Optional
import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.hackathon.decisionmaker.notifying.DecisionNotifier
import org.apache.commons.lang.math.RandomUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class DecisionEngine {

    @Autowired
    DecisionStorage decisionStorage

    @Autowired
    DecisionNotifier decisionNotifier

    @Autowired
    MetricRegistry metricRegistry

    Counter grantedCounter

    Counter rejectedCounter

    @PostConstruct
    void init() {
        grantedCounter = metricRegistry.counter('decision-granted')
        rejectedCounter = metricRegistry.counter('decision-rejected')
    }

    void process(Long applicationId, LoanApplication application) {
        def decision = decision(applicationId, application)

        if (decision.allowed) {
            grantedCounter.inc()
        } else {
            rejectedCounter.inc()
        }

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

package com.ofg.hackathon.decisionmaker.notifying

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DecisionNotifier {

    @Autowired
    List<AbstractNotifier> notifiers

    void notifyServices(LoanApplication application, Decision decision) {
        notifiers.each { n -> n.notifyService(application, decision) }
    }
}

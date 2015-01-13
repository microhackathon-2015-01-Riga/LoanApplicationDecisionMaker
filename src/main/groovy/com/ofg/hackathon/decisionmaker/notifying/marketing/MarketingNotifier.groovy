package com.ofg.hackathon.decisionmaker.notifying.marketing

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.hackathon.decisionmaker.notifying.AbstractNotifier
import org.springframework.stereotype.Component

@Component
class MarketingNotifier extends AbstractNotifier {

    @Override
    void notifyService(LoanApplication application, Decision decision) {
        callService('marketing-offer-generator', "/api/marketing/$decision.applicationId", 'put',
                new MarketingRequest(person: new Person(firstName: application.firstName, lastName: application.lastName), decision: decision.allowed ? 'granted' : 'rejected'));
    }
}

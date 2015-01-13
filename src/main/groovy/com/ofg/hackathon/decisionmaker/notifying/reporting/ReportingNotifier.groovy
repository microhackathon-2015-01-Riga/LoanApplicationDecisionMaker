package com.ofg.hackathon.decisionmaker.notifying.reporting

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.hackathon.decisionmaker.notifying.AbstractNotifier
import org.springframework.stereotype.Component

@Component
public class ReportingNotifier extends AbstractNotifier {

    @Override
    public void notifyService(LoanApplication application, Decision decision) {
        callService('reporting-service', '/api/reporting', 'post', new ReportingRequest(loanId: decision.applicationId,
                job: application.job, amount: application.amount, fraudStatus: application.fraudStatus, decision: decision.allowed ? 'granted' : 'rejected'));
    }
}

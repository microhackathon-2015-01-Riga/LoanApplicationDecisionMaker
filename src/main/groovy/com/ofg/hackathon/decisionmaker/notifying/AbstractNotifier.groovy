package com.ofg.hackathon.decisionmaker.notifying

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.ofg.infrastructure.web.resttemplate.fluent.common.request.HttpMethod
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractNotifier {

    @Autowired
    ServiceRestClient serviceRestClient

    abstract void notifyService(LoanApplication application, Decision decision);

    void callService(String service, String relativeUrl, String method, Object body) {

        HttpMethod httpMethod

        if (method == 'put') {
            httpMethod = serviceRestClient.forService(service).put()
        } else if (method == 'post') {
            httpMethod = serviceRestClient.forService(service).post()
        }

        httpMethod.onUrl(relativeUrl).
                body(body).
                withHeaders().
                contentTypeJson().
                andExecuteFor().
                anObject().
                ofType(String)
    }
}

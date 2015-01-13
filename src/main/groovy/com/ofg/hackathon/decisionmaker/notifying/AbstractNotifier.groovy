package com.ofg.hackathon.decisionmaker.notifying
import com.nurkiewicz.asyncretry.AsyncRetryExecutor
import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.ofg.infrastructure.web.resttemplate.fluent.common.request.HttpMethod
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import java.util.concurrent.ScheduledThreadPoolExecutor

@Slf4j
abstract class AbstractNotifier {

    @Autowired
    ServiceRestClient serviceRestClient

    abstract void notifyService(LoanApplication application, Decision decision);

    void callService(String service, String relativeUrl, String method, Object body) {
        try {
            HttpMethod httpMethod

            def asyncRetryExecutor = new AsyncRetryExecutor(new ScheduledThreadPoolExecutor(1)).withMaxRetries(3).withMinDelay(1000)
            def serviceBuilder = serviceRestClient.forService(service).retryUsing(asyncRetryExecutor)
            if (method == 'put') {
                httpMethod = serviceBuilder.put()
            } else if (method == 'post') {
                httpMethod = serviceBuilder.post()
            }

            httpMethod.onUrl(relativeUrl).
                    body(body).
                    withHeaders().
                    contentTypeJson().
                    andExecuteFor().
                    ignoringResponse()
        } catch (Exception e) {
            log.warn('Exception caught: ', e)
        }
    }
}

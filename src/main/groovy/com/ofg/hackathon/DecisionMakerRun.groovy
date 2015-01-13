package com.ofg.hackathon
import com.ofg.infrastructure.discovery.EnableServiceDiscovery
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier
import com.ofg.infrastructure.metrics.config.EnableMetrics
import com.ofg.infrastructure.web.resttemplate.fluent.EnableServiceRestClient
import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

import static com.ofg.config.BasicProfiles.*

@TypeChecked
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableServiceDiscovery
@EnableMetrics
@EnableServiceRestClient
class DecisionMakerRun {

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(DecisionMakerRun)
        application.addListeners(new EnvironmentSetupVerifier([DEVELOPMENT, PRODUCTION, TEST]))
        application.run(args)
    }
}

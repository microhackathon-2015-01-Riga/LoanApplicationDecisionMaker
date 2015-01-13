package com.ofg.hackathon

import com.ofg.infrastructure.discovery.EnableServiceDiscovery
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier
import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

import static com.ofg.config.BasicProfiles.*

@TypeChecked
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableServiceDiscovery
class DecisionMakerRun {

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(DecisionMakerRun)
        application.addListeners(new EnvironmentSetupVerifier([DEVELOPMENT, PRODUCTION, TEST]))
        application.run(args)
    }
}

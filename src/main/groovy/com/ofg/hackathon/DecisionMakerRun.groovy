package com.ofg.hackathon

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet
import com.ofg.infrastructure.discovery.EnableServiceDiscovery
import com.ofg.infrastructure.environment.EnvironmentSetupVerifier
import com.ofg.infrastructure.metrics.config.EnableMetrics
import com.ofg.infrastructure.web.correlationid.EnableCorrelationId
import com.ofg.infrastructure.web.resttemplate.fluent.EnableServiceRestClient
import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy

import static com.ofg.config.BasicProfiles.*

@TypeChecked
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableServiceDiscovery
@EnableMetrics
@EnableServiceRestClient
@EnableCorrelationId
class DecisionMakerRun {

    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystryx.stream");
    }

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(DecisionMakerRun)
        application.addListeners(new EnvironmentSetupVerifier([DEVELOPMENT, PRODUCTION, TEST]))
        application.run(args)
    }
}

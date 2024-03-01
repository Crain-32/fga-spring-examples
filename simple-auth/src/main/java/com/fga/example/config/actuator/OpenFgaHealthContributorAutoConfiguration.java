package com.fga.example.config.actuator;

import dev.openfga.sdk.api.client.OpenFgaClient;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration // should be Auto configuration
@ConditionalOnClass(OpenFgaClient.class)
@ConditionalOnBean(OpenFgaClient.class)
@ConditionalOnEnabledHealthIndicator("openFga")
public class OpenFgaHealthContributorAutoConfiguration extends CompositeHealthContributorConfiguration<OpenFgaHealthIndicator, OpenFgaClient> {

    OpenFgaHealthContributorAutoConfiguration() {
        super(OpenFgaHealthIndicator::new);
    }

    @Bean
    @ConditionalOnMissingBean(name = {"openFgaHealthIndicator", "openFgaHealthContributor"})
    public HealthContributor openFgaHealthContributor(Map<String, OpenFgaClient> openFgaClient) {
        return createContributor(openFgaClient);
    }
}

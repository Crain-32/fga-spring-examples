package com.fga.example.config.actuator;

import dev.openfga.sdk.api.client.OpenFgaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

public class OpenFgaHealthIndicator extends AbstractHealthIndicator {

    Logger logger = LoggerFactory.getLogger(OpenFgaHealthIndicator.class);
    private final OpenFgaClient openFgaClient;

    public OpenFgaHealthIndicator(OpenFgaClient openFgaClient) {
        super("OpenFga Server health check failed");
        Assert.notNull(openFgaClient, "OpenFgaClient must not be null");
        this.openFgaClient = openFgaClient;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        // Assume if we can hit the Server, that we're Healthy
        try {
            var storeInfo = openFgaClient.getStore().get();
            builder.withDetail("store_name", storeInfo.getName());
            builder.withDetail("store_id", storeInfo.getId());
            builder.up();
        } catch (Exception e) {
            logger.error("Failed to connect to OpenFGA", e);
            builder.down(e);
        }
    }
}

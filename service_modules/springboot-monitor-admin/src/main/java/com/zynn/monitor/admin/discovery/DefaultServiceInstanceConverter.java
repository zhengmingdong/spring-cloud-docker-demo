package com.zynn.monitor.admin.discovery;

import java.net.URI;
import java.util.Map;

import de.codecentric.boot.admin.server.domain.values.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.util.StringUtils.isEmpty;

public class DefaultServiceInstanceConverter implements ServiceInstanceConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceInstanceConverter.class);
    private static final String KEY_MANAGEMENT_PORT = "management.port";
    private static final String KEY_MANAGEMENT_PATH = "management.context-path";
    private static final String KEY_HEALTH_PATH = "health.path";

    /**
     * Default context-path to be appended to the url of the discovered service for the
     * managment-url.
     */
    private String managementContextPath = "/actuator";
    /**
     * Default path of the health-endpoint to be used for the health-url of the discovered service.
     */
    private String healthEndpointPath = "health";

    @Override
    public Registration convert(ServiceInstance instance) {
        LOGGER.debug("Converting service '{}' running at '{}' with metadata {}", instance.getServiceId(),
                instance.getUri(), instance.getMetadata());

        Registration.Builder builder = Registration.create(instance.getServiceId(), getHealthUrl(instance).toString());

        URI managementUrl = getManagementUrl(instance);
        if (managementUrl != null) {
            builder.managementUrl(managementUrl.toString());
        }

        URI serviceUrl = getServiceUrl(instance);
        if (serviceUrl != null) {
            builder.serviceUrl(serviceUrl.toString());
        }

        Map<String, String> metadata = getMetadata(instance);
        if (metadata != null) {
            builder.metadata(metadata);
        }

        return builder.build();
    }

    protected URI getHealthUrl(ServiceInstance instance) {
        String healthPath = instance.getMetadata().get(KEY_HEALTH_PATH);
        if (isEmpty(healthPath)) {
            healthPath = healthEndpointPath;
        }

        return UriComponentsBuilder.fromUri(getManagementUrl(instance)).path("/").path(healthPath).build().toUri();
    }

    protected URI getManagementUrl(ServiceInstance instance) {
        String managamentPath = instance.getMetadata().get(KEY_MANAGEMENT_PATH);
        if (isEmpty(managamentPath)) {
            managamentPath = managementContextPath;
        }

        URI serviceUrl = getServiceUrl(instance);
        String managamentPort = instance.getMetadata().get(KEY_MANAGEMENT_PORT);
        if (isEmpty(managamentPort)) {
            managamentPort = String.valueOf(serviceUrl.getPort());
        }

        return UriComponentsBuilder.fromUri(serviceUrl)
                .port(managamentPort)
                .path("/")
                .path(managamentPath)
                .build()
                .toUri();
    }

    protected URI getServiceUrl(ServiceInstance instance) {
        return UriComponentsBuilder.fromUri(instance.getUri()).path("/").build().toUri();
    }

    protected Map<String, String> getMetadata(ServiceInstance instance) {
        return instance.getMetadata();
    }


    public void setManagementContextPath(String managementContextPath) {
        this.managementContextPath = managementContextPath;
    }

    public String getManagementContextPath() {
        return managementContextPath;
    }

    public void setHealthEndpointPath(String healthEndpointPath) {
        this.healthEndpointPath = healthEndpointPath;
    }

    public String getHealthEndpointPath() {
        return healthEndpointPath;
    }
}

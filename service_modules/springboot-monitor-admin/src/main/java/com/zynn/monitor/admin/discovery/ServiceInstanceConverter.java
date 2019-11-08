package com.zynn.monitor.admin.discovery;

import de.codecentric.boot.admin.server.domain.values.Registration;
import org.springframework.cloud.client.ServiceInstance;


public interface ServiceInstanceConverter {

    /**
     * Converts a service instance to a application instance to be registered.
     *
     * @param instance the service instance.
     * @return Instance
     */
    Registration convert(ServiceInstance instance);
}
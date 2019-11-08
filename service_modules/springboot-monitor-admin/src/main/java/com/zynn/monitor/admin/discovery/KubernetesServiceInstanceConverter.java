package com.zynn.monitor.admin.discovery;

import java.net.URI;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.discovery.KubernetesServiceInstance;
import org.springframework.util.Assert;


public class KubernetesServiceInstanceConverter extends DefaultServiceInstanceConverter {

    @Override
    protected URI getHealthUrl(ServiceInstance instance) {
        Assert.isInstanceOf(KubernetesServiceInstance.class,
                instance,
                "serviceInstance must be of type KubernetesServiceInstance");
        return ((KubernetesServiceInstance) instance).getUri();
    }
}
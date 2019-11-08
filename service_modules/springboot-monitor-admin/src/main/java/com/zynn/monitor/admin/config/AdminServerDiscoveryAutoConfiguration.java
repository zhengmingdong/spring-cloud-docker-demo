package com.zynn.monitor.admin.config;

import com.zynn.monitor.admin.discovery.InstanceDiscoveryListener;
import com.zynn.monitor.admin.discovery.KubernetesServiceInstanceConverter;
import com.zynn.monitor.admin.discovery.ServiceInstanceConverter;
import io.fabric8.kubernetes.client.KubernetesClient;
import de.codecentric.boot.admin.server.config.AdminServerAutoConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerMarkerConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnSingleCandidate(DiscoveryClient.class)
@ConditionalOnBean(AdminServerMarkerConfiguration.Marker.class)
@ConditionalOnProperty(prefix = "spring.boot.admin.discovery", name = "enabled", matchIfMissing = false)
@AutoConfigureAfter(value = AdminServerAutoConfiguration.class, name = {
        "org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClientAutoConfiguration",
        "org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration"})
public class AdminServerDiscoveryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "spring.boot.admin.discovery")
    public InstanceDiscoveryListener instanceDiscoveryListener(ServiceInstanceConverter serviceInstanceConverter,
                                                               DiscoveryClient discoveryClient,
                                                               InstanceRegistry registry,
                                                               InstanceRepository repository) {
        InstanceDiscoveryListener listener = new InstanceDiscoveryListener(discoveryClient, registry, repository);
        listener.setConverter(serviceInstanceConverter);
        return listener;
    }

    @Configuration
    @ConditionalOnMissingBean({ServiceInstanceConverter.class})
    @ConditionalOnBean(KubernetesClient.class)
    public static class KubernetesConverterConfiguration {
        @Bean
        @ConfigurationProperties(prefix = "spring.boot.admin.discovery.converter")
        public KubernetesServiceInstanceConverter serviceInstanceConverter() {
            return new KubernetesServiceInstanceConverter();
        }
    }

    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");
            successHandler.setDefaultTargetUrl(adminContextPath + "/");

            http.authorizeRequests()
                    .antMatchers(adminContextPath + "/assets/**").permitAll()
                    .antMatchers(adminContextPath + "/login").permitAll()
                    .antMatchers(adminContextPath + "/actuator/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                    .logout().logoutUrl(adminContextPath + "/logout").and()
                    .httpBasic().and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers(
                            adminContextPath + "/instances",
                            adminContextPath + "/actuator/**"
                    );
        }
    }

}

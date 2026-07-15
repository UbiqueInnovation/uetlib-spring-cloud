package ch.ubique.backend.cloud.feign.autoconfigure;

import ch.ubique.backend.cloud.feign.ExtendedFeignObservationConvention;

import feign.micrometer.MicrometerObservationCapability;

import io.micrometer.observation.ObservationRegistry;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = ObservationAutoConfiguration.class)
public class UetlibSpringCloudFeignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ObservationRegistry.class)
    MicrometerObservationCapability micrometerObservationCapability(
            final ObservationRegistry observationRegistry) {
        return new MicrometerObservationCapability(
                observationRegistry, new ExtendedFeignObservationConvention());
    }
}

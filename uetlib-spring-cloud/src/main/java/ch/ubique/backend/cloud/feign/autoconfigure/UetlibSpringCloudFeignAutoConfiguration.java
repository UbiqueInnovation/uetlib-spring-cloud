package ch.ubique.backend.cloud.feign.autoconfigure;

import ch.ubique.backend.cloud.feign.CustomNamePrefixFeignObservationConvention;

import feign.micrometer.MicrometerObservationCapability;

import io.micrometer.observation.ObservationRegistry;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class UetlibSpringCloudFeignAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ObservationRegistry.class)
    MicrometerObservationCapability micrometerObservationCapability(
            final ObservationRegistry observationRegistry) {
        return new MicrometerObservationCapability(
                observationRegistry, new CustomNamePrefixFeignObservationConvention());
    }
}

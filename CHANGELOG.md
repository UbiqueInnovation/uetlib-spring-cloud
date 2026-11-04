# CHANGELOG

## 3.0.0 (unreleased)

- Align Feign and security integration packages under `ch.ubique.uetlib.spring.cloud.*`.

### Breaking changes

- Move Java packages from `ch.ubique.backend` to `ch.ubique.uetlib`, including class-name resources. Existing Spring configuration property keys are unchanged.
- Remove the inherited Ubique Artifactory repository and deployment destination; private consumers must configure their own registry.
- Add Apache-2.0 licensing for original code, preserve third-party licenses, and add public project metadata and contribution/security guidance.

- Move Maven coordinates from `ch.ubique:uetlib-*` to `ch.uetlib:uetlib-*`.
- Align uetlib parent, BOM and library references with the migration releases.

### Migration

After the migration releases and `uetlib-rewrite:0.1.0` are published, run this from the consuming project, then review the diff and run its tests:

```sh
mvn org.openrewrite.maven:rewrite-maven-plugin:6.46.1:run \
  -Drewrite.recipeArtifactCoordinates=ch.uetlib:uetlib-rewrite:0.1.0 \
  -Drewrite.activeRecipes=ch.ubique.uetlib.MigrateToPublicUetlib
```

The recipe updates Maven coordinates/versions, Java references and known class-name resources. Review application-owned packages under the old namespace, Kotlin, reflection patterns, native-access module flags and persisted class names separately. Use `:dryRun` instead of `:run` to preview a patch.


## 2.4.1

- latest uetlib parent
- version updates

## 2.4.0

- Add `org.zalando:logbook-openfeign` to `uetlib-spring-cloud`
- update actions
- update uetlib parent and versions
- Java 25

## 2.3.2

- updated actions to not run tests twice for sonar
- update uetlib version

## 2.3.1

### Bugfixes

- AutoConfiguration class moved to correct place in resources dir (`UetlibSpringCloudAutoConfiguration` was completely
  ignored)

### Improvements

- uetlib and actions version updates

## 2.3.0

### Improvements

- Null-marked it all
- Update `uetlib-spring-boot/-geo/-security` to `2.2.0`

## 2.2.1

### Improvements

- Fix `FeignExceptionUtils` to use Jackson 3

## 2.2.0

- Update `uetlib-spring-boot/-security` to `2.1.0`

## 2.1.0

- Add new `ExtendedFeignObservationConvention` that is auotconfigured int `UetlibSpringCloudFeignAutoConfiguration`.
  With this change, all http client metrics from feign client have the new prfix `feign.http.client.requests` instead of
  just `http.client.requests` in prometheus which conflicts with Spring Boot defaults.

## 2.0.0

### Improvements

- Upgrade to Spring Boot 4.0
- Remove deprecated `RetryUpstreamFailureErrorDecoder`, to be replaced with `RetryOnStatusErrorDecoder`

## 1.2.1

### Improvements

- Latest uetlib dependencies with Spring Boot 3.5 support

## 1.2.0

- Upgrade Uetlib Parent to 1.2.0

## 1.1.1

- Include feign-hc5 so Spring Boot Openfeign use the Apache HttpClient5 instead of HTTPUrlConnection vy default

## 1.1.0

- Spring Boot 3.4
- Spring Cloud 2024.0

## 1.0.0

First release. The code was moved
from [UbiqueInnovation/backend-jvm-lib](https://github.com/UbiqueInnovation/backend-jvm-lib).


# CHANGELOG

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


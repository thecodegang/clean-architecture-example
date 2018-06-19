[![Build Status](https://travis-ci.org/aantoniadis/clean-architecture-example.svg?branch=master)](https://travis-ci.org/aantoniadis/clean-architecture-example)

# Compile and run the app

`./mvnw package && ./mvnw spring-boot:run -pl delivery`

or

`./mvnw package && java -jar delivery/target/delivery-1.0.0-SNAPSHOT.jar`

# Description of the architecture

The project consists of 4 modules `core`, `usecases`, `dataproviders`, and
`delivery`.

## `core` module

This module contains the domain entities. There are no dependencies to frameworks and/or libraries.

## `usecases` module

This module contains the business rules that are essential for our application
(Application business rules). The only dependency of this module is to `core`.
In this module, gateways for the repositories are being defined. Each use case
defines the interface of the gateway that is required following the
[ISP](https://en.wikipedia.org/wiki/Interface_segregation_principle). These
gateways, operate on the domain entities defined in `core`.

In this module,
[`UseCase`](https://github.com/aantoniadis/clean-architecture-example/blob/master/usecases/src/main/kotlin/com/github/aantoniadis/delivery/usecases/core/UseCase.kt)
and
[`UseCaseExecutor`](https://github.com/aantoniadis/clean-architecture-example/blob/master/usecases/src/main/kotlin/com/github/aantoniadis/delivery/usecases/core/UseCase.kt)
are also defined. The `UseCase` is an interface similar to the
`java.util.Function`. It just gets a request and returns a response.

The `UseCaseExecutor` handles the execution of a `UseCase`. To do so, it has an
`invoke` method that takes the following arguments:
1. the `UseCase` that is to be executed
2. the `RequestDto`
3. a _mapper function_ that converts the `RequestDto` to a `Request` object (the input of the use case)
4. a _mapper function_ that converts the `Response` object (the output of the use case) of the use case execution to a `ResponseDto`

There are 3 more overloaded versions of the `invoke` method, which omit the input
and/or the output of the `UseCaseExecutor`.

Currently, the `UseCaseExecutor` implementation
([`UseCaseExecutorImp`](https://github.com/aantoniadis/clean-architecture-example/blob/master/usecases/src/main/kotlin/com/github/aantoniadis/delivery/usecases/core/UseCase.kt))
is using `java.util.concurrent.CompletableFuture` and
`java.util.concurrent.CompletionStage` for the execution abstraction. These
abstractions are convenient as they can perform asynchronous executions and also
have out of the box compatibility with most frameworks.

## `dataproviders` module

This module contains the implementation of the gateways defined in the
`usecases` module. This module depends on the framework that facilitates the
data access. In our example, we use JPA and Spring Data. The `Jpa*Repository`
classes are the actual implementation of the gateways defined in the `usecases`
module.

These repositories, use the Spring Data `JpaRepository` as dependencies.
For more, check
[JpaProductRepository.kt](https://github.com/aantoniadis/clean-architecture-example/blob/master/dataproviders/src/main/kotlin/com/github/aantoniadis/dataproviders/db/jpa/repositories/JpaProductRepository.kt).
The entities in this module, are JPA entities, so mappings to and from these and
the domain entities are needed. Check
[ProductEntity.kt](https://github.com/aantoniadis/clean-architecture-example/blob/master/dataproviders/src/main/kotlin/com/github/aantoniadis/dataproviders/db/jpa/entities/ProductEntity.kt)
for more.

## `delivery` module

This module contains all the details of the delivery mechanism that we use along
with the wiring of the app and the configurations. In our example, we use rest services
built with Spring Boot. Similarly, to the JPA entities of the
`dataproviders` module, the DTOs have mappers, to convert from and to the domain
entities.

A rest controller gets the RequestDto, and forwards it to the related
use case through the `UseCaseExecutor`. The response of the use case (which is a ResponseDto) is the response of the
controller's method that implements the endpoint. An example of such usage is
[ProductResourceImp.kt](https://github.com/aantoniadis/clean-architecture-example/blob/master/delivery/src/main/kotlin/com/github/aantoniadis/delivery/rest/imp/ProductResourceImp.kt).
The exceptions are handled by
[GlobalExceptionHandler.kt](https://github.com/aantoniadis/clean-architecture-example/blob/master/delivery/src/main/kotlin/com/github/aantoniadis/delivery/rest/imp/GlobalExceptionHandler.kt),
and they are converted to
[ErrorDto](https://github.com/aantoniadis/clean-architecture-example/blob/master/delivery/src/main/kotlin/com/github/aantoniadis/delivery/rest/api/ErrorDto.kt).

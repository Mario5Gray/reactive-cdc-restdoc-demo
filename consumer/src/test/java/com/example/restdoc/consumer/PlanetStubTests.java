package com.example.restdoc.consumer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = "com.example.restdoc:producer:+:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class PlanetStubTests {

    WebClient webclient = WebClient.builder().build();

    @Test
    void shouldGetAllPlanets() {
        Mono<Collection<Planet>> planets = webclient
                .get()
                .uri("http://localhost:8080/api/planets/")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Collection<Planet>>() {
                });

        StepVerifier
                .create(planets)
                .assertNext(p -> {
                    Assertions
                            .assertThat(p)
                            .isNotNull()
                            .isNotEmpty()
                            .hasSize(2);
                })
                .verifyComplete();
    }
}

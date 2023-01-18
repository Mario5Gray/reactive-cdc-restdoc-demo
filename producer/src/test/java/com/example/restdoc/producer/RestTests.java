package com.example.restdoc.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@AutoConfigureRestDocs
@WebFluxTest
public class RestTests {

    @Autowired
    WebTestClient client;


    @Test
    public void shouldGetPlanets() {
        client
                .get()
                .uri("/api/planets/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].name").hasJsonPath()
                .jsonPath("$.[1].name").hasJsonPath()
                .consumeWith(WebTestClientRestDocumentation
                        .document("all",
                                SpringCloudContractRestDocs.dslContract()))
                ;
    }

    @Test
    public void shouldAddPlanet() {
        client
                .post()
                .uri("/api/planets/")
                .body(Mono.just("Jupiter"), String.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Jupiter")
                .consumeWith(WebTestClientRestDocumentation
                        .document("all",
                                SpringCloudContractRestDocs.dslContract()));
                // TODO: What should this return

    }
}

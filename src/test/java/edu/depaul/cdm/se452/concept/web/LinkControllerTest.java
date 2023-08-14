package edu.depaul.cdm.se452.concept.web;

import javax.swing.text.html.HTMLEditorKit.LinkController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = LinkController.class)
public class LinkControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shortenLink() {
        webTestClient.post()
                    .uri("/link")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{\"link\":\"https://spring.io\"}")
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful();
    }
    
}

package com.example.restdoc.producer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController()
@RequestMapping("/api/planets")
public class PlanetController {

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<Planet> getPlanets() {
        return Arrays.asList(new Planet(1L, "Saturn"),
                new Planet(2L, "Jupiter"));
    }

    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.TEXT_PLAIN_VALUE})
    Planet createPlanet(@RequestBody String name) {
        return new Planet(new Random().nextLong(), name);
    }
}

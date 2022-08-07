package com.fahad.ratingservice.controller;

import java.util.Arrays;
import java.util.List;

import com.fahad.ratingservice.model.Rating;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @GetMapping("/{bookId}")
    public Mono<Rating> getRating(@PathVariable("bookId") long bookId) {
        List<Rating> ratingList = Arrays.asList(new Rating(1, 1, 3), new Rating(2, 2, 4));
        Rating rating = ratingList.stream().filter(item -> item.getBookId() == bookId).findFirst().orElse(null);
        return Mono.fromCallable(() -> rating);
    }
}

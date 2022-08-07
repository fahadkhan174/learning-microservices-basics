package com.fahad.bookservice.controller;

import java.util.Arrays;
import java.util.List;

import com.fahad.bookservice.model.Book;
import com.fahad.bookservice.model.BookDTO;
import com.fahad.bookservice.model.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
@RefreshScope
public class BookController {

    @Value("${greeting: static value}")
    private String greeting;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @GetMapping
    public Flux<BookDTO> getAllBooks() {
        Book b1 = new Book(1, "Book 1", "Fahad");
        Book b2 = new Book(2, "Book 2", "Mohd");
        List<Book> books = Arrays.asList(b1, b2);

        Flux<BookDTO> bookDTOFlux = Flux.fromIterable(books).flatMap(book -> {
            return getRating(book.getId()).map(rating -> {
                return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), rating.getRating());
            });
        });

        return bookDTOFlux;

    }

    private Mono<Rating> getRating(long bookId) {
        ReactiveCircuitBreaker rcBreaker = reactiveCircuitBreakerFactory.create("ratings-circuit-breaker");
        return rcBreaker.run(webClientBuilder.build().get().uri("http://rating-service/ratings/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Rating.class), throwable -> getDefaultRating());

    }

    private Mono<Rating> getDefaultRating() {
        Rating rating = new Rating(0, 0, 0);
        return Mono.just(rating);
    }

    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting;
    }

}

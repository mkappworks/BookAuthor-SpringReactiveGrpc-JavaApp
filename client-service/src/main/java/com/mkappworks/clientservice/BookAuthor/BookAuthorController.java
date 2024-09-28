package com.mkappworks.clientservice.BookAuthor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class BookAuthorController {

    private final BookAuthorClientService bookAuthorClientService;

    @GetMapping("/{authorId}")
    public Mono<AuthorResponse> getAuthor(@PathVariable int authorId) {
        return bookAuthorClientService.getAuthor(authorId).flatMap(author ->
                Mono.just(AuthorResponse.builder()
                        .authorId(author.getAuthorId())
                        .bookId(author.getBookId())
                        .firstName(author.getFirstName())
                        .lastName(author.getLastName())
                        .gender(author.getGender())
                        .build())
        );
    }

    @GetMapping(value = "/book/{authorId}")
    public Flux<BookResponse> getBooksByAuthor(@PathVariable int authorId) {
        return bookAuthorClientService.getBooksByAuthor(authorId).flatMap(book ->
                Flux.just(BookResponse.builder()
                        .bookId(book.getBookId())
                        .authorId(book.getAuthorId())
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .pages(book.getPages())
                        .build())

        );
    }

    @GetMapping(value = "/streambook/{authorId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookResponse> getStreamBooksByAuthor(@PathVariable int authorId) {
        return bookAuthorClientService.getStreamBooksByAuthor(authorId).flatMap(book ->
                Flux.just(BookResponse.builder()
                        .bookId(book.getBookId())
                        .authorId(book.getAuthorId())
                        .title(book.getTitle())
                        .price(book.getPrice())
                        .pages(book.getPages())
                        .build())

        );
    }
}

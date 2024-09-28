package com.mkappworks.clientservice.BookAuthor;

import com.mkappworks.proto.grpcspringreactivejavaapp.Author;
import com.mkappworks.proto.grpcspringreactivejavaapp.Book;
import com.mkappworks.proto.grpcspringreactivejavaapp.ReactorBookAuthorServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Service
public class BookAuthorClientService {

    @GrpcClient("producer-service")
    ReactorBookAuthorServiceGrpc.ReactorBookAuthorServiceStub bookAuthorServiceStub;

    public Mono<Author> getAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();

        return bookAuthorServiceStub.getAuthor(Mono.just(authorRequest));
    }

    public Flux<Book> getBooksByAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();

        return bookAuthorServiceStub.getBooksByAuthor(Mono.just(authorRequest));
    }

    public Flux<Book> getStreamBooksByAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();

        return bookAuthorServiceStub.getBooksByAuthor(Mono.just(authorRequest))
                .delayElements(Duration.ofSeconds(1));
    }

}

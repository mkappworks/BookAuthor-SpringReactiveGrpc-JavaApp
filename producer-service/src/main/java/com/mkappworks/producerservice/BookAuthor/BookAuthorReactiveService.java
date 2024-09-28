package com.mkappworks.producerservice.BookAuthor;

import com.mkappworks.proto.grpcspringreactivejavaapp.Author;
import com.mkappworks.proto.grpcspringreactivejavaapp.Book;
import com.mkappworks.proto.grpcspringreactivejavaapp.ReactorBookAuthorServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GrpcService
public class BookAuthorReactiveService extends ReactorBookAuthorServiceGrpc.BookAuthorServiceImplBase {

    @Override
    public Mono<Author> getAuthor(Mono<Author> request) {
        return request
                .map(Author::getAuthorId)
                .map(authorId -> TempDB.getAuthorsFromTempDb()
                        .stream()
                        .filter(author -> author.getAuthorId() == authorId)
                        .findFirst()
                        .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND.withDescription("authorId not found in DB"))));
    }

    @Override
    public Flux<Book> getBooksByAuthor(Mono<Author> request) {
        return request
                .map(Author::getAuthorId)
                .flatMapMany(authorId -> Flux.fromStream(TempDB.getBooksFromTempDb()
                        .stream()
                        .filter(book -> book.getAuthorId() == authorId)));
    }
}
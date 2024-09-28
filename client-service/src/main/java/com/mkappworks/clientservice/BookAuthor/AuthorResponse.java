package com.mkappworks.clientservice.BookAuthor;

import lombok.Builder;

@Builder
public record AuthorResponse(int authorId, int bookId, String firstName, String lastName, String gender) {
}


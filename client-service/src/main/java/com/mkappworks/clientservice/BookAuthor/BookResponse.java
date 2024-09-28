package com.mkappworks.clientservice.BookAuthor;

import lombok.Builder;

@Builder
public record BookResponse(int bookId, int authorId, String title, float price, int pages) {
}

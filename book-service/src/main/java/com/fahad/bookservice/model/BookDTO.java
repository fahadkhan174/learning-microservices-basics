package com.fahad.bookservice.model;

public class BookDTO extends Book {

    private int rating;

    public BookDTO() {
    }

    public BookDTO(int rating) {
        this.rating = rating;
    }

    public BookDTO(long id, String title, String author, int rating) {
        super(id, title, author);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}

package com.fahad.ratingservice.model;

public class Rating {

    private long id;
    private long bookId;
    private int rating;

    public Rating() {
    }

    public Rating(long id, long bookId, int rating) {
        this.id = id;
        this.bookId = bookId;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
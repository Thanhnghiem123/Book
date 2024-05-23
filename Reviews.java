package entity;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {
    private int rating;
    private String comment;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book book;



    public Reviews(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Reviews() {
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

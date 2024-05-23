package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CollectionId;

import java.io.Serializable;
import java.util.Set;

@Entity

@Table(name = "books")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book implements Serializable {
    //Thông tin về sách (Book) bao gồm: Mã sách theo tiêu chuẩn ISBN duy nhất (ISBN), tên sách (name),
    //năm xuất bản (publishYear), số trang (numOfPages) và giá tiền (price).
    @Id
    protected String ISBN;

    protected String name;
    @Column(name = "publish_year")
    protected int publishYear;
    @Column(name = "num_of_pages")
    protected int numOfPages;
    protected double price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reviews> reviews;


    @ElementCollection
    @CollectionTable(name = "books_authors", joinColumns = @JoinColumn(name = "ISBN"))
    @Column(name = "author")
    protected Set<String> authors;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    protected Publisher publisher;

    public Set<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Book(String ISBN, String name, int publishYear, int numOfPages, double price, Set<String> authors, Publisher publisher) {
        this.ISBN = ISBN;
        this.name = name;
        this.publishYear = publishYear;
        this.numOfPages = numOfPages;
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book() {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", publishYear=" + publishYear +
                ", numOfPages=" + numOfPages +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }
}

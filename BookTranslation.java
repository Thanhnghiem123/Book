package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "book_translations")
public class BookTranslation extends Book implements Serializable {
    @Column(name = "translate_name")
    private String translateName;
    private String language;

    public BookTranslation() {
    }

    public BookTranslation(String ISBN, String name, int publishYear, int numOfPages, double price, Set<String> authors, Publisher publisher, String translateName, String language) {
        super(ISBN, name, publishYear, numOfPages, price, authors, publisher);
        this.translateName = translateName;
        this.language = language;
    }



    public String getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String translateName) {
        this.translateName = translateName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

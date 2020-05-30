package alekseybykov.portfolio.xml.jaxb;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Aleksey Bykov
 * @since 18.10.2019
 */
@XmlRootElement(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Book {

    private String isbn;
    private String author;
    private String title;
    private String genre;
    private double price;

    // deserialization requires a constructor with no parameters
    private Date publishDate;

    private String publisher;

    @XmlElement
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement
    public void setPrice(double price) {
        this.price = price;
    }

    @XmlElement(name = "publish_date")
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @XmlElement
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}

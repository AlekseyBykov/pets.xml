package alekseybykov.portfolio.xml.jaxb;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Aleksey Bykov
 * @since 18.10.2019
 */
@XmlRootElement(name = "catalog")
@NoArgsConstructor
@Getter
public class Catalog {

    @XmlElement(name = "book")
    private List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    public String toString() {
        return Arrays.deepToString(books.toArray());
    }
}

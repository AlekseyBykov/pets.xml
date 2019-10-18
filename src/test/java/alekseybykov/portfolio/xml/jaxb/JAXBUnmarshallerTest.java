//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.xml.jaxb;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Also can be used schemagen util for deserialization XSD
 * schema to Java classes.
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-18
 */
@DisplayName("Tests for JAXB deserializer")
class JAXBUnmarshallerTest {

    private static File xmlFile = Paths.get("src", "test", "resources", "xml_without_whitespaces.xml").toFile();
    private static List<String> expected;

    @BeforeAll
    @SneakyThrows
    private static void init() {
        xmlFile = Paths.get("src", "test", "resources", "xml_without_whitespaces.xml").toFile();
        expected = Arrays.asList("Thomas Erl", "Josh Juneau", "Enrico Pirozzi");
    }

    @Test
    @SneakyThrows
    @DisplayName("Deserialize XML document into Java classes")
    void testDeserializeXmlIntoJavaClasses() {

        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Catalog catalog = (Catalog) unmarshaller.unmarshal(xmlFile);

        List<Book> books = catalog.getBooks();

        assertThat(books, hasSize(3));

        List<String> authors = books.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toList());

        assertThat(authors, is(expected));

        assertEquals("Enrico Pirozzi", authors.get(2));
    }
}

package alekseybykov.portfolio.xml.sax;

import alekseybykov.portfolio.xml.XmlElements;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * Useful for working with large files.
 */
@DisplayName("Tests for parsing document by using SAX parser")
class SAXParserTest {

    private static File xml;

    private static boolean author;

    private static List<String> authors;
    private static List<String> expected;

    @BeforeAll
    @SneakyThrows
    private static void init() {
        xml = Paths.get("src", "test", "resources", "xml_with_whitespaces.xml").toFile();

        authors = new ArrayList<>();
        expected = Arrays.asList("Thomas Erl", "Josh Juneau", "Enrico Pirozzi");

        author = false;
    }

    @Test
    @SneakyThrows
    @DisplayName("Obtain list of element values")
    void testObtainListOfElementValues() {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        // the model of events: callbacks is performed when tag occurs
        DefaultHandler defaultHandler = new DefaultHandler() {
            public void startElement (String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase(XmlElements.AUTHOR.getName())) {
                    author = true;
                }  /* and so on */
            }

            @Override
            public void characters(char ch[], int start, int length) {
                if (author) {
                    authors.add(new String(ch, start, length));
                    author = false;
                } /* and so on */
            }
        };

        saxParser.parse(xml, defaultHandler);

        assertThat(authors, is(expected));
        assertThat(authors, hasItems("Josh Juneau"));
        assertThat(authors, hasSize(3));
    }
}

package alekseybykov.portfolio.xml.jaxb;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Also can be used schemagen util for deserialization Java classes
 * to XSD schema.
 */
@DisplayName("Tests for JAXB serializer")
class JAXBMarshallerTest {

    private static File xmlFile;
    private static Catalog catalog;
    private static Path xmlFilePath;

    @BeforeAll
    @SneakyThrows
    private static void init() {
        xmlFilePath = Paths.get("src", "test", "resources", "temp");
        xmlFile = xmlFilePath.resolve("temp.xml").toFile();

        catalog = new Catalog();

        Book firstBook = Book.builder()
                .isbn("1").author("A").genre("B").price(10.3).publisher("C").publishDate(new Date()).build();

        Book secondBook = Book.builder()
                .isbn("2").author("D").genre("E").price(20.3).publisher("F").publishDate(new Date()).build();

        catalog.add(firstBook);
        catalog.add(secondBook);
    }

    @AfterAll
    @SneakyThrows
    private static void clearTempDirectory() {
        FileUtils.cleanDirectory(xmlFilePath.toAbsolutePath().toFile());
    }

    @Test
    @SneakyThrows
    @DisplayName("Serialize Java object trees into XML file")
    void test() {

        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.marshal(catalog, xmlFile);

        assertTrue(xmlFile.exists());
        assertTrue(xmlFile.length() > 0);

        marshaller.marshal(catalog, System.out);
    }
}

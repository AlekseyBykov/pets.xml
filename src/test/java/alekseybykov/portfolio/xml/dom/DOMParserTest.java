//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.xml.dom;

import alekseybykov.portfolio.xml.XmlElements;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-16
 */
@DisplayName("Tests for parsing document by using DOM parser")
class DOMParserTest {

    private final File xmlFile = Paths.get("src", "test", "resources", "books.xml").toFile();

    @Test
    @SneakyThrows
    @DisplayName("Obtain value of element")
    void testReadElementValue() {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();

        assertEquals(XmlElements.CATALOG.getName(), root.getNodeName());

        NodeList nodeList = root.getChildNodes();
        Node node = nodeList.item(1);

        assertTrue(node.getNodeType() == Node.ELEMENT_NODE);

        Element element = (Element)node;
        String isbnValue = element.getElementsByTagName(XmlElements.ISBN.getName())
                .item(0)
                .getChildNodes()
                .item(0)
                .getNodeValue();

        assertEquals("978-0-1338-5904-1", isbnValue);
    }
}

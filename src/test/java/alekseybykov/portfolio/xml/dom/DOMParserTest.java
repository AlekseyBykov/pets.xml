//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.xml.dom;

import alekseybykov.portfolio.xml.XmlElements;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
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

    private static Document document;

    @BeforeAll
    @SneakyThrows
    private static void loadDocument() {
        File xmlFile = Paths.get("src", "test", "resources", "xml_with_whitespaces.xml").toFile();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // all the document loaded in memory
        document = documentBuilder.parse(xmlFile);
    }

    @Test
    @SneakyThrows
    @DisplayName("Obtain root element")
    void testObtainRootElement() {
        Element root = document.getDocumentElement();

        assertEquals(XmlElements.CATALOG.getName(), root.getNodeName());
    }

    @Test
    @SneakyThrows
    @DisplayName("Traversing XML with whitespaces")
    void testTraverseXmlDocumentWithWhitespaces() {
        Element root = document.getDocumentElement();

        NodeList nodeList = root.getChildNodes();

        assertEquals(7, nodeList.getLength());

        assertEquals("#text", nodeList.item(0).getNodeName());

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(1).getNodeName());
        assertTrue(nodeList.item(1).getNodeType() == Node.ELEMENT_NODE);

        assertEquals("#text", nodeList.item(2).getNodeName());

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(3).getNodeName());
        assertTrue(nodeList.item(3).getNodeType() == Node.ELEMENT_NODE);

        assertEquals("#text", nodeList.item(4).getNodeName());

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(5).getNodeName());
        assertTrue(nodeList.item(5).getNodeType() == Node.ELEMENT_NODE);

        assertEquals("#text", nodeList.item(6).getNodeName());
    }

    @Test
    @SneakyThrows
    @DisplayName("Obtain value of element")
    void testReadElementValue() {
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

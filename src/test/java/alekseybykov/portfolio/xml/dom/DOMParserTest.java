//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.xml.dom;

import alekseybykov.portfolio.xml.XmlElements;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-16
 */
@DisplayName("Tests for parsing document by using DOM parser")
class DOMParserTest {

    private static Document documentWithWhitespaces;
    private static Document documentWithoutWhitespaces;

    private static File xmlDocumentFile;
    private static Path xmlDocumentFilePath;

    @BeforeAll
    @SneakyThrows
    private static void loadDocuments() {
        File documentWithWhitespacesFile = Paths.get("src", "test", "resources", "xml_with_whitespaces.xml").toFile();
        File documentWithoutWhitespacesFile = Paths.get("src", "test", "resources", "xml_without_whitespaces.xml").toFile();

        xmlDocumentFilePath = Paths.get("src", "test", "resources", "temp");
        xmlDocumentFile = xmlDocumentFilePath.resolve("xml_document.xml").toFile();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // all the documents loaded in memory
        documentWithWhitespaces = documentBuilder.parse(documentWithWhitespacesFile);
        documentWithoutWhitespaces = documentBuilder.parse(documentWithoutWhitespacesFile);
    }

    @AfterAll
    @SneakyThrows
    private static void clearTempDirectory() {
        FileUtils.cleanDirectory(xmlDocumentFilePath.toAbsolutePath().toFile());
    }

    @Test
    @SneakyThrows
    @DisplayName("Obtain root element")
    void testObtainRootElement() {
        Element root = documentWithWhitespaces.getDocumentElement();

        assertEquals(XmlElements.CATALOG.getName(), root.getNodeName());
    }

    @Test
    @SneakyThrows
    @DisplayName("Traversing XML with whitespaces")
    void testTraverseXmlDocumentWithWhitespaces() {
        Element root = documentWithWhitespaces.getDocumentElement();

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
    @DisplayName("Traversing XML without whitespaces")
    void testTraverseXmlDocumentWithoutWhitespaces() {
        Element root = documentWithoutWhitespaces.getDocumentElement();

        NodeList nodeList = root.getChildNodes();

        assertEquals(3, nodeList.getLength());

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(0).getNodeName());
        assertTrue(nodeList.item(0).getNodeType() == Node.ELEMENT_NODE);

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(1).getNodeName());
        assertTrue(nodeList.item(1).getNodeType() == Node.ELEMENT_NODE);

        assertEquals(XmlElements.BOOK.getName(), nodeList.item(2).getNodeName());
        assertTrue(nodeList.item(2).getNodeType() == Node.ELEMENT_NODE);
    }

    @Test
    @SneakyThrows
    @DisplayName("Obtain value of element")
    void testReadElementValue() {
        Element root = documentWithWhitespaces.getDocumentElement();

        assertEquals(XmlElements.CATALOG.getName(), root.getNodeName());

        NodeList nodeList = root.getChildNodes();
        Node node = nodeList.item(1);

        assertTrue(node.getNodeType() == Node.ELEMENT_NODE);

        Element element = (Element) node;
        String isbnValue = element.getElementsByTagName(XmlElements.ISBN.getName())
                .item(0)
                .getChildNodes()
                .item(0)
                .getNodeValue();

        assertEquals("978-0-1338-5904-1", isbnValue);
    }

    @Test
    @SneakyThrows
    @DisplayName("Create XML document")
    void testCreateXmlDocument() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element root = document.createElement(XmlElements.CATALOG.getName());
        document.appendChild(root);

        Element book = document.createElement(XmlElements.BOOK.getName());
        root.appendChild(book);

        for (XmlElements el : XmlElements.values()) {
            Element element = document.createElement(el.getName());
            element.setTextContent("text content");
            book.appendChild(element);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(xmlDocumentFile);

        transformer.transform(domSource, streamResult);

        assertTrue(xmlDocumentFile.exists());
        assertTrue(xmlDocumentFile.length() > 0);
    }
}

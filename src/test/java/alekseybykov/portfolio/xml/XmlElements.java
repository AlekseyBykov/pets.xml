package alekseybykov.portfolio.xml;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aleksey Bykov
 * @since 17.10.2019
 */
@Getter
@RequiredArgsConstructor
public enum XmlElements {

    CATALOG("catalog"),
    BOOK("book"),
    ISBN("isbn"),
    AUTHOR("author"),
    TITLE("title"),
    GENRE("genre"),
    PRICE("price"),
    PUBLISH_DATE("publish_date"),
    PUBLISHER("publisher");

    private final String name;
}

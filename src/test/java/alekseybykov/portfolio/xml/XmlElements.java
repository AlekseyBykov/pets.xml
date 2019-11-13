package alekseybykov.portfolio.xml;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

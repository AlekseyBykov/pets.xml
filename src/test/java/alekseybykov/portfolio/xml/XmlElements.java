//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.xml;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-10-17
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

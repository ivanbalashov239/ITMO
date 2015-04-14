package calculator;

import java.util.List;

/**
 * Created by anton on 14.04.15.
 */
public interface Node {
    List<Node> childs();
    Double calculate();
}

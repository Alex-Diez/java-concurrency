package org.sudoku.game.elements;

public class Element
        implements Cloneable {

    public static final Element EMPTY_ELEMENT = new Element(0);
    static final Element[] POSSIBLE_ELEMENTS = new Element[] {
            new Element(1),
            new Element(2),
            new Element(3),
            new Element(4),
            new Element(5),
            new Element(6),
            new Element(7),
            new Element(8),
            new Element(9)
    };

    final int value;

    public Element(int value) {
        this.value = value;
    }

    @Override
    public Element clone() throws CloneNotSupportedException {
        return (Element)super.clone();
    }

    public String toString() {
        return new StringBuilder('|')
                .append(value)
                .append('|').toString();
    }
}

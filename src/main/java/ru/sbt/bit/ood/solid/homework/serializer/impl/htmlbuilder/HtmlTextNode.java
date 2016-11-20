package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

/**
 * Created by Ivan on 20/11/2016.
 */
public class HtmlTextNode implements HtmlBuilder {
    private String text;

    public HtmlTextNode(String text) {
        setText(text);
    }

    public void setText(String text) {
        // TODO html encode
        this.text = text;
    }

    @Override
    public String build() {
        return text;
    }

    @Override
    public void clear() {
        text = "";
    }

    @Override
    public boolean appendElement(HtmlBuilder builder) {
        return false;
    }
}

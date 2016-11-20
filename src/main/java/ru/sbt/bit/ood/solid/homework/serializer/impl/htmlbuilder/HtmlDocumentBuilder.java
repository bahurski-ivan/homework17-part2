package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/11/2016.
 */
public class HtmlDocumentBuilder implements HtmlBuilder {
    private List<HtmlBuilder> elements = new ArrayList<>();

    public HtmlTableBuilder table() {
        return new HtmlTableBuilder();
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public boolean appendElement(HtmlBuilder builder) {
        elements.add(builder);
        return true;
    }

    @Override
    public String build() {
        String bodyContent = "";

        for (HtmlBuilder element : elements)
            bodyContent += element.build();

        return "<html><head></head><body>" + bodyContent + "</body></html>";
    }
}

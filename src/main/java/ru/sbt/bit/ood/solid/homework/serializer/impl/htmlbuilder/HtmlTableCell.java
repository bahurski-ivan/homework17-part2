package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/11/2016.
 */
public class HtmlTableCell implements HtmlBuilder {
    private List<HtmlBuilder> items = new ArrayList<>();
    private boolean isHeader;

    HtmlTableCell() {
        clear();
    }


    public HtmlTableCell appendText(String text) {
        items.add(new HtmlTextNode(text));
        return this;
    }

    public HtmlTableCell makeHeader(boolean flag) {
        isHeader = flag;
        return this;
    }


    @Override
    public boolean appendElement(HtmlBuilder html) {
        items.add(html);
        return true;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public String build() {
        String content = "";
        for (HtmlBuilder element : items)
            content += element.build();
        String tag = getTag();
        return "<" + tag + ">" +
                content +
                "</" + tag + ">";
    }


    private String getTag() {
        return isHeader ? "th" : "td";
    }
}

package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 13/11/2016.
 */
public class HtmlTableBuilder implements HtmlBuilder {
    private List<HtmlTableRow> rows = new ArrayList<>();

    @Override
    public void clear() {
        rows.clear();
    }

    @Override
    public boolean appendElement(HtmlBuilder builder) {
        return false;
    }

    @Override
    public String build() {
        String tableContent = "";
        for (HtmlTableRow row : rows)
            tableContent += row.build();
        return "<table>" + tableContent + "</table>";
    }

    public HtmlTableRow row() {
        HtmlTableRow row = new HtmlTableRow();
        rows.add(row);
        return row;
    }
}

package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/11/2016.
 */
public class HtmlTableRow implements HtmlBuilder {
    private List<HtmlTableCell> cells = new ArrayList<>();

    HtmlTableRow() {
    }

    public HtmlTableCell cell() {
        HtmlTableCell cell = new HtmlTableCell();
        cells.add(cell);
        return cell;
    }

    @Override
    public String build() {
        String rowValue = "";
        for (HtmlTableCell cell : cells)
            rowValue += cell.build();
        return "<tr>" + rowValue + "</tr>";
    }

    @Override
    public void clear() {
        cells.clear();
    }

    @Override
    public boolean appendElement(HtmlBuilder builder) {
        if (!(builder instanceof HtmlTableCell))
            return false;
        cells.add((HtmlTableCell) builder);
        return true;
    }
}

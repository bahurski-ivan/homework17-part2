package ru.sbt.bit.ood.solid.homework;

/**
 * Created by Ivan on 13/11/2016.
 */
class HtmlTableBuilder {
    private final StringBuilder html;
    private boolean buildWasCalled;
    private boolean needToCloseRow;
    private boolean needToCloseCell;

    public HtmlTableBuilder() {
        html = new StringBuilder();
        html.append("<html><body><table>");
    }

    HtmlTableBuilder row() {
        closeStuff(true);
        html.append("<tr>");
        needToCloseRow = true;
        return this;
    }

    HtmlTableBuilder cell() {
        closeStuff(false);

        if (!needToCloseRow)
            row();

        html.append("<td>");
        needToCloseCell = true;

        return this;
    }

    HtmlTableBuilder append(String value) {
        if (!needToCloseCell) {
            if (!needToCloseRow)
                row();
            cell();
        }

        html.append(value);

        return this;
    }

    String build() {
        if (!buildWasCalled) {
            closeStuff(true);
            buildWasCalled = true;
            html.append("</table></body></html>");
        }
        return html.toString();
    }

    @Override
    public String toString() {
        return html.toString();
    }

    private void closeStuff(boolean closeRow) {
        if (needToCloseCell) {
            html.append("</td>");
            needToCloseCell = false;
        }

        if (needToCloseRow && closeRow) {
            html.append("</tr>");
            needToCloseRow = false;
        }
    }
}

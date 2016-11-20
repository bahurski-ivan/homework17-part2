package ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder;

/**
 * Created by Ivan on 20/11/2016.
 */
public interface HtmlBuilder {
    String build();

    void clear();

    boolean appendElement(HtmlBuilder builder);
}

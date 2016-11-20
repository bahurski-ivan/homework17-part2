package ru.sbt.bit.ood.solid.homework.serializer.impl;

import ru.sbt.bit.ood.solid.homework.serializer.ToStringSerializer;
import ru.sbt.bit.ood.solid.homework.serializer.annotations.Ignore;
import ru.sbt.bit.ood.solid.homework.serializer.annotations.ValueList;
import ru.sbt.bit.ood.solid.homework.serializer.annotations.ValueName;
import ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder.HtmlDocumentBuilder;
import ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder.HtmlTableBuilder;
import ru.sbt.bit.ood.solid.homework.serializer.impl.htmlbuilder.HtmlTableRow;
import ru.sbt.bit.ood.solid.homework.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 20/11/2016.
 */
public class HtmlTableSerializer<T> implements ToStringSerializer<T> {
    @Override
    public String serialize(T object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        List<Pair<String, String>> groupValues = new ArrayList<>();

        List<Field> fieldsToSerialize = Arrays.stream(fields)
                .peek(f -> f.setAccessible(true))
                .peek(f -> {
                    if (f.isAnnotationPresent(ValueName.class)) {
                        try {
                            groupValues.add(Pair.of(f.getAnnotation(ValueName.class).value(), f.get(object).toString()));
                        } catch (IllegalAccessException ignored) {
                        }
                    }
                })
                .filter(f -> f.isAnnotationPresent(ValueList.class))
                .collect(Collectors.toList());

        HtmlDocumentBuilder document = new HtmlDocumentBuilder();

        if (fieldsToSerialize.size() != 0) {
            Field field = fieldsToSerialize.get(0);
            field.setAccessible(true);
            Object o = null;

            try {
                o = field.get(object);
            } catch (IllegalAccessException ignored) {
            }

            if (o instanceof List) {
                HtmlTableBuilder table = serializeValues((List) o);

                groupValues.forEach(p -> {
                    HtmlTableRow row = table.row();
                    row.cell().appendText(p.getKey());
                    row.cell().appendText(p.getValue());
                });

                document.appendElement(table);
            }
        }

        return document.build();
    }

    private HtmlTableBuilder serializeValues(List values) {
        HtmlTableBuilder table = new HtmlTableBuilder();

        if (values.size() == 0)
            return table;

        List<Field> toSerialize;
        Object o = values.get(0);
        Field[] fields = o.getClass().getDeclaredFields();

        {
            HtmlTableRow row = table.row();
            toSerialize = Arrays.stream(fields)
                    .peek(field -> field.setAccessible(true))
                    .filter(f -> !f.isAnnotationPresent(Ignore.class))
                    .peek(f -> {
                        String name = f.getName();
                        if (f.isAnnotationPresent(ValueName.class))
                            name = f.getAnnotation(ValueName.class).value();

                        row.cell().appendText(name).makeHeader(true);
                    })
                    .collect(Collectors.toList());
        }

        for (Object obj : values) {
            HtmlTableRow row = table.row();
            toSerialize.stream()
                    .map(f -> {
                        Object result = null;
                        try {
                            result = f.get(obj);
                        } catch (IllegalAccessException ignored) {
                        }
                        return result;
                    })
                    .forEach(value -> row.cell().appendText(value.toString()));
        }

        return table;
    }
}

package ru.sbt.bit.ood.solid.homework.serializer;

/**
 * Created by Ivan on 20/11/2016.
 */
public interface Serializer<T, R> {
    R serialize(T object);
}

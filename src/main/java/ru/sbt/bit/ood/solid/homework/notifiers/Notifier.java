package ru.sbt.bit.ood.solid.homework.notifiers;

/**
 * Created by Ivan on 20/11/2016.
 */
public interface Notifier {
    <T> void doNotify(T object);
}
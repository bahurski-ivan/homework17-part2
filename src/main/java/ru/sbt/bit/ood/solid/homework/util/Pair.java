package ru.sbt.bit.ood.solid.homework.util;

import java.util.Map;

/**
 * Created by Ivan on 20/11/2016.
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public static <K, V> Pair<K, V> of(K key, V value) {
        Pair<K, V> result = new Pair<>();
        result.setKey(key);
        result.setValue(value);
        return result;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}

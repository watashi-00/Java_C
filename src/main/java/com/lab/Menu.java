package com.lab;

import java.util.HashMap;
import java.util.function.Function;

interface Menu<K, V, I> {

    HashMap<K, V> getMap();

    Function<K, I> indexer();

    default V select(I index) {
        for (var entry : getMap().entrySet()) {
            if (indexer().apply(entry.getKey()).equals(index)) {
                return entry.getValue();
            }
        }

        return null;
    }

    default void put(K key, V value) {
        getMap().put(key, value);
    }

}
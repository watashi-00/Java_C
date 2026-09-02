package com.lab;

import java.util.HashMap;
import java.util.function.Function;

public class MenuActions <K, V, I> implements Menu<K, V, I> {

    private final HashMap<K, V> map;
    private final Function<K, I> indexer;
    MenuActions(Function<K, I> indexer) {
        this.map = new HashMap<K, V>();
        this.indexer = indexer;
    }

    @Override
    public HashMap<K, V> getMap() {
        return this.map;
    }

    @Override
    public Function<K, I> indexer() {
        return indexer;
    }
}

package com.otzg.util;


import java.util.Map;

public interface FMap<K,V> extends Map<K,V> {

    FMap p(K key, V value);
}

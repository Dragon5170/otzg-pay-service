package com.otzg.util;

import java.util.HashMap;

public class HashFMap<K,V> extends HashMap<K,V> implements FMap<K,V>{

    public HashFMap() {
        super();
    }

    public HashFMap p(K key,V value){
        this.put(key, value);
        return this;
    }

}

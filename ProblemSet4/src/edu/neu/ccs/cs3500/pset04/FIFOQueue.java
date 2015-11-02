package edu.neu.ccs.cs3500.pset04;

import java.util.ArrayList;

public final class FIFOQueue<K> extends Object implements ReplacementPolicy<K>{

  // The capacity of the cache:
  private final int capacity;
  // The number of items in the cache:
  private int size = 0;
  private ArrayList<K> cache;

  public FIFOQueue(int cap){
    if (cap < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    }
    cache = new ArrayList<K>(cap);
    capacity = cap;
  }

  @Override
  public K require(K item) {

    //do nothing if it exists already
    if (size>0) {
      for (K cachedItem : cache) {
        if (item.equals(cachedItem)) {
          return null;
        }
      }
    }
    K evicted = null;

    //if not, evict oldest if necessary and append new to the end
    if (size==capacity) {
      evicted = cache.get(0); //O(1)
      cache.remove(0); // O(capacity()) -- this call is the only > O(1) part
    } else {
      size++;
    }
    cache.add(item); // O(1) because cache was initialized with size
    if(cache.size()>capacity){
      throw new IllegalArgumentException("cache has extended its capabilities");
    }

    return evicted;
  }

  public int capacity(){
    return capacity;
  }

  public int size(){
    return size;
  }
}

package edu.neu.ccs.cs3500.pset04;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LRUQueueTest {

  @Test
  public void extendedExample() {
    ReplacementPolicy<Integer> policy = new LRUQueue<>(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    policy.require(1);                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    policy.require(2);                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    policy.require(2);                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    policy.require(3);                            // 1 2 3 _ _
    policy.require(4);                            // 1 2 3 4 _
    assertEquals(4, policy.size());

    // requiring a present object places it at the end
    // of the cache again
    policy.require(1);                            // 2 3 4 1 _
    assertEquals(4, policy.size());

    policy.require(5);                            // 2 3 4 1 5
    assertEquals(5, policy.size());

    // The least accessed will be evicted
    assertEquals((Integer) 2, policy.require(6)); // 3 4 1 5 6
    assertEquals(5, policy.size());

    assertNull(policy.require(5));                // 3 4 1 6 5
    assertNull(policy.require(1));                // 3 4 6 5 1
    assertNull(policy.require(5));                // 3 4 6 1 5
    assertNull(policy.require(4));                // 3 6 1 5 4
    assertEquals((Integer) 3, policy.require(7)); // 6 1 5 4 7
    assertNull(policy.require(4));                // 6 1 5 7 4
    assertEquals((Integer) 6, policy.require(8)); // 1 5 4 7 8
    assertEquals((Integer) 1, policy.require(9)); // 5 4 7 8 9

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }

  @Test
  public void testStrings() {
    ReplacementPolicy<String> policy = new LRUQueue<>(3);
    assertNull(policy.require("abc"));
    assertNull(policy.require("def"));
    assertNull(policy.require("ghi"));
    assertEquals("abc", policy.require("jkl"));
  }

  @Test
  public void testArrays() {
    ReplacementPolicy<Integer[]> policy = new LRUQueue<>(3);
    Integer[] a1 = {1, 2, 3};
    Integer[] a2 = {4, 5, 6};
    assertNull(policy.require(a1));
    assertNull(policy.require(a2));
    assertNull(policy.require(new Integer[]{7, 8, 9}));
    assertNull(policy.require(a1));
    assertTrue(a2.equals(policy.require(new Integer[]{10, 11, 12})));
  }

  @Test(expected = NullPointerException.class)
  public void testWrongType() {
    ReplacementPolicy<Integer> policy = new LRUQueue<>(4);
    policy.require((Integer) 1);
    policy.require(Integer.getInteger("abc"));
  }

  @Test(expected = NullPointerException.class)
  public void testNull() {
    ReplacementPolicy<Integer> policy = new LRUQueue<>(4);
    policy.require(1);
    policy.require(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroCap() {
    ReplacementPolicy<Integer> policy = new LRUQueue<>(0);
  }

}

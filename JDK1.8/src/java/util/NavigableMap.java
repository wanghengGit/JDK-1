/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea and Josh Bloch with assistance from members of JCP
 * JSR-166 Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util;

/**
 * @author Doug Lea
 * @author Josh Bloch
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 1.6
 * @author wangheng
 * @date 20200308
 */
public interface NavigableMap<K,V> extends SortedMap<K,V> {

    Map.Entry<K,V> lowerEntry(K key);

    K lowerKey(K key);

    Map.Entry<K,V> floorEntry(K key);

    K floorKey(K key);

    Map.Entry<K,V> ceilingEntry(K key);

    K ceilingKey(K key);

    Map.Entry<K,V> higherEntry(K key);

    K higherKey(K key);

    Map.Entry<K,V> firstEntry();

    Map.Entry<K,V> lastEntry();

    Map.Entry<K,V> pollFirstEntry();

    Map.Entry<K,V> pollLastEntry();

    NavigableMap<K,V> descendingMap();

    NavigableSet<K> navigableKeySet();

    NavigableSet<K> descendingKeySet();

    NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                             K toKey,   boolean toInclusive);

    NavigableMap<K,V> headMap(K toKey, boolean inclusive);

    NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

    SortedMap<K,V> subMap(K fromKey, K toKey);

    SortedMap<K,V> headMap(K toKey);

    SortedMap<K,V> tailMap(K fromKey);
}

/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * @author  wangheng
 * @date 2019/08/14
 * @see     Collection
 * @see     Set
 * @see     HashSet
 * @see     Comparable
 * @see     Comparator
 * @see     TreeMap
 * @since   1.2
 */

public class TreeSet<E> extends AbstractSet<E>
    implements NavigableSet<E>, Cloneable, java.io.Serializable
{
    /**
     * The backing map.
     */
    private transient NavigableMap<E,Object> m;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    /**
     * Constructs a set backed by the specified navigable map.
     */
    TreeSet(NavigableMap<E,Object> m) {
        this.m = m;
    }

    public TreeSet() {
        this(new TreeMap<E,Object>());
    }

    public TreeSet(Comparator<? super E> comparator) {
        this(new TreeMap<>(comparator));
    }

    public TreeSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    /**
     * Constructs a new tree set containing the same elements and
     * using the same ordering as the specified sorted set.
     *
     * @param s sorted set whose elements will comprise the new set
     * @throws NullPointerException if the specified sorted set is null
     */
    public TreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }

    /**
     * Returns an iterator over the elements in this set in ascending order.
     *
     * @return an iterator over the elements in this set in ascending order
     */
    public Iterator<E> iterator() {
        return m.navigableKeySet().iterator();
    }

    /**
     * Returns an iterator over the elements in this set in descending order.
     *
     * @return an iterator over the elements in this set in descending order
     * @since 1.6
     */
    public Iterator<E> descendingIterator() {
        return m.descendingKeySet().iterator();
    }

    /**
     * @since 1.6
     */
    public NavigableSet<E> descendingSet() {
        return new TreeSet<>(m.descendingMap());
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality)
     */
    public int size() {
        return m.size();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements
     */
    public boolean isEmpty() {
        return m.isEmpty();
    }

    public boolean contains(Object o) {
        return m.containsKey(o);
    }

    public boolean add(E e) {
        return m.put(e, PRESENT)==null;
    }

    public boolean remove(Object o) {
        return m.remove(o)==PRESENT;
    }

    /**
     * Removes all of the elements from this set.
     * The set will be empty after this call returns.
     */
    public void clear() {
        m.clear();
    }

    public  boolean addAll(Collection<? extends E> c) {
        // Use linear-time version if applicable
        if (m.size()==0 && c.size() > 0 &&
            c instanceof SortedSet &&
            m instanceof TreeMap) {
            SortedSet<? extends E> set = (SortedSet<? extends E>) c;
            TreeMap<E,Object> map = (TreeMap<E, Object>) m;
            Comparator<?> cc = set.comparator();
            Comparator<? super E> mc = map.comparator();
            if (cc==mc || (cc != null && cc.equals(mc))) {
                map.addAllForTreeSet(set, PRESENT);
                return true;
            }
        }
        return super.addAll(c);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code fromElement} or {@code toElement}
     *         is null and this set uses natural ordering, or its comparator
     *         does not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.6
     */
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive,
                                  E toElement,   boolean toInclusive) {
        return new TreeSet<>(m.subMap(fromElement, fromInclusive,
                                       toElement,   toInclusive));
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code toElement} is null and
     *         this set uses natural ordering, or its comparator does
     *         not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.6
     */
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return new TreeSet<>(m.headMap(toElement, inclusive));
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code fromElement} is null and
     *         this set uses natural ordering, or its comparator does
     *         not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.6
     */
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new TreeSet<>(m.tailMap(fromElement, inclusive));
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code fromElement} or
     *         {@code toElement} is null and this set uses natural ordering,
     *         or its comparator does not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code toElement} is null
     *         and this set uses natural ordering, or its comparator does
     *         not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if {@code fromElement} is null
     *         and this set uses natural ordering, or its comparator does
     *         not permit null elements
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    public Comparator<? super E> comparator() {
        return m.comparator();
    }

    /**
     * @throws NoSuchElementException {@inheritDoc}
     */
    public E first() {
        return m.firstKey();
    }

    /**
     * @throws NoSuchElementException {@inheritDoc}
     */
    public E last() {
        return m.lastKey();
    }

    // NavigableSet API methods

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     *         and this set uses natural ordering, or its comparator
     *         does not permit null elements
     * @since 1.6
     */
    public E lower(E e) {
        return m.lowerKey(e);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     *         and this set uses natural ordering, or its comparator
     *         does not permit null elements
     * @since 1.6
     */
    public E floor(E e) {
        return m.floorKey(e);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     *         and this set uses natural ordering, or its comparator
     *         does not permit null elements
     * @since 1.6
     */
    public E ceiling(E e) {
        return m.ceilingKey(e);
    }

    /**
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     *         and this set uses natural ordering, or its comparator
     *         does not permit null elements
     * @since 1.6
     */
    public E higher(E e) {
        return m.higherKey(e);
    }

    /**
     * @since 1.6
     */
    public E pollFirst() {
        Map.Entry<E,?> e = m.pollFirstEntry();
        return (e == null) ? null : e.getKey();
    }

    /**
     * @since 1.6
     */
    public E pollLast() {
        Map.Entry<E,?> e = m.pollLastEntry();
        return (e == null) ? null : e.getKey();
    }

    /**
     * Returns a shallow copy of this {@code TreeSet} instance. (The elements
     * themselves are not cloned.)
     *
     * @return a shallow copy of this set
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        TreeSet<E> clone;
        try {
            clone = (TreeSet<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }

        clone.m = new TreeMap<>(m);
        return clone;
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden stuff
        s.defaultWriteObject();

        // Write out Comparator
        s.writeObject(m.comparator());

        // Write out size
        s.writeInt(m.size());

        // Write out all elements in the proper order.
        for (E e : m.keySet())
            s.writeObject(e);
    }

    /**
     * Reconstitute the {@code TreeSet} instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden stuff
        s.defaultReadObject();

        // Read in Comparator
        @SuppressWarnings("unchecked")
            Comparator<? super E> c = (Comparator<? super E>) s.readObject();

        // Create backing TreeMap
        TreeMap<E,Object> tm = new TreeMap<>(c);
        m = tm;

        // Read in size
        int size = s.readInt();

        tm.readTreeSet(size, s, PRESENT);
    }

    public Spliterator<E> spliterator() {
        return TreeMap.keySpliteratorFor(m);
    }

    private static final long serialVersionUID = -2479143000061671589L;
}

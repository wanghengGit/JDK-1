package java.lang;
import java.util.*;

/**
 * This interface imposes a total ordering on the objects of each class that
 * implements it.  This ordering is referred to as the class's <i>natural
 * ordering</i>, and the class's <tt>compareTo</tt> method is referred to as
 * its <i>natural comparison method</i>.<p>
 * @param <T> the type of objects that this object may be compared to
 *
 * @author  Josh Bloch
 * @see java.util.Comparator
 * @since 1.2
 * @date 20200325
 * Comparable接口用于可以比较大小的类。此接口强行对实现它的每个类的对象进行整体排序，这种排序被称为类的自然排序，
 * 类的comparaTo方法被称为它的自然比较方法。
 *   实现此接口的对象列表（和数组）可以通过Collection.sort和Arrays.sort进行自动排序。
 * 实现此接口的对象可以用做有序映射中的键或有序集合中的元素，无需指定比较器
 */
public interface Comparable<T> {
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(T o);
}

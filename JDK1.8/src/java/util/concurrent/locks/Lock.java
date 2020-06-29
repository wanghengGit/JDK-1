package java.util.concurrent.locks;
import java.util.concurrent.TimeUnit;

/**
 * @see ReentrantLock
 * @see Condition
 * @see ReadWriteLock
 *
 * @since 1.5
 * @author Doug Lea
 *
 * @date 2019/08/14
 * 当多个线程需要访问某个公共资源的时候，我们知道需要通过加锁来保证资源的访问不会出问题。
 * java提供了两种方式来加锁，一种是关键字：synchronized，一种是concurrent包下的lock锁。
 * synchronized是java底层支持的，而concurrent包则是jdk实现。
 */
public interface Lock {

    void lock();

    void lockInterruptibly() throws InterruptedException;

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

    Condition newCondition();
}

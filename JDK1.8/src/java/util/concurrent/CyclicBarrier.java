package java.util.concurrent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @since 1.5
 * @see CountDownLatch
 *
 * @author Doug Lea
 *
 * @author wangheng
 * @date 2019/08/14
 * CyclicBarrier内部使用了ReentrantLock和Condition两个类
 */
public class CyclicBarrier {
    private static class Generation {
        boolean broken = false;
    }

    /** The lock for guarding barrier entry */
    private final ReentrantLock lock = new ReentrantLock();
    /** Condition to wait on until tripped */
    private final Condition trip = lock.newCondition();
    /** The number of parties */
    private final int parties;
    /* The command to run when tripped */
    private final Runnable barrierCommand;
    /** The current generation */
    private Generation generation = new Generation();

    /**
     * Number of parties still waiting. Counts down from parties to 0
     * on each generation.  It is reset to parties on each new
     * generation or when broken.
     */
    private int count;

    /**
     * Updates state on barrier trip and wakes up everyone.
     * Called only while holding lock.
     */
    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
        generation = new Generation();
    }

    /**
     * Sets current barrier generation as broken and wakes up everyone.
     * Called only while holding lock.
     */
    private void breakBarrier() {
        generation.broken = true;
        count = parties;
        trip.signalAll();
    }

    /**
     * Main barrier code, covering the various policies.
     */
    private int dowait(boolean timed, long nanos)
        throws InterruptedException, BrokenBarrierException,
               TimeoutException {
        // 获取独占锁
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation g = generation;
            // 如果这代损坏了，抛出异常
            if (g.broken)
                throw new BrokenBarrierException();
            // 如果线程中断了，抛出异常
            if (Thread.interrupted()) {
                // 将损坏状态设置为true
                // 并通知其他阻塞在此栅栏上的线程
                breakBarrier();
                throw new InterruptedException();
            }
            // 获取下标
            int index = --count;
            // 如果是 0，说明最后一个线程调用了该方法
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    final Runnable command = barrierCommand;
                    // 执行栅栏任务
                    if (command != null)
                        command.run();
                    ranAction = true;
                    // 更新一代，将count重置，将generation重置
                    // 唤醒之前等待的线程
                    nextGeneration();
                    return 0;
                } finally {
                    // 如果执行栅栏任务的时候失败了，就将损坏状态设置为true
                    if (!ranAction)
                        breakBarrier();
                }
            }

            // loop until tripped, broken, interrupted, or timed out
            for (;;) {
                try {
                    // 如果没有时间限制，则直接等待，直到被唤醒
                    if (!timed)
                        trip.await();
                        // 如果有时间限制，则等待指定时间
                    else if (nanos > 0L)
                        nanos = trip.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    // 当前代没有损坏
                    if (g == generation && ! g.broken) {
                        // 让栅栏失效
                        breakBarrier();
                        throw ie;
                    } else {
                        // We're about to finish waiting even if we had not
                        // been interrupted, so this interrupt is deemed to
                        // "belong" to subsequent execution.
                        // 上面条件不满足，说明这个线程不是这代的
                        // 就不会影响当前这代栅栏的执行，所以，就打个中断标记
                        Thread.currentThread().interrupt();
                    }
                }
                // 当有任何一个线程中断了，就会调用breakBarrier方法
                // 就会唤醒其他的线程，其他线程醒来后，也要抛出异常
                if (g.broken)
                    throw new BrokenBarrierException();
                // g != generation表示正常换代了，返回当前线程所在栅栏的下标
                // 如果 g == generation，说明还没有换代，那为什么会醒了？
                // 因为一个线程可以使用多个栅栏，当别的栅栏唤醒了这个线程，就会走到这里，所以需要判断是否是当前代。
                // 正是因为这个原因，才需要generation来保证正确
                if (g != generation)
                    return index;
                // 如果有时间限制，且时间小于等于0，销毁栅栏并抛出异常
                if (timed && nanos <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }
            }
        } finally {
            // 释放独占锁
            lock.unlock();
        }
    }

    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) throw new IllegalArgumentException();
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }

    public CyclicBarrier(int parties) {
        this(parties, null);
    }

    /**
     * Returns the number of parties required to trip this barrier.
     *
     * @return the number of parties required to trip this barrier
     */
    public int getParties() {
        return parties;
    }

    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0L);
        } catch (TimeoutException toe) {
            throw new Error(toe); // cannot happen
        }
    }

    public int await(long timeout, TimeUnit unit)
        throws InterruptedException,
               BrokenBarrierException,
               TimeoutException {
        return dowait(true, unit.toNanos(timeout));
    }

    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        } finally {
            lock.unlock();
        }
    }

    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();   // break the current generation
            nextGeneration(); // start a new generation
        } finally {
            lock.unlock();
        }
    }

    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}

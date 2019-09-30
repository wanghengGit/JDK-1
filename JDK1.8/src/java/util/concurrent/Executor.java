package java.util.concurrent;

/**
 * @since 1.5
 * @author Doug Lea
 *
 * @author wangheng
 * @date 2019/08/14
 */
public interface Executor {

    void execute(Runnable command);
}

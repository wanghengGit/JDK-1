package java.util.concurrent;

/**
 * @since 1.5
 * @author Doug Lea
 *
 * @date 2019/08/14
 * 一个接口，其定义了一个接收Runnable对象的方法executor，其方法签名为executor(Runnable command)
 */
public interface Executor {

    void execute(Runnable command);
}

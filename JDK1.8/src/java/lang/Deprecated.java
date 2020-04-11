package java.lang;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

/**
 * @author  Neal Gafter
 * @author wangheng
 * @date 2019/10/14
 * @since 1.5
 * @jls 9.6.3.6 @Deprecated
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Deprecated {
}

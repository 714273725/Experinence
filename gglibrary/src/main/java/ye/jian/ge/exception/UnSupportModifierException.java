package ye.jian.ge.exception;

/**
 * Created by Administrator on 2016/8/8.
 */
public class UnSupportModifierException extends Exception {
    public UnSupportModifierException() {

        super();

    }
    public UnSupportModifierException(String msg) {

        super(msg);

    }
    public UnSupportModifierException(String msg, Throwable cause) {

        super(msg, cause);

    }
    public UnSupportModifierException(Throwable cause) {
        super(cause);

    }
}

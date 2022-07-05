package tech.sqlclub.common.exception;

/**
 * 断言异常
 * Created by songgr on 2022/02/23.
 */
public class AssertException extends RuntimeException {


    private static final long serialVersionUID = -2690780657520683564L;

    public AssertException(){

    }

    public AssertException(String message){
        super(message);
    }

    public AssertException(String message, Throwable throwable){
        super(message, throwable);
    }
}

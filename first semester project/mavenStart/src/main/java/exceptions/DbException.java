package exceptions;

public class DbException extends Exception{


    public DbException(){
        super();
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

}

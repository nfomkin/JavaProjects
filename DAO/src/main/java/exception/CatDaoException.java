package exception;

public class CatDaoException extends RuntimeException {

  public CatDaoException() {
    super();
  }

  public CatDaoException(Throwable throwable) {
    super(throwable);
  }
}

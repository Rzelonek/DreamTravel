package pl.edu.wszib.media.store.exceptions;

public class InsufficientBalanceException extends RuntimeException {
      public InsufficientBalanceException(String message) {
            super(message);
      }
}

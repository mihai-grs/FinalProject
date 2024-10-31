package FinalProject.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
         super("The order with ID " + id + " was not found!");
    }
}

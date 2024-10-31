package FinalProject.exception;

public class iPhoneNotFoundException extends RuntimeException {
    public iPhoneNotFoundException(Long id) {
        super("iPhone can't be found by id: " + id);
    }
}

package exception;

public class DuplicateNoteException extends Exception {
    public DuplicateNoteException(String message) {
        super(message);
    }
}
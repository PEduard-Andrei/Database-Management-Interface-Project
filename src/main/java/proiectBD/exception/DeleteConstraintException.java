/** Clasa care arata erorile cauzate de incalcarea constrangerilor la ștergerea elementelor.
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */
package proiectBD.exception;

public class DeleteConstraintException extends RuntimeException {
    public DeleteConstraintException(String message) {
        super(message);
    }
}


package datamapper.exceptions;

/**
 * <p>Exception class used in case an Entity that doens't exist had an atempt
 * to be accessed</p>
 * @author Dechechi
 */
public class NonexistentEntityException extends Exception {
    /**
     * <p>Constructor of the class that throws an exception. It receives as
     * parameters the class of the object that was accessed, the own object
     * and it can receive the Throwable cause or not. If it receives,
     * another constructor that sends a message and the cause is called to
     * send to the Exception extended by this class, if not it just sends a
     * message</p> 
     * @param entity Class of the object
     * @param object Object tried to be accessed
     * @param cause The cause acussed of the exception
     * @throws NonexistentEntityException Own exception class 
     */
    public NonexistentEntityException(Class entity, Object object, Throwable cause) throws NonexistentEntityException {
        String message = "The " + entity.getName() + " with identification " + 
                object.getClass() + " " + object.toString() + " does not exist";
        if (cause == null) {
            throw new NonexistentEntityException(message);
        } else {
            throw new NonexistentEntityException(message, cause);
        }
    }
    /**
     * <p>Constructor used to send the message and cause of the exception
     * treated by this class, to the super class Exception</p>
     * @param message String that represents the message
     * @param cause Throwable cause of the exception
     */
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * <p>Constructor used to send the message of the exception
     * treated by this class to the super class Exception. This one
     * does not receive a cause</p>
     * @param message String that represents the message
     */
    public NonexistentEntityException(String message) {
        super(message);
    }
}

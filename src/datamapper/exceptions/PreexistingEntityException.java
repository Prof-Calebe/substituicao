package datamapper.exceptions;



public class PreexistingEntityException extends Exception {

    /**
     * <p>Constructor used to throws an exception. It receives as parameters the 
     * class of the object, the object itself and the throwable cause. If there's
     * a cause then another constructor that sends message and the cause is called to send
     * to the Exception extended by this class, but if not it, just sends a message</p>
     * @param entity Class of the object
     * @param object Object that already exist and tried to be inserted
     * @param cause Throwable cause of the exception
     * @throws PreexistingEntityException Own exception class
     */
    public PreexistingEntityException(Class entity, Object object, Throwable cause) throws PreexistingEntityException{
        String message = "The "+entity.getName() + " with identification "+object.getClass() + " already exists "+object.toString();
        if(cause==null){
        throw new PreexistingEntityException(message, cause);
        }else{
           throw new PreexistingEntityException(message); 
        }
    }
    
    /**
     * <p>Constructor used to send the message and cause of the exception treated
     * by this class to the super class</p>
     * @param message String representing the message
     * @param cause  Throwable cause of the exception
     */
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * <p>Constructor used to send the message of the exception treated
     * by this class to the super class</p>
     * @param message String representing the message
     */
    public PreexistingEntityException(String message) {
        super(message);
    }
}

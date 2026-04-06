package br.com.animator.window.exceptions;

/**
 * Exception thrown when the system does not support full screen mode.
 */
public class FullScreenNotSupportedException extends Exception {
    
    /**
     * Constructor
     */
    public FullScreenNotSupportedException() {
        super("Full screen mode is not supported on this system.");
    }

    /**
     * Constructor
     * @param message
     */
    public FullScreenNotSupportedException(String message) {
        super(message);
    }	
}
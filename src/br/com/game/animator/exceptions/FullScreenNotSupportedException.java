package br.com.game.animator.exceptions;

/**
 * Exception thrown when the system does not support full screen mode.
 */
public class FullScreenNotSupportedException extends Exception {
    
    /**
     * Constructor
     */
    public FullScreenNotSupportedException() {
        super("O modo de tela cheia não é suportado neste sistema.");
    }

    /**
     * Constructor
     * @param message
     */
    public FullScreenNotSupportedException(String message) {
        super(message);
    }	
}
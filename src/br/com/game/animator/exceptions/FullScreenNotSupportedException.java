package br.com.game.animator.exceptions;

/**
 * Exceção lançada quando o sistema não suporta o modo de tela cheia.
 */
public class FullScreenNotSupportedException extends Exception {
    /**
     * Construtor padrão da exceção.
     */
    public FullScreenNotSupportedException() {
        super("O modo de tela cheia não é suportado neste sistema.");
    }

    /**
     * Construtor da exceção com uma mensagem personalizada.
     *
     * @param message A mensagem de erro personalizada.
     */
    public FullScreenNotSupportedException(String message) {
        super(message);
    }	
}
package leandro.online.library.exception;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String err) {
        super(err);
    }
}

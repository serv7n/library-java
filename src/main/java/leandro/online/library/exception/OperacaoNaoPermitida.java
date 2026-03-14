package leandro.online.library.exception;

public class OperacaoNaoPermitida extends RuntimeException{
    public OperacaoNaoPermitida(String err) {
        super(err);
    }
}

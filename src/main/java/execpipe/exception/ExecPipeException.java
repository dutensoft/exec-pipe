package execpipe.exception;

import java.io.Serializable;

public class ExecPipeException extends Exception implements Serializable {
    private static final long serialVersionUID = 9214116643527743848L;

    public ExecPipeException(String message) {
        super(message);
    }
}

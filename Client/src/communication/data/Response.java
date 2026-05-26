package communication.data;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private ResponseStatus status;
    private String message;
    private Object result;

    public Response(Object result, ResponseStatus status, String message) {
        this.result = result;
        this.status = status;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
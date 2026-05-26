package communication.data;

import java.io.Serializable;

public class RequestComm implements Serializable {

    private static final long serialVersionUID = 1L;

    private InquiryManagerActions action;
    private Object[] requestParameters;

    public RequestComm(InquiryManagerActions action, Object... requestParameters) {
        this.action = action;
        this.requestParameters = requestParameters;
    }

    public InquiryManagerActions getAction() {
        return action;
    }

    public Object[] getData() {
        return requestParameters;
    }
}
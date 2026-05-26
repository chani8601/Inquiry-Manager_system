package data;


import java.io.*;
import java.net.ProtocolFamily;
import java.time.LocalDateTime;

public abstract class Inquiry implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;
    protected String type;

    public void fillData(String description,String type) {
        this.description=description;
        this.creationDate=LocalDateTime.now();
        this.type=type;
    }

    public void handling(){
        System.out.println("Inquiry inquiry code: "+this.code);
    }

    private INQUIRY_STATUS status;

    public INQUIRY_STATUS getStatus() {
        return status;
    }

    public void setStatus(INQUIRY_STATUS status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Code: " + code +
                ", Description: " + description +
                ", Date: " + creationDate;
    }
}

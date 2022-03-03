package kimsungsu.finalToBoot.entity.form;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Message {

    private HttpStatus status;
    private String message;
    private Object data;

    public Message() {
        status = HttpStatus.BAD_REQUEST;
        message = null;
        data = null;
    }
}

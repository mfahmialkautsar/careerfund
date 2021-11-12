package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseTemplate {
    private String code;
    private String message;

    public ResponseTemplate(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ResponseTemplate responseSuccess() {
        return new ResponseTemplate("Success", "200");
    }

    public ResponseTemplate responseSuccess(String message) {
        return new ResponseTemplate(message, "200");
    }

    public ResponseTemplate responseNotFound() {
        return new ResponseTemplate("Not Found", "404");
    }

    public ResponseTemplate responseBadRequest() {
        return new ResponseTemplate("Bad Request", "400");
    }

    public ResponseTemplate responseBadRequest(String message) {
        return new ResponseTemplate(message, "400");
    }

}




package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseTemplate {
    private String code;
    private String message;

    public ResponseTemplate(String message, String code) {
        this.message = message;
        this.code = code;
    }
}




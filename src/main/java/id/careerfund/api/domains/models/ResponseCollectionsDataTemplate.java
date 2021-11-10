package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public class ResponseCollectionsDataTemplate extends ResponseTemplate {
    private Collection data;

    public ResponseCollectionsDataTemplate(String statusMessage, String statusCode, Collection data) {
        super(statusMessage, statusCode);
        this.data = data;
    }
}

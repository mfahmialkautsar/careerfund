package id.careerfund.api.domains.models;

import lombok.Data;

@Data
public class Response<T> {
    private String status;
    private T data;

    public Response() {
    }

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }
}

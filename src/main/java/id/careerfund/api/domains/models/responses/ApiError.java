package id.careerfund.api.domains.models.responses;

import lombok.Getter;

@Getter
public class ApiError extends ErrorResponse {
    private final int code;

    public ApiError(String message, int code) {
        super(message);
        this.code = code;
    }
}

package id.careerfund.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private final Boolean success;

    public static ApiResponse success() {
        return new ApiResponse(true);
    }

    public static ApiResponse error() {
        return new ApiResponse(false);
    }
}

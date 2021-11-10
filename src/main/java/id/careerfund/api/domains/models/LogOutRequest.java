package id.careerfund.api.domains.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {
    private String refreshtoken;
}

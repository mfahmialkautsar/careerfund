package id.careerfund.api.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceInfo {
    private String userAgent;
    private String remoteAddr;
}

package id.careerfund.api.domains.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceInfo extends ResponseTemplate {
    private String userAgent;
    private String remoteAddr;

    public DeviceInfo(String statusMessage, String statusCode, String userAgent, String remoteAddr) {
        super(statusMessage, statusCode);
        this.userAgent = userAgent;
        this.remoteAddr = remoteAddr;
    }
}

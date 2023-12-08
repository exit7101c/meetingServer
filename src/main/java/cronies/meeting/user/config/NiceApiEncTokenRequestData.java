package cronies.meeting.user.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class NiceApiEncTokenRequestData {

    private dataHeader dataHeader;
    private dataBody dataBody;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class dataHeader {
        private String CNTY_CD;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class dataBody {
        private String req_dtim;
        private String req_no;
        private String enc_mode;
    }
}

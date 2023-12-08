package cronies.meeting.user.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageMulit {


    private String click_action;
    private String notification_foreground;
    private String nick;
    private String regUserKey;
    private String regTime;
    private String formatTime;
    private String imageId;
    private String messageType;
    private String regUserId;
    private String messageId;
    private String message;
    private String fileId;
    private String chatroomId;
    private String cdnThumbNm;
    private String cdnNmImage;
    private String pushType;
    private String openChatKey;
    private String articleKey;


}

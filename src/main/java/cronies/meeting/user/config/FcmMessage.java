package cronies.meeting.user.config;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {

    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
        private Data data;
        private Android android;
        private Apns apns;
    }


    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
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

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private Notification notification;

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Notification {
            private String sound;
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Apns {
        private Payload payload;

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Payload {
            private Aps aps;

            @Builder
            @AllArgsConstructor
            @Getter
            public static class Aps {
                private String sound;
            }
        }
    }
}

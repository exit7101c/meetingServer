package cronies.meeting.user.service.impl;



import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import cronies.meeting.user.config.FcmMessage;
import cronies.meeting.user.config.FcmMessageMulit;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.FcmService;
import cronies.meeting.user.service.PushService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("fcmService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@EnableAsync
public class FcmServiceImpl implements FcmService {


	Logger log = LoggerFactory.getLogger(this.getClass());

	//FCM을 위한 처리 시작

	private String API_URL = "https://fcm.googleapis.com/v1/projects/navy-69d5c/messages:send";
	private String API_URL_MULTI = "https://fcm.googleapis.com/fcm/send";

	//@Autowired
	//private ObjectMapper objectMapper;

	@Async
	public void sendMessageTo(String targetToken, String title, String body) throws IOException {

		String message = makeMessage(targetToken, title, body, "else");

		try {

			OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);
			Request request = new Request.Builder()
					.url(API_URL)
					.post(requestBody)
					.addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer "+ getAccessToken())
					.addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
					.build();

			Response response = client.newCall(request).execute();
			//log.info(response.body().string());

		} catch (Exception e){
			System.out.println(e);
		}


	}

	@Async
	public void sendMessageTo(String pushType, String targetToken, String title, String body) throws IOException {

		String message = makeMessage(targetToken, title, body, pushType);

		try {

			OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);
			Request request = new Request.Builder()
					.url(API_URL)
					.post(requestBody)
					.addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer "+ getAccessToken())
					.addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
					.build();

			Response response = client.newCall(request).execute();
			//log.info(response.body().string());

		} catch (Exception e){
			System.out.println(e);
		}


	}

	@Async
	public void sendMessageTo(String pushType, String targetToken, String title, String body, HashMap<String, Object> data) throws IOException {
		String message = "";
		if(pushType.equals("message")){
			message = makeMessageForMessage(targetToken, title, body, data);
		} else if(pushType.equals("openChat")) {
			message = makeMessageForOpenChat(targetToken, title, body, data);
		} else if(pushType.equals("community")) {
			message = makeMessageForCommunity(targetToken, title, body, data);
		} else if(pushType.equals("myLikeMatching")){
			message = makeMessageForMatching(targetToken, title, body, data);
		} else if(pushType.equals("communityChat")){
			message = makeMessageForCommunityChat(targetToken, title, body, data);
		} else {
			message = makeMessage(targetToken, title, body, "else");
		}

		try {

			OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);
			Request request = new Request.Builder()
					.url(API_URL)
					.post(requestBody)
					.addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer "+ getAccessToken())
					.addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
					.build();

			Response response = client.newCall(request).execute();
			//log.info(response.body().string());

		} catch (Exception e){
			System.out.println(e);
		}


	}


	@Async
	public void sendMessageToMulti(String pushType, List<String> tokenList, String title, String body, HashMap<String, Object> data) throws IOException {

		//String dataJson = makeMessageForMessageMulti(tokenList, title, body, data);

		/*System.out.println("전송시작");
		System.out.println(dataJson);*/

		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("click_action", "NOTIFICATION_CLICK");
		dataMap.put("notification_foreground", "true");
		dataMap.put("nick", data.get("nick").toString());
		dataMap.put("regUserKey", data.get("regUserKey").toString());
		dataMap.put("regTime", data.get("regTime").toString());
		dataMap.put("formatTime", data.get("formatTime").toString());
		dataMap.put("imageId", data.get("imageId").toString());
		dataMap.put("messageType", data.get("messageType").toString());
		dataMap.put("regUserId", data.get("regUserId").toString());
		dataMap.put("messageId", data.get("messageId").toString());
		dataMap.put("message", data.get("message").toString());
		dataMap.put("fileId", data.get("fileId").toString());
		dataMap.put("chatroomId", data.get("chatroomId").toString());
		dataMap.put("cdnThumbNm", data.get("cdnThumbNm").toString());
		dataMap.put("cdnNmImage", data.get("cdnNmImage").toString());
		dataMap.put("pushType", pushType);


		try {

			if (tokenList.size() > 0) {
				MulticastMessage message = MulticastMessage.builder()
						.setNotification(Notification.builder().setTitle(title).setBody(body).build())
						.addAllTokens(tokenList)
						.putAllData(dataMap)
						.setAndroidConfig(
								AndroidConfig.builder()
										.setNotification(
												AndroidNotification.builder()
													.setSound("default")
													.build()
										)
										.build()
						)
						.setApnsConfig(
								ApnsConfig.builder()
										.setAps(Aps.builder()
											.setSound("default")
											.build()
										)
										.build()
						)
						//.putData("data", dataJson)
						.build();
				try {
					FirebaseMessaging.getInstance().sendMulticast(message);

				} catch (FirebaseMessagingException e) {
					System.out.println( e);
				}
			}

			/*OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);
			Request request = new Request.Builder()
					.url(API_URL)
					.post(requestBody)
					.addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer "+ getAccessToken())
					.addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
					.build();

			Response response = client.newCall(request).execute();*/



			/*System.out.println("response @@@ ");
			System.out.println(response.body().string());*/
			//log.info(response.body().string());

		} catch (Exception e){
			System.out.println(e);
		}


	}

	private String makeMessage(String targetToken, String title, String body, String pushType) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
					.token(targetToken)
					.notification(FcmMessage.Notification.builder()
						.title(title)
						.body(body)
						.image(null)
						.build()
					)
					.data(FcmMessage.Data.builder()
						.click_action("NOTIFICATION_CLICK")
						.notification_foreground("true")
						.pushType(pushType)
						.build()
					)
					.android(FcmMessage.Android.builder()
							.notification(FcmMessage.Android.Notification.builder()
									.sound("default")
									.build()
							)
							.build()
					)
					.apns(FcmMessage.Apns.builder()
							.payload(FcmMessage.Apns.Payload.builder()
									.aps(FcmMessage.Apns.Payload.Aps.builder()
											.sound("default")
											.build()
									)
									.build()
							)
							.build()
					)
					.build()
			)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForMessage(String targetToken, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
						.token(targetToken)
						.notification(FcmMessage.Notification.builder()
								.title(title)
								.body(body)
								.image(null)
								.build()
						)
						.data(FcmMessage.Data.builder()
							.click_action("NOTIFICATION_CLICK")
							.notification_foreground("true")
							.nick(data.get("nick").toString())
							.regUserKey(data.get("regUserKey").toString())
							.regTime(data.get("regTime").toString())
							.formatTime(data.get("formatTime").toString())
							.imageId(data.get("imageId").toString())
							.messageType(data.get("messageType").toString())
							.regUserId(data.get("regUserId").toString())
							.messageId(data.get("messageId").toString())
							.message(data.get("message").toString())
							.fileId(data.get("fileId").toString())
							.chatroomId(data.get("chatroomId").toString())
							.cdnThumbNm(data.get("cdnThumbNm").toString())
							.cdnNmImage(data.get("cdnNmImage").toString())
							.pushType("message")
							.build()
						)
						.android(FcmMessage.Android.builder()
							.notification(FcmMessage.Android.Notification.builder()
								.sound("default")
								.build()
							)
							.build()
						)
						.apns(FcmMessage.Apns.builder()
							.payload(FcmMessage.Apns.Payload.builder()
								.aps(FcmMessage.Apns.Payload.Aps.builder()
									.sound("default")
									.build()
								)
								.build()
							)
							.build()
						)
						.build()
				)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForMessageMulti(List<String> tokenList, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessageMulit fcmMessage = FcmMessageMulit.builder()
						.click_action("NOTIFICATION_CLICK")
						.notification_foreground("true")
						.nick(data.get("nick").toString())
						.regUserKey(data.get("regUserKey").toString())
						.regTime(data.get("regTime").toString())
						.formatTime(data.get("formatTime").toString())
						.imageId(data.get("imageId").toString())
						.messageType(data.get("messageType").toString())
						.regUserId(data.get("regUserId").toString())
						.messageId(data.get("messageId").toString())
						.message(data.get("message").toString())
						.fileId(data.get("fileId").toString())
						.chatroomId(data.get("chatroomId").toString())
						.cdnThumbNm(data.get("cdnThumbNm").toString())
						.cdnNmImage(data.get("cdnNmImage").toString())
						.pushType("message")
						.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForOpenChat(String targetToken, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
						.token(targetToken)
						.notification(FcmMessage.Notification.builder()
								.title(title)
								.body(body)
								.image(null)
								.build()
						)
						.data(FcmMessage.Data.builder()
								.click_action("NOTIFICATION_CLICK")
								.notification_foreground("true")
								.openChatKey(data.get("openChatKey").toString())
								.pushType("openChat")
								.build()
						)
						.android(FcmMessage.Android.builder()
								.notification(FcmMessage.Android.Notification.builder()
										.sound("default")
										.build()
								)
								.build()
						)
						.apns(FcmMessage.Apns.builder()
								.payload(FcmMessage.Apns.Payload.builder()
										.aps(FcmMessage.Apns.Payload.Aps.builder()
												.sound("default")
												.build()
										)
										.build()
								)
								.build()
						)
						.build()
				)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForCommunity(String targetToken, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
						.token(targetToken)
						.notification(FcmMessage.Notification.builder()
								.title(title)
								.body(body)
								.image(null)
								.build()
						)
						.data(FcmMessage.Data.builder()
								.click_action("NOTIFICATION_CLICK")
								.notification_foreground("true")
								.articleKey(data.get("articleKey").toString())
								.pushType("community")
								.build()
						)
						.android(FcmMessage.Android.builder()
								.notification(FcmMessage.Android.Notification.builder()
										.sound("default")
										.build()
								)
								.build()
						)
						.apns(FcmMessage.Apns.builder()
								.payload(FcmMessage.Apns.Payload.builder()
										.aps(FcmMessage.Apns.Payload.Aps.builder()
												.sound("default")
												.build()
										)
										.build()
								)
								.build()
						)
						.build()
				)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForMatching(String targetToken, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
						.token(targetToken)
						.notification(FcmMessage.Notification.builder()
								.title(title)
								.body(body)
								.image(null)
								.build()
						)
						.data(FcmMessage.Data.builder()
								.click_action("NOTIFICATION_CLICK")
								.notification_foreground("true")
								.chatroomId(data.get("chatroomId").toString())
								.pushType("myLikeMatching")
								.build()
						)
						.android(FcmMessage.Android.builder()
								.notification(FcmMessage.Android.Notification.builder()
										.sound("default")
										.build()
								)
								.build()
						)
						.apns(FcmMessage.Apns.builder()
								.payload(FcmMessage.Apns.Payload.builder()
										.aps(FcmMessage.Apns.Payload.Aps.builder()
												.sound("default")
												.build()
										)
										.build()
								)
								.build()
						)
						.build()
				)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String makeMessageForCommunityChat(String targetToken, String title, String body, HashMap<String, Object> data) throws JsonProcessingException {
		FcmMessage fcmMessage = FcmMessage.builder()
				.message(FcmMessage.Message.builder()
						.token(targetToken)
						.notification(FcmMessage.Notification.builder()
								.title(title)
								.body(body)
								.image(null)
								.build()
						)
						.data(FcmMessage.Data.builder()
								.click_action("NOTIFICATION_CLICK")
								.notification_foreground("true")
								.chatroomId(data.get("chatroomId").toString())
								.pushType("myLikeMatching")
								.build()
						)
						.android(FcmMessage.Android.builder()
								.notification(FcmMessage.Android.Notification.builder()
										.sound("default")
										.build()
								)
								.build()
						)
						.apns(FcmMessage.Apns.builder()
								.payload(FcmMessage.Apns.Payload.builder()
										.aps(FcmMessage.Apns.Payload.Aps.builder()
												.sound("default")
												.build()
										)
										.build()
								)
								.build()
						)
						.build()
				)
				.validate_only(false)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String getAccessToken() throws IOException {
		String firebaseConfigPath = "fcmConfig/navy-69d5c-firebase-adminsdk-ifnll-6cf4099089.json";

		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
				.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
		googleCredentials.refreshIfExpired();

		return googleCredentials.getAccessToken().getTokenValue();
	}



}
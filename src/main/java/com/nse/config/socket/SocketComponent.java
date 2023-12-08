package com.nse.config.socket;

import com.cronies.smartmes.service.SmsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import cronies.meeting.user.service.FcmService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import select.spring.exquery.service.ExqueryService;
import select.spring.util.ApplicationProperty;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;


@Component
public class SocketComponent implements ApplicationListener<BrokerAvailabilityEvent> {

    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public SocketComponent(
        final MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(final BrokerAvailabilityEvent event) {
    }

    @Autowired
	private ExqueryService exqueryService;

    @Autowired
	private SmsService smsService;

	@Autowired
	FcmService fcmService;


    //배치 작동 여부
  	private String batchRun = ApplicationProperty.get("batch.run");

  	//스케줄 사용여부
  	private boolean isScheduled(String batchCd) {

  		boolean isScheduleYn = false;
  		if(batchRun.toUpperCase().equals("YES") &&
			(ApplicationProperty.get("batch." + batchCd)).toUpperCase().equals("YES")) {
  			isScheduleYn = true;
  		}
  		return isScheduleYn;
  	}


	//선언
	private int lastNo = 0;//마지막 처리한 번호
	private HashMap<String, Object> batchMap = new HashMap<String, Object>();
	private HashMap<String, Object> lastMap = new HashMap<String, Object>();

	/**
	 * 대화내용
	 * @throws Exception
	 * @repeat
	 */
	@Scheduled(cron="*/1 * * * * *")
	public void checkSystem() throws Exception {
		String BATCH_CD = "messageSend";

		batchMap.put("batchCd", BATCH_CD);

		//스케쥴사용여부가 no면 진행하지 않음
		if(!isScheduled(BATCH_CD)){
			return;
		}
		//this.messagingTemplate.convertAndSend("/channel/10" , batchMap);
		//최초시작
	/*	if(lastNo == 0){
			//이전에 마지막으로 처리한 lastNo를 DB에서 조회한다.
			lastMap = exqueryService.selectOne("cronies.app.message.getSockLastNo", batchMap);
			lastNo = Integer.parseInt(lastMap.get("contentSeq").toString());
		}

		//lastNo보다 큰 메세지 정보 가져옴
		List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.message.getChatList", lastMap);

		System.out.println(lastNo);

		// 리스트 갯수만큼 반복
		for(int i=0, len = resultList.size(); i < len; i++) {
			this.messagingTemplate.convertAndSend("/channel/" + resultList.get(i).get("channelId"), resultList.get(i));
			lastNo++;
		}

		//마지막으로 처리한 LastNo 를 DB에 저장.
		lastMap.replace("contentSeq", lastNo);
		batchMap.put("lastNo", lastNo);
		exqueryService.update("cronies.app.message.updateBatch", batchMap);*/

	}
}






















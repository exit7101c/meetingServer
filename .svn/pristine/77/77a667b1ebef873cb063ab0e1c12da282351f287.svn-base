package cronies.meeting.user.purchase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.api.services.androidpublisher.model.ProductPurchasesAcknowledgeRequest;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.google.api.services.androidpublisher.model.SubscriptionPurchasesAcknowledgeRequest;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import cronies.meeting.user.purchase.utils.AppleUtils;
import cronies.meeting.user.service.PointService;
import cronies.meeting.user.service.StoreProcessService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value="purchase")
public class PurchaseController {

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private PointService pointService;

    @Autowired
    private StoreProcessService storeProcessService;

    private AppleUtils appleUtils = new AppleUtils();

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "purchaseRecoveryOne", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> purchaseRecoveryOne(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("cronies.app.storeCommon.purchaseRecoveryOne", param);
        if(resultMap == null){
            resultMap = new HashMap<String, Object>();
        }
        return resultMap;
    }

    @RequestMapping(value = "transaction", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public char purchaseTransaction(@RequestParam HashMap<String, Object> param) throws Exception {
        char returnCharY = 'Y';
        char returnCharN = 'N';

        if(!param.containsKey("ssUserId")){
            return returnCharN;
        }
        HashMap<String, Object> resultMap = exqueryService.selectOne("cronies.app.storeCommon.selectPurchaseReceiptOne", param);

        if(resultMap == null){
            if (exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge1", param) > 0) {
                return returnCharY;
            }
            return returnCharN;
        } else {
            return returnCharY;
        }
    }


    @RequestMapping(value = "apple", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> applePurchase(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> pointMap = new HashMap<String, Object>();
        String successYn = "Y";
        String isRealYn = "N";
        String message = "";
        Integer cnttt = 0;

        ObjectMapper om = new ObjectMapper();

        try {
            // apple에서 보내주는 영수증 검증 데이터
            HashMap<String, Object> appStoreResponse = appleUtils.verifyReceipt(param);
            // 영수증 관련 정보만 필요하므로 receipt만 따로 조회
            //HashMap<String, Object> receipt = (HashMap<String, Object>)appStoreResponse.get("receipt");
            List<HashMap<String, Object>> inApp = (List<HashMap<String, Object>>)appStoreResponse.get("latest_receipt_info");
            // in_app 내 array 가져오기 (구매가 정상처리되지 못해 쌓여있거나 구매 이력 등..)
            //List<HashMap<String, Object>> inApp = (List<HashMap<String, Object>>)receipt.get("latest_receipt_info");


            Iterator<HashMap<String, Object>> iterator = inApp.iterator();
            while(iterator.hasNext()) {
                // 쌓여있는 데이터 각각 분리
                HashMap<String, Object> inAppData = iterator.next();

                // 저장할 데이터 담고있을 Map
                HashMap<String, Object> resultMap2 = new HashMap<>();

                resultMap2.put("productId", inAppData.get("product_id").toString());
                resultMap2.put("transactionId", inAppData.get("transaction_id").toString());
                resultMap2.put("ssUserId", param.get("ssUserId"));
                resultMap2.put("purchaseType", "consumable");

                String purchaseUnixTimeStamp = inAppData.get("purchase_date_ms").toString();
                long purchaseTimestamp = Long.parseLong(purchaseUnixTimeStamp);

                Date date = new Date();
                date.setTime(purchaseTimestamp);
                String Datetime = formatter.format(date);
                resultMap2.put("purchaseDate", Datetime);

                // 구독상품 체크 후 만료일자 및 purchaseType 변경
                for (String key : inAppData.keySet()) {
                    if (key.equals("product_id") && inAppData.get(key).toString().toLowerCase().contains("subscription")) {
                        resultMap2.put("purchaseType", "subscription");

                        String expUnixTimeStamp = inAppData.get("expires_date_ms").toString();
                        long expTimestamp = Long.parseLong(expUnixTimeStamp);

                        Date date2 = new Date();
                        date2.setTime(expTimestamp);
                        String Datetime2 = formatter.format(date2);
                        resultMap2.put("expiresDate", Datetime2);
                    }
                }

                param.put("productId", resultMap2.get("productId"));
                param.put("transactionId", resultMap2.get("transactionId"));
                param.put("purchaseType", resultMap2.get("purchaseType"));
                param.put("purchaseDate", resultMap2.get("purchaseDate"));
                param.put("expiresDate", resultMap2.get("expiresDate"));


//                HashMap<String, Object> selectPurchaseMap = exqueryService.selectOne("cronies.app.storeCommon.selectPurchaseReceiptOne", param);
//                if(selectPurchaseMap == null) {
//                    exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge2", resultMap2);
////                    if (exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge2", resultMap2) > 0) {
////                        successYn = "Y";
////                    }
//                }

                HashMap<String, Object> returnPurchaseMap = exqueryService.selectOne("cronies.app.storeCommon.getPurchaseHisByTransactionForApple", param);
                cnttt = cnttt+1;
                if(returnPurchaseMap != null && !MapUtils.isEmpty(returnPurchaseMap)){
                    if(!returnPurchaseMap.get("endYn").equals("Y")){
                        exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge2", resultMap2);
                        isRealYn = "Y";
                        if(resultMap2.get("purchaseType").toString().equals("subscription")){
                            // 구독 일 시
                            // subscribeCd, pointCd 필요
                            // subscribeCd를 알기위해 조회
                            HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getSubscribeByProductId", param);
                            if(returnMap != null && !MapUtils.isEmpty(returnMap)){
                                param.put("subscribeCd", returnMap.get("subscribeCd"));
                                param.put("pointCd", "BUY");
                                resultMap = storeProcessService.buySubscribeComplete(param);
                                exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                            }
                        } else {
                            // 하트 구매 일 시
                            // itemCd, pointCd 필요
                            // itemCd를 알기위해 조회
                            HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getItemByProductId", param);
                            if(returnMap != null && !MapUtils.isEmpty(returnMap)){
                                param.put("itemCd", returnMap.get("itemCd"));
                                param.put("pointCd", "BUY");
                                resultMap = pointService.setPointByBuyHeart(param);
                                exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException | JsonProcessingException e) {
            e.printStackTrace();
            resultMap.put("successYn", "N");
            resultMap.put("message", "구매 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
            return resultMap;
        }

        param.put("userId", param.get("ssUserId"));
        pointMap = exqueryService.selectOne("cronies.app.point.getUserLastPoint", param);
        resultMap.put("successYn", successYn);
        resultMap.put("message", message);
        resultMap.put("lastPoint", pointMap.get("lastPoint"));
        resultMap.put("isRealYn", isRealYn);
        return resultMap;
    }

    @RequestMapping(value = "google", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> googlePurchase(@RequestParam HashMap<String, Object> param) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> pointMap = new HashMap<String, Object>();
        String successYn = "Y";
        String isRealYn = "N";
        String message = "";

        final String googleAccountFilePath = "googleApiConfig/google_api_key.json";
        final String googleApplicationPackageName = "cmdg.navy.client";

        InputStream inputStream = new ClassPathResource(googleAccountFilePath).getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream).createScoped(AndroidPublisherScopes.ANDROIDPUBLISHER);

        try {
            AndroidPublisher publisher = new AndroidPublisher.Builder(
                                            GoogleNetHttpTransport.newTrustedTransport(),
                                            GsonFactory.getDefaultInstance(),
                                            new HttpCredentialsAdapter(credentials)
                                        ).setApplicationName(googleApplicationPackageName)
                                         .build();

            if (!param.get("productId").toString().contains("subscription")) {  // 소모품
                // 소모품
                AndroidPublisher.Purchases.Products.Get productGet = publisher
                                                                    .purchases()
                                                                    .products()
                                                                    .get(googleApplicationPackageName, param.get("productId").toString(), param.get("purchaseToken").toString());
                ProductPurchase productPurchase = productGet.execute();

                if (productPurchase.getPurchaseState() == 0) {
                    ProductPurchasesAcknowledgeRequest content = new ProductPurchasesAcknowledgeRequest();
                    content.setDeveloperPayload(param.get("developerPayload") == null ? "" : param.get("developerPayload").toString());
                    // 결제 승인 처리 (승인하지 않으면 3일뒤 사용자에게 환불됨)
                    AndroidPublisher.Purchases.Products.Acknowledge result = publisher.purchases().products().acknowledge(googleApplicationPackageName, param.get("productId").toString(), param.get("purchaseToken").toString(), content);

                    // 결제 승인 완료 이후
                    long purchaseTimestamp = productPurchase.getPurchaseTimeMillis();
                    Date purchaseDate = new Date();
                    purchaseDate.setTime(purchaseTimestamp);
                    String purchaseDatetime = formatter.format(purchaseDate);

                    param.put("productId", param.get("productId"));
                    param.put("transactionId", param.get("transactionId"));
                    param.put("purchaseType", "consumable");
                    param.put("purchaseDate", purchaseDatetime);

                    HashMap<String, Object> returnPurchaseMap = exqueryService.selectOne("cronies.app.storeCommon.getPurchaseHisByTransaction", param);
                    if (returnPurchaseMap != null && !MapUtils.isEmpty(returnPurchaseMap)) {
                        if (!returnPurchaseMap.get("endYn").equals("Y")) {
                            exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge2", param);
                            isRealYn = "Y";
                            if (param.get("purchaseType").toString().equals("subscription")) {
                                // 구독 일 시
                                // subscribeCd, pointCd 필요
                                // subscribeCd를 알기위해 조회
                                HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getSubscribeByProductId", param);
                                if (returnMap != null) {
                                    param.put("subscribeCd", returnMap.get("subscribeCd"));
                                    param.put("pointCd", "BUY");
                                    resultMap = storeProcessService.buySubscribeComplete(param);
                                    exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                                }
                            } else {
                                // 하트 구매 일 시
                                // itemCd, pointCd 필요
                                // itemCd를 알기위해 조회
                                HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getItemByProductId", param);
                                if (returnMap != null) {
                                    param.put("itemCd", returnMap.get("itemCd"));
                                    param.put("pointCd", "BUY");
                                    resultMap = pointService.setPointByBuyHeart(param);
                                    exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                                }
                            }
                        }
                    }
                } else if (productPurchase.getPurchaseState() == 1) { // 결제가 처리되지 않은 경우, 사용자가 취소했거나 결제중 네트워크 문제 및 앱 튕김 현상 등
                    // TODO : 재귀?
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "구매 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                    return resultMap;
                }
            } else { // 구독
                // 구독상품
                // AndroidPublisher.Purchases.Subscriptions
                // 관련 메서드
                // 갱신 취소 : cancel, 구독 환불 : refund, 구독 철회 : revoke, 구매 연기: defer
                AndroidPublisher.Purchases.Subscriptions.Get subcriptionGet = publisher
                                                                             .purchases()
                                                                             .subscriptions()
                                                                             .get(googleApplicationPackageName, param.get("productId").toString(), param.get("purchaseToken").toString());
                SubscriptionPurchase subscriptionPurchase = subcriptionGet.execute();

                if (subscriptionPurchase.getPaymentState() == 1 && subscriptionPurchase.getAcknowledgementState() == 0) { // 결제 처리 됨 && 구독 승인 전, getAcknowledgementState() == 1 : 구독 승인 됨
                    SubscriptionPurchasesAcknowledgeRequest content = new SubscriptionPurchasesAcknowledgeRequest();
                    content.setDeveloperPayload(param.get("developerPayload") == null ? "" : param.get("developerPayload").toString());
                    // 구독 승인 처리 (승인하지 않으면 3일뒤 사용자에게 환불됨)
                    AndroidPublisher.Purchases.Subscriptions.Acknowledge result = publisher.purchases().subscriptions().acknowledge(googleApplicationPackageName, param.get("productId").toString(), param.get("purchaseToken").toString(), content);

                    // 결제 승인 완료 이후
                    long purchaseTimestamp = subscriptionPurchase.getStartTimeMillis();
                    Date purchaseDate = new Date();
                    purchaseDate.setTime(purchaseTimestamp);
                    String purchaseDatetime = formatter.format(purchaseDate);

                    long expTimestamp = subscriptionPurchase.getExpiryTimeMillis();
                    Date expDate = new Date();
                    expDate.setTime(expTimestamp);
                    String expDatetime = formatter.format(expDate);

                    param.put("productId", param.get("productId"));
                    param.put("transactionId", param.get("transactionId"));
                    param.put("purchaseType", "subscription");
                    param.put("purchaseDate", purchaseDatetime);
                    param.put("expiresDate", expDatetime);


                    HashMap<String, Object> returnPurchaseMap = exqueryService.selectOne("cronies.app.storeCommon.getPurchaseHisByTransaction", param);
                    if (returnPurchaseMap != null && !MapUtils.isEmpty(returnPurchaseMap)) {
                        if (!returnPurchaseMap.get("endYn").equals("Y")) {
                            exqueryService.update("cronies.app.storeCommon.purchaseReceiptMerge2", param);
                            if (param.get("purchaseType").toString().equals("subscription")) {
                                // 구독 일 시
                                // subscribeCd, pointCd 필요
                                // subscribeCd를 알기위해 조회
                                HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getSubscribeByProductId", param);
                                if (returnMap != null) {
                                    param.put("subscribeCd", returnMap.get("subscribeCd"));
                                    param.put("pointCd", "BUY");
                                    resultMap = storeProcessService.buySubscribeComplete(param);
                                    exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                                }
                            } else {
                                // 하트 구매 일 시
                                // itemCd, pointCd 필요
                                // itemCd를 알기위해 조회
                                HashMap<String, Object> returnMap = exqueryService.selectOne("cronies.app.storeCommon.getItemByProductId", param);
                                if (returnMap != null) {
                                    param.put("itemCd", returnMap.get("itemCd"));
                                    param.put("pointCd", "BUY");
                                    resultMap = pointService.setPointByBuyHeart(param);
                                    exqueryService.update("cronies.app.storeCommon.updatePurchaseReceiptEndYn", param);
                                }
                            }
                        }
                    }
                } else if (subscriptionPurchase.getPaymentState() == 0) { // 결제가 아직 처리되지 않음
                    // TODO : 재귀?
                    resultMap.put("successYn", successYn);
                    resultMap.put("message", "구매 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                    return resultMap;
                } else if (subscriptionPurchase.getPaymentState() == null) { // 결제 정보가 만료됨.
                    resultMap.put("successYn", successYn);
                    resultMap.put("message", "결제 정보가 만료되었습니다. 다시 시도해 주세요.");
                    return resultMap;
                }
            }
        } catch (NullPointerException | GeneralSecurityException | IOException e) {
            e.printStackTrace();
            resultMap.put("successYn", successYn);
            resultMap.put("message", "구매 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
            return resultMap;
        }
        param.put("userId", param.get("ssUserId"));
        pointMap = exqueryService.selectOne("cronies.app.point.getUserLastPoint", param);

        resultMap.put("successYn", successYn);
        resultMap.put("message", message);
        resultMap.put("lastPoint", pointMap.get("lastPoint"));
        resultMap.put("isRealYn", isRealYn);
        return resultMap;
    }
}

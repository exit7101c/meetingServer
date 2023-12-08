package com.nse.config.socket;

import cronies.meeting.user.purchase.utils.IapTicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import select.spring.exquery.service.ExqueryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScheduleComponent {

    @Autowired
    private ExqueryService exqueryService;

    /**
     * @parameter: none
     * @throws Exception
     *
     * @description: ipatic에 등록된 결제건 중 구독 정보를 app db에 update한다
     *               실행 주기 - 매시 정각
     */
//    @Scheduled(cron="0 0 * * * *")
    public void subscriptionRenew() throws Exception {
        IapTicData iapTicData = new IapTicData();
        List<HashMap<String, Object>> transactions = iapTicData.getTransactions();

        Date currentTime = new Date();
        SimpleDateFormat dateTimeISOFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        Date today = dayFormat.parse(dayFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, +1);
        Date afterDay = dayFormat.parse(dayFormat.format(calendar.getTime()));

        List<HashMap<String, Object>> filteredList = transactions.stream()
                .filter(item -> {
                    Date expirationDate;
                    Date lastRenewalDate;
                    Boolean sandbox;
                    try {
                        String expirationDateStr = String.valueOf(item.get("expirationDate"));
                        String lastRenewalDateStr = String.valueOf(item.get("lastRenewalDate"));

                        sandbox = Boolean.parseBoolean(String.valueOf(item.get("sandbox")));
                        expirationDate = dateTimeISOFormat.parse(expirationDateStr);
                        lastRenewalDate = dayFormat.parse(lastRenewalDateStr);
                    } catch (ParseException | NullPointerException e) {
                        return false;
                    }
                    return !sandbox && expirationDate.after(currentTime) && (lastRenewalDate.equals(today) || lastRenewalDate.equals(afterDay));
                })
                .collect(Collectors.toList());

        Iterator<HashMap<String, Object>> iterator = filteredList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> item = iterator.next();
            if (item.get("platform").equals("google")) {
                String transactionItemId = item.get("transactionId").toString().split(":")[1];
                if (transactionItemId.contains("..")) {
                    int index = transactionItemId.indexOf("..");
                    if (index != -1) {
                        transactionItemId = transactionItemId.substring(0, index);
                    }
                }
                item.put("transactionItemId", transactionItemId);
            } else if (item.get("platform").equals("apple")) {
                item.put("transactionItemId", item.get("purchaseId").toString().split(":")[1]);
            }

            Date expiredDate = dateTimeISOFormat.parse(item.get("expirationDate").toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(expiredDate);
            cal.add(Calendar.HOUR_OF_DAY, 9);
            expiredDate = cal.getTime();

            String expiredDateStr = dateTimeFormat.format(expiredDate);
            item.put("expiredDate", expiredDateStr);

//            if (!item.containsKey("cancelationReason")) {
//                // todo: SC_PURCHASE_HIS테이블 insert위해 PK수정 필요함, 현재 USER_ID, TRANSACTION_ID, PRODUCT_ID
////                exqueryService.insert("cronies.app.storeCommon.purchaseHisInsert", item); // 아직 쿼리 없음
//                exqueryService.update("cronies.app.storeCommon.subscriptionCurrentRenew", item);
//            } else {
//                exqueryService.update("cronies.app.storeCommon.cancelationUpdate", item);
//            }
        }
    }
}

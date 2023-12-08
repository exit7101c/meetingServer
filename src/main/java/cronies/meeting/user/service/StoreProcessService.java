package cronies.meeting.user.service;

import java.util.HashMap;
import java.util.List;

public interface StoreProcessService {

    /***************** 아이템 구매  *****************/
    public HashMap<String, Object> setBuyItem(HashMap<String, Object> param) throws Exception;
    /***************** 패키지 구매  *****************/
    public HashMap<String, Object> setBuyPackage(HashMap<String, Object> param) throws Exception;
    /***************** 하트어택 사용  *****************/
    public HashMap<String, Object> useHeartAttack(HashMap<String, Object> param) throws Exception;
    /***************** 받은좋아요확인 사용  *****************/
    public HashMap<String, Object> useReceivedLike(HashMap<String, Object> param) throws Exception;
    /***************** 허니부스트 사용  *****************/
    public HashMap<String, Object> useHoneyBoost(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> checkBoost(HashMap<String, Object> param) throws Exception;
    /***************** 프로필광고 사용  *****************/
    public HashMap<String, Object> useProfileAd(HashMap<String, Object> param) throws Exception;
    /***************** 스와이프 추가 사용  *****************/
    public HashMap<String, Object> useSwiperPlus(HashMap<String, Object> param) throws Exception;
    /***************** 쿠폰 사용  *****************/
    public HashMap<String, Object> useCoupon(HashMap<String, Object> param) throws Exception;
    /***************** 모임부스트 사용  *****************/
    public HashMap<String, Object> useOpenChatBoost(HashMap<String, Object> param) throws Exception;
    /***************** 콘테스트투표 사용  *****************/
    public HashMap<String, Object> useContestVote(HashMap<String, Object> param) throws Exception;
    /***************** 구독 결제 이후 프로세스  *****************/
    public HashMap<String, Object> buySubscribeComplete(HashMap<String, Object> param) throws Exception;




    /***************** 구독 RESET 임시 프로세스  *****************/
    public HashMap<String, Object> cancelSubscribe(HashMap<String, Object> param) throws Exception;

}
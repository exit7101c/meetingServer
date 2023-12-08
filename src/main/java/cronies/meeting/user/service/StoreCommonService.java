package cronies.meeting.user.service;

import java.util.HashMap;
import java.util.List;

public interface StoreCommonService {

    public List<HashMap<String, Object>> getStoreCategoryList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getStoreItemList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getStoreInitList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserCouponDataOne(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getProfileAdList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserSwiperInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserSubscribeInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getTargetUserSubscribeInfo(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getSubscribeList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> buySubscribeCheck(HashMap<String, Object> param) throws Exception;

}
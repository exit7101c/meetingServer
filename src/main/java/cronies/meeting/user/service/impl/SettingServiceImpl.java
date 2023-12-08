package cronies.meeting.user.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.config.CryptoUtil;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.SettingService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service("SettingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SettingServiceImpl implements SettingService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    HttpSession session;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getSettingMainUserInfo(HashMap<String, Object> param) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingMainUserInfo", param);
        resultMap.put("userKey", commonService.getEncoding(String.valueOf(resultMap.get("userKey"))));
        if(resultMap.get("userPhotoKey") != null){
            resultMap.put("userPhotoId", resultMap.get("userPhotoKey"));//임시PhotoId사용
            if(resultMap.get("userPhotoKey") != null){
                resultMap.put("userPhotoKey", commonService.getEncoding(String.valueOf(resultMap.get("userPhotoKey"))));
            }
        } else {
            resultMap.put("userPhotoId", null);//임시PhotoId사용
        }


        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", commonService.getDecoding(param.get("regUserKey").toString()));
        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingMainUserInfo", param);
        if(resultMap.get("userPhotoKey") != null){
            resultMap.put("userPhotoId", resultMap.get("userPhotoKey"));//임시PhotoId사용
            resultMap.put("userPhotoKey", commonService.getEncoding(String.valueOf(resultMap.get("userPhotoKey"))));
        } else {
            resultMap.put("userPhotoId", null);//임시PhotoId사용
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getMyLocation(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("cronies.app.setting.getMyLocation", param);
        if(resultMap == null){
            resultMap.put("currLat", "33.450701");
            resultMap.put("currLon", "126.570667");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getSettingUserInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> mbtiList = new ArrayList<>(); String mbtiStr = "";
        List<HashMap<String, Object>> formList = new ArrayList<>(); String formStr = ""; String idealFormStr = "";
        List<HashMap<String, Object>> hobbyList = new ArrayList<>(); String hobbyStr = "";
        List<HashMap<String, Object>> idealLookList = new ArrayList<>(); String idealLookStr = "";
        List<HashMap<String, Object>> idealCharacterList = new ArrayList<>(); String idealCharacterStr = "";

        List<HashMap<String, Object>> smokeList = new ArrayList<>(); String smokeStr = "";
        List<HashMap<String, Object>> drinkingList = new ArrayList<>(); String drinkingStr = "";

        param.put("userId", param.get("ssUserId"));

        mbtiList = exqueryService.selectList("cronies.app.setting.getMbtiList", param);
        formList = exqueryService.selectList("cronies.app.setting.getBodyList", param);
        hobbyList = exqueryService.selectList("cronies.app.setting.getHobbyList", param);
        idealLookList = exqueryService.selectList("cronies.app.setting.getFaceList", param);
        idealCharacterList = exqueryService.selectList("cronies.app.setting.getPersonalityList", param);

        smokeList = exqueryService.selectList("cronies.app.setting.getSmokeList", param);
        drinkingList = exqueryService.selectList("cronies.app.setting.getDrinkingList", param);

        if (!param.containsKey("userKey")) {
            param.put("userKey", "");
        }
        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingUserInfo", param);

        if(resultMap.get("mbtiCd") != null){
            ArrayList<String> mbtiArray = new ArrayList<>(Arrays.asList(resultMap.get("mbtiCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : mbtiArray) {
                for (int i = 0; i < mbtiList.size(); i++){
                    if(mbtiList.get(i).get("mbtiCd").toString().equals(item)){ if(mbtiStr.equals("")){ mbtiStr = mbtiList.get(i).get("mbtiNm").toString(); } else { mbtiStr += ", "+mbtiList.get(i).get("mbtiNm").toString(); } }
                }
            }
        }
        if(resultMap.get("formCd") != null){
            ArrayList<String> formArray = new ArrayList<>(Arrays.asList(resultMap.get("formCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : formArray) {
                for (int i = 0; i < formList.size(); i++){
                    if(formList.get(i).get("formCd").toString().equals(item)){ if(formStr.equals("")){ formStr = formList.get(i).get("formNm").toString(); } else { formStr += ", "+formList.get(i).get("formNm").toString(); } }
                }
            }
        }
        if(resultMap.get("hobbyCd") != null){
            ArrayList<String> hobbyArray = new ArrayList<>(Arrays.asList(resultMap.get("hobbyCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : hobbyArray) {
                for (int i = 0; i < hobbyList.size(); i++){
                    if(hobbyList.get(i).get("hobbyCd").toString().equals(item)){ if(hobbyStr.equals("")){ hobbyStr = hobbyList.get(i).get("hobbyNm").toString(); } else { hobbyStr += ", "+hobbyList.get(i).get("hobbyNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealFormCd") != null){
            ArrayList<String> idealFormArray = new ArrayList<>(Arrays.asList(resultMap.get("idealFormCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealFormArray) {
                for (int i = 0; i < formList.size(); i++){
                    if(formList.get(i).get("formCd").toString().equals(item)){ if(idealFormStr.equals("")){ idealFormStr = formList.get(i).get("formNm").toString(); } else { idealFormStr += ", "+formList.get(i).get("formNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealLookCd") != null){
            ArrayList<String> idealLookArray = new ArrayList<>(Arrays.asList(resultMap.get("idealLookCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealLookArray) {
                for (int i = 0; i < idealLookList.size(); i++){
                    if(idealLookList.get(i).get("lookCd").toString().equals(item)){ if(idealLookStr.equals("")){ idealLookStr = idealLookList.get(i).get("lookNm").toString(); } else { idealLookStr += ", "+idealLookList.get(i).get("lookNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealCharacterCd") != null){
            ArrayList<String> idealCharacterArray = new ArrayList<>(Arrays.asList(resultMap.get("idealCharacterCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealCharacterArray) {
                for (int i = 0; i < idealCharacterList.size(); i++){
                    if(idealCharacterList.get(i).get("characterCd").toString().equals(item)){ if(idealCharacterStr.equals("")){ idealCharacterStr = idealCharacterList.get(i).get("characterNm").toString(); } else { idealCharacterStr += ", "+idealCharacterList.get(i).get("characterNm").toString(); } }
                }
            }
        }

        if(resultMap.get("smokeCd") != null){
            ArrayList<String> smokeArray = new ArrayList<>(Arrays.asList(resultMap.get("smokeCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : smokeArray) {
                for (int i = 0; i < smokeList.size(); i++){
                    if(smokeList.get(i).get("smokeCd").toString().equals(item)){ if(smokeStr.equals("")){ smokeStr = smokeList.get(i).get("smokeNm").toString(); } else { smokeStr += ", "+smokeList.get(i).get("smokeNm").toString(); } }
                }
            }
        }
        if(resultMap.get("drinkCd") != null){
            ArrayList<String> drinkArray = new ArrayList<>(Arrays.asList(resultMap.get("drinkCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : drinkArray) {
                for (int i = 0; i < drinkingList.size(); i++){
                    if(drinkingList.get(i).get("drinkCd").toString().equals(item)){ if(drinkingStr.equals("")){ drinkingStr = drinkingList.get(i).get("drinkNm").toString(); } else { drinkingStr += ", "+drinkingList.get(i).get("drinkNm").toString(); } }
                }
            }
        }

        resultMap.put("mbtiNm", mbtiStr);
        resultMap.put("formNm", formStr);
        resultMap.put("hobbyNm", hobbyStr);
        resultMap.put("idealFormNm", idealFormStr);
        resultMap.put("idealLookNm", idealLookStr);
        resultMap.put("idealCharacterNm", idealCharacterStr);

        resultMap.put("smokeNm", smokeStr);
        resultMap.put("drinkNm", drinkingStr);

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getSettingUserBaseInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingUserBaseInfo", param);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getReceptLetterList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.setting.getReceptLetterList", param);
        for(int i = 0; resultList.size() > i; i++){
            resultList.get(i).put("letterKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("letterKey"))));
            resultList.get(i).put("sendKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("sendKey"))));
        }
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getSendLetterList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.setting.getSendLetterList", param);
        for(int i = 0; resultList.size() > i; i++){
            resultList.get(i).put("letterKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("letterKey"))));
            resultList.get(i).put("receptKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("receptKey"))));
        }
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getLetterDetail(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("letterId", commonService.getDecoding(String.valueOf(param.get("letterKey"))));
        resultMap = exqueryService.selectOne("cronies.app.setting.getLetterDetail", param);
        if(resultMap.get("userPhotoKey") != null){
            resultMap.put("userPhotoKey", commonService.getEncoding(String.valueOf(resultMap.get("userPhotoKey"))));
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getSettingUserSet(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.setting.getSettingUserSet", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setSettingUserSet(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> codeTypeMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        codeTypeMap = exqueryService.selectOne("cronies.app.setting.getSettingUserSetByCodeType", param);
        String updateValue = "N";
        if(codeTypeMap != null){
            if(codeTypeMap.get("val").toString().equals("Y")){
                updateValue = "N";
            } else {
                updateValue = "Y";
            }
        } else {
            updateValue = "N";
        }
        param.put("updateValue", updateValue);
        if(exqueryService.insert("cronies.app.setting.setSettingUserSet", param) == 1) {
            resultMap.put("successYn","Y");
            resultMap.put("message","");
            resultMap.put("updatedValue", updateValue);
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","수정 중 에러가 발생하였습니다. 문의 부탁드립니다");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getSettingUserTerm(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingUserTerm", param);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setSettingUserTerm(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> codeTypeMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        codeTypeMap = exqueryService.selectOne("cronies.app.setting.getSettingUserTerm", param);
        String updateValue = "N";

        if(param.get("codeType").toString().equals("MARKETING")){
            if(codeTypeMap.get("termMarketing").toString().equals("Y")){
                updateValue = "N";
            } else {
                updateValue = "Y";
            }
        } else {
            if(codeTypeMap.get("termPush").toString().equals("Y")){
                updateValue = "N";
            } else {
                updateValue = "Y";
            }
        }

        param.put("updateValue", updateValue);
        if(exqueryService.insert("cronies.app.setting.setSettingUserTerm", param) == 1) {
            resultMap.put("successYn","Y");
            resultMap.put("message","");
            resultMap.put("updatedValue", updateValue);
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","수정 중 에러가 발생하였습니다. 문의 부탁드립니다");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getNoticeList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        resultMap = exqueryService.selectPagingList("cronies.app.setting.getNoticeList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        dataList = (List<HashMap<String, Object>>) resultMap.get("result");
        for(int i = 0; dataList.size() > i; i++){
            dataList.get(i).put("articleKey", commonService.getEncoding(String.valueOf(dataList.get(i).get("articleKey"))));
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getNoticeArticleOne(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.setting.getNoticeArticleOne", param);
        resultMap.put("articleKey", commonService.getEncoding(param.get("articleId").toString()));

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getLoungeList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        resultMap = exqueryService.selectPagingList("cronies.app.setting.getLoungeList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        dataList = (List<HashMap<String, Object>>) resultMap.get("result");
        for(int i = 0; dataList.size() > i; i++){
            dataList.get(i).put("articleKey", commonService.getEncoding(String.valueOf(dataList.get(i).get("articleKey"))));
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getPartyList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        resultMap = exqueryService.selectPagingList("cronies.app.setting.getPartyList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        dataList = (List<HashMap<String, Object>>) resultMap.get("result");
        for(int i = 0; dataList.size() > i; i++){
            dataList.get(i).put("articleKey", commonService.getEncoding(String.valueOf(dataList.get(i).get("articleKey"))));
        }
        return resultMap;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getProfilePic(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        param.put("userId", commonService.getDecoding(param.get("regUserKey").toString()));
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.setting.getProfilePic", param);
        if(resultMap.get("userPhotoKey") != null){
            resultMap.put("userPhotoId", resultMap.get("userPhotoKey"));//임시PhotoId사용
            if(resultMap.get("userPhotoKey") != null){
                resultMap.put("userPhotoKey", commonService.getEncoding(String.valueOf(resultMap.get("userPhotoKey"))));
            }
        } else {
            resultMap.put("userPhotoId", null);//임시PhotoId사용
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> updateProfilePic(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if (exqueryService.update("cronies.app.setting.updateProfilePic", param) > 0) {
            if (userPhotoLocationCheck(param)) {
                exqueryService.update("cronies.app.setting.updateUserChoiceYn", param);
            }
            paramMap.put("isUpdate", true);
        } else {
            paramMap.put("isUpdate", false);
        }
        return paramMap;
    }

    public Boolean userPhotoLocationCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> userPhotoLocationCheckMap = exqueryService.selectOne("cronies.app.setting.userPhotoLocationCheck", param);
        if (!MapUtils.isEmpty(userPhotoLocationCheckMap) &&
            userPhotoLocationCheckMap.get("photo1") != null &&
            userPhotoLocationCheckMap.get("photo2") != null &&
            userPhotoLocationCheckMap.get("lat") != null &&
            userPhotoLocationCheckMap.get("lon") != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> deleteProfilePic(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if(exqueryService.update("cronies.app.setting.deleteProfilePic", param) > 0){
            paramMap.put("successYn", "Y");
            paramMap.put("message", "사진삭제 성공");
        } else {
            paramMap.put("successYn", "N");
            paramMap.put("message", "에러가 발생하였습니다. 다시 시도하여주시기 바랍니다.");

        }
        return paramMap;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserDetailUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        if(exqueryService.update("cronies.app.setting.getUserDetailUpdate", param) > 0){
            resultMap.put("successYn", "Y");
        } else {
            resultMap.put("successYn", "N");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        if(param.get("addr1") == null ){
            if(exqueryService.update("cronies.app.setting.getUserUpdate", param) > 0){
                resultMap.put("successYn", "Y");
            } else {
                resultMap.put("successYn", "N");
            }
        } else {
            if(exqueryService.update("cronies.app.setting.getUserAddressUpdate", param) > 0){
                resultMap.put("successYn", "Y");

                if (userPhotoLocationCheck(param)) {
                    exqueryService.update("cronies.app.setting.updateUserChoiceYn", param);
                }
            } else {
                resultMap.put("successYn", "N");
            }
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> changePassword(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        String successYn = "N";
        String message = "";

        CryptoUtil pwEncyript = new CryptoUtil();
        String beforePw = pwEncyript.encrypt(param.get("beforePw").toString());
        param.put("beforePw", beforePw);

        HashMap<String, Object> aMap = exqueryService.selectOne("cronies.app.setting.selectChangePwUserInfo", param);
        if(aMap == null){
            message = "현재 비밀번호가 일치하지 않습니다.";
        } else {
            param.put("afterPw", pwEncyript.encrypt(param.get("afterPw1").toString()));
            if(exqueryService.update("cronies.app.setting.updateUserPw", param) == 1){
                successYn = "Y";
                message = "비밀번호를 변경하였습니다.";
            } else {
                message = "비밀번호 변경 중 오류가 발생하였습니다. 문의 부탁드립니다.";
            }
        }

        resultMap.put("successYn", successYn);
        resultMap.put("message", message);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getDailyUserInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> mbtiList = new ArrayList<>(); String mbtiStr = ""; String mbtiDt = "";
        List<HashMap<String, Object>> formList = new ArrayList<>(); String formStr = ""; String idealFormStr = "";
        List<HashMap<String, Object>> hobbyList = new ArrayList<>(); String hobbyStr = "";
        List<HashMap<String, Object>> idealLookList = new ArrayList<>(); String idealLookStr = "";
        List<HashMap<String, Object>> idealCharacterList = new ArrayList<>(); String idealCharacterStr = "";

        List<HashMap<String, Object>> smokeList = new ArrayList<>(); String smokeStr = "";
        List<HashMap<String, Object>> drinkingList = new ArrayList<>(); String drinkingStr = "";
        String successYn = "N";
        String message = "";

        if( param.get("pageType").toString().equals("mypage") ){
            param.put("userId", param.get("ssUserId"));
        } else {
            param.put("userId", commonService.getDecoding(param.get("userKey").toString()));
        }

        mbtiList = exqueryService.selectList("cronies.app.setting.getMbtiList", param);
        formList = exqueryService.selectList("cronies.app.setting.getBodyList", param);
        hobbyList = exqueryService.selectList("cronies.app.setting.getHobbyList", param);
        idealLookList = exqueryService.selectList("cronies.app.setting.getFaceList", param);
        idealCharacterList = exqueryService.selectList("cronies.app.setting.getPersonalityList", param);

        smokeList = exqueryService.selectList("cronies.app.setting.getSmokeList", param);
        drinkingList = exqueryService.selectList("cronies.app.setting.getDrinkingList", param);

        resultMap = exqueryService.selectOne("cronies.app.setting.getSettingUserInfo", param);

        if(resultMap.get("mbtiCd") != null){
            ArrayList<String> mbtiArray = new ArrayList<>(Arrays.asList(resultMap.get("mbtiCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : mbtiArray) {
                for (int i = 0; i < mbtiList.size(); i++){
                    if(mbtiList.get(i).get("mbtiCd").toString().equals(item)){ if(mbtiStr.equals("")){ mbtiStr = mbtiList.get(i).get("mbtiNm").toString(); } else { mbtiStr += ", "+mbtiList.get(i).get("mbtiNm").toString(); } }
                    if(mbtiList.get(i).get("mbtiCd").toString().equals(item)){ if(mbtiStr.equals("")){ mbtiDt = mbtiList.get(i).get("mbtiDetail").toString(); } else { mbtiDt += mbtiList.get(i).get("mbtiDetail").toString(); } }
                }
            }
        }
        if(resultMap.get("formCd") != null){
            ArrayList<String> formArray = new ArrayList<>(Arrays.asList(resultMap.get("formCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : formArray) {
                for (int i = 0; i < formList.size(); i++){
                    if(formList.get(i).get("formCd").toString().equals(item)){ if(formStr.equals("")){ formStr = formList.get(i).get("formNm").toString(); } else { formStr += ", "+formList.get(i).get("formNm").toString(); } }
                }
            }
        }
        if(resultMap.get("hobbyCd") != null){
            ArrayList<String> hobbyArray = new ArrayList<>(Arrays.asList(resultMap.get("hobbyCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : hobbyArray) {
                for (int i = 0; i < hobbyList.size(); i++){
                    if(hobbyList.get(i).get("hobbyCd").toString().equals(item)){ if(hobbyStr.equals("")){ hobbyStr = hobbyList.get(i).get("hobbyNm").toString(); } else { hobbyStr += ", "+hobbyList.get(i).get("hobbyNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealFormCd") != null){
            ArrayList<String> idealFormArray = new ArrayList<>(Arrays.asList(resultMap.get("idealFormCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealFormArray) {
                for (int i = 0; i < formList.size(); i++){
                    if(formList.get(i).get("formCd").toString().equals(item)){ if(idealFormStr.equals("")){ idealFormStr = formList.get(i).get("formNm").toString(); } else { idealFormStr += ", "+formList.get(i).get("formNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealLookCd") != null){
            ArrayList<String> idealLookArray = new ArrayList<>(Arrays.asList(resultMap.get("idealLookCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealLookArray) {
                for (int i = 0; i < idealLookList.size(); i++){
                    if(idealLookList.get(i).get("lookCd").toString().equals(item)){ if(idealLookStr.equals("")){ idealLookStr = idealLookList.get(i).get("lookNm").toString(); } else { idealLookStr += ", "+idealLookList.get(i).get("lookNm").toString(); } }
                }
            }
        }
        if(resultMap.get("idealCharacterCd") != null){
            ArrayList<String> idealCharacterArray = new ArrayList<>(Arrays.asList(resultMap.get("idealCharacterCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : idealCharacterArray) {
                for (int i = 0; i < idealCharacterList.size(); i++){
                    if(idealCharacterList.get(i).get("characterCd").toString().equals(item)){ if(idealCharacterStr.equals("")){ idealCharacterStr = idealCharacterList.get(i).get("characterNm").toString(); } else { idealCharacterStr += ", "+idealCharacterList.get(i).get("characterNm").toString(); } }
                }
            }
        }

        if(resultMap.get("smokeCd") != null){
            ArrayList<String> smokeArray = new ArrayList<>(Arrays.asList(resultMap.get("smokeCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : smokeArray) {
                for (int i = 0; i < smokeList.size(); i++){
                    if(smokeList.get(i).get("smokeCd").toString().equals(item)){ if(smokeStr.equals("")){ smokeStr = smokeList.get(i).get("smokeNm").toString(); } else { smokeStr += ", "+smokeList.get(i).get("smokeNm").toString(); } }
                }
            }
        }
        if(resultMap.get("drinkCd") != null){
            ArrayList<String> drinkArray = new ArrayList<>(Arrays.asList(resultMap.get("drinkCd").toString()
                    .replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","")
                    .split(",")));
            for (String item : drinkArray) {
                for (int i = 0; i < drinkingList.size(); i++){
                    if(drinkingList.get(i).get("drinkCd").toString().equals(item)){ if(drinkingStr.equals("")){ drinkingStr = drinkingList.get(i).get("drinkNm").toString(); } else { drinkingStr += ", "+drinkingList.get(i).get("drinkNm").toString(); } }
                }
            }
        }

        resultMap.put("mbtiNm", mbtiStr);
        resultMap.put("mbtiDetail", mbtiDt);
        resultMap.put("formNm", formStr);
        resultMap.put("hobbyNm", hobbyStr);
        resultMap.put("idealFormNm", idealFormStr);
        resultMap.put("idealLookNm", idealLookStr);
        resultMap.put("idealCharacterNm", idealCharacterStr);

        resultMap.put("smokeNm", smokeStr);
        resultMap.put("drinkNm", drinkingStr);
        if(resultMap == null){
            message = "사용자가 존재하지 않습니다.";
        } else {
            successYn = "Y";
            message = "조회성공.";
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getKeywordList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> mbtiList = new ArrayList<>();
        List<HashMap<String, Object>> faceList = new ArrayList<>();
        List<HashMap<String, Object>> bodyList = new ArrayList<>();
        List<HashMap<String, Object>> perList = new ArrayList<>();
        List<HashMap<String, Object>> hobbyList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));

        mbtiList = exqueryService.selectList("cronies.app.setting.getMbtiList", param);
        faceList = exqueryService.selectList("cronies.app.setting.getFaceList", param);
        bodyList = exqueryService.selectList("cronies.app.setting.getBodyList", param);
        perList = exqueryService.selectList("cronies.app.setting.getPersonalityList", param);
        hobbyList = exqueryService.selectList("cronies.app.setting.getHobbyList", param);

        resultMap.put("mbtiList", mbtiList );
        resultMap.put("faceList", faceList );
        resultMap.put("bodyList", bodyList );
        resultMap.put("perList", perList );
        resultMap.put("hobbyList", hobbyList );

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserKeywordUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> mbtiCd = mapper.readValue(param.get("mbtiCd").toString(), new TypeReference<ArrayList<String>>(){});
//        List<String> lookCd = mapper.readValue(param.get("lookCd").toString(), new TypeReference<ArrayList<String>>(){});
//        List<String> formCd = mapper.readValue(param.get("formCd").toString(), new TypeReference<ArrayList<String>>(){});
//        List<String> characterCd = mapper.readValue(param.get("characterCd").toString(), new TypeReference<ArrayList<String>>(){});
//        List<String> hobbyCd = mapper.readValue(param.get("hobbyCd").toString(), new TypeReference<ArrayList<String>>(){});
//        param.put("mbtiCd", mbtiCd);
//        param.put("lookCd", lookCd);
//        param.put("formCd", formCd);
//        param.put("characterCd", characterCd);
//        param.put("hobbyCd", hobbyCd);
//        System.out.println("\n\n json param:::::" + param + "\n\n");
        if(exqueryService.update("cronies.app.setting.getUserKeywordUpdate", param) > 0){
            resultMap.put("successYn", "Y");
        } else {
            resultMap.put("successYn", "N");
        }

        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getInfoList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> bodyList = new ArrayList<>();
        List<HashMap<String, Object>> smokeList = new ArrayList<>();
        List<HashMap<String, Object>> drinkingList = new ArrayList<>();
        List<HashMap<String, Object>> iconList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));

        /** 기본정보 리스트 조회 **/
        bodyList = exqueryService.selectList("cronies.app.setting.getBodyList", param);
        smokeList = exqueryService.selectList("cronies.app.setting.getSmokeList", param);
        drinkingList = exqueryService.selectList("cronies.app.setting.getDrinkingList", param);
        iconList = exqueryService.selectList("cronies.app.setting.getIconList", param);

        resultMap.put("bodyList", bodyList );
        resultMap.put("smokeList", smokeList );
        resultMap.put("drinkingList", drinkingList );
        resultMap.put("iconList", iconList );
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserInfoUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        if(exqueryService.update("cronies.app.setting.getUserInfoUpdate", param) > 0){
            resultMap.put("successYn", "Y");
        } else {
            resultMap.put("successYn", "N");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> changeUserNick(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> diffDateMap = new HashMap<String, Object>();
        HashMap<String, Object> changeHisMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        try {
            //가입 후 10일이 지났는지 여부
            diffDateMap = exqueryService.selectOne("cronies.app.setting.getUserOneSignDate", param);
            if(Integer.parseInt(diffDateMap.get("diffDate").toString()) < 10){
                resultMap.put("successYn", "N");
                resultMap.put("message", "가입 10일 이내에는 닉네임 변경이 불가능합니다.");
            } else {
                //변경이력이 30일 이내에 존재하는지 여부
                changeHisMap = exqueryService.selectOne("cronies.app.setting.getUserNickLastChangeHis", param);
                if(changeHisMap != null){
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "닉네임은 변경 후 30일이 지나야 변경 가능합니다.");
                } else {
                    // SC_USER - UPDATE
                    if(exqueryService.update("cronies.app.setting.changeUserNick", param) > 0){
                        // SC_NICK_CHANGE_HIS - INSERT
                        exqueryService.insert("cronies.app.setting.insertNickChangeHis", param);
                        resultMap.put("successYn", "Y");
                    } else {
                        resultMap.put("successYn", "N");
                        resultMap.put("message", "변경 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                    }
                }
            }
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "변경 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getPoliceList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.setting.getPoliceList", param);
        return resultList;
        }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> savePolice(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultList = new HashMap<String, Object>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> selectOneMap = new HashMap<>();
        param.put("userId", param.get("ssUserId"));

        param.put("targetKey", commonService.getDecoding(param.get("targetKey").toString()));

        if(!param.get("targetType").equals("MESSAGE_TALK")){
            param.put("targetId", commonService.getDecoding(param.get("targetId").toString()));
        }

        /** OPENCHAT **/
        if(param.get("targetType").equals("OPENCHAT")){
            resultList = exqueryService.selectOne("cronies.app.setting.getPoliceOpenChatDetail", param);
            resultList.put("contents", "\"TITLE=" + resultList.get("title") + "\", \"CONTENT=" + resultList.get("content") + "\", \"PHOTO=" + resultList.get("mainFileKey")+"\"");
        }
        /** COMMUNITY or CONTEST **/
        else if(param.get("targetType").equals("COMMUNITY") || param.get("targetType").equals("CONTEST")){
            resultList = exqueryService.selectOne("cronies.app.setting.getPoliceCommunityDetail", param);
            resultList.put("contents", "\"TITLE=" + resultList.get("title") + "\", \"CONTENT=" + resultList.get("content") + "\", \"PHOTO1=" + resultList.get("attr1") + "\", \"PHOTO2=" + resultList.get("attr2") + "\", \"PHOTO3=" + resultList.get("attr3") + "\"");
        }
        /** COMMENT **/
        else if(param.get("targetType").equals("COMMENT") || param.get("targetType").equals("CHILD_COMMENT")){
            resultList = exqueryService.selectOne("cronies.app.setting.getPoliceComment", param);
            resultList.put("contents", "\"CONTENT=" + resultList.get("contents") + "\", \"PHOTO=" + resultList.get("attachFileId") + "\"");
        }
        /** DAILY_CARD_INFO **/
        else if(param.get("targetType").equals("DAILY_INFO")){
            resultList = exqueryService.selectOne("cronies.app.setting.getPoliceDailyUserInfo", param);
            resultList.put("contents", "\"NICK_NM=" + resultList.get("nick") + "\", \"PHOTO1=" + resultList.get("photoKey1") + "\", \"PHOTO2=" + resultList.get("photoKey2") + "\", \"PHOTO3=" + resultList.get("photoKey3") + "\", \"PHOTO4=" + resultList.get("photoKey4") + "\", \"PHOTO5=" + resultList.get("photoKey5") + "\", \"PHOTO6=" + resultList.get("photoKey6") + "\"");
        }
        /** MESSAGE_TALK **/
        else if(param.get("targetType").equals("MESSAGE_TALK")){
            resultList = exqueryService.selectOne("cronies.app.setting.getPoliceMessageTalk", param);

            /** 채팅방내에서 상대방이 내용없이 신고시 null **/
            if( resultList != null ){
                resultList.put("contents", "\"MESSAGE_ID=" + resultList.get("messageId") + "\", \"USER_ID=" + resultList.get("regUserId") + "\", \"MESSAGE=" + resultList.get("message") + "\"");
            }
        }

        try {

            if( resultList != null ) {
                param.put("content", resultList.get("contents"));
                selectOneMap = exqueryService.selectOne("cronies.app.setting.getSelectOnePolice", param);
                if(selectOneMap == null){
                    exqueryService.insert("cronies.app.setting.insertPolice", param);
                    resultMap.put("message", "신고가 완료 되었습니다.");
                    resultMap.put("successYn", "Y");
                } else {
                    resultMap.put("message", "요청사항은 이미 접수되어 처리중입니다.");
                    resultMap.put("successYn", "Y");
                }
            } else {
                resultMap.put("message", "상대방이 아무런 메시지도 입력하지 않았습니다");
                resultMap.put("successYn", "N");
            }

        } catch (Exception ex) {
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
            resultMap.put("successYn", "N");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> blockByUserInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> blockMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("targetUserId", commonService.getDecoding(param.get("targetUserKey").toString()));
        resultMap.put("successYn", "Y");

        // 이미 차단되었는지 조회
        blockMap = exqueryService.selectOne("cronies.app.setting.getUserBlockOne", param);

        if(blockMap != null){
            // 차단 목록에 해당 유저가 존재 할 경우
            resultMap.put("successYn", "N");
            resultMap.put("message", "이미 차단 된 사용자입니다.");
        } else {
            // 차단 목록에 해당 유저가 존재하지 않을 경우
            exqueryService.update("cronies.app.setting.mergeUserBlock", param);
            resultMap.put("message", "차단하였습니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> logout(HashMap<String, Object> param) throws Exception {
        System.out.println(param.toString());
        HashMap<String, Object> resultMap = new HashMap<>();
        String success = "N";
        String message = "";

        if (exqueryService.update("cronies.app.setting.logout", param) == 1) {
            success = "Y";
            message = "로그아웃 되었습니다.";
        }

        resultMap.put("success", success);
        resultMap.put("message", message);

        return resultMap;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setUserSecession(HashMap<String, Object> param) throws Exception {
        System.out.println(param.toString());
        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            exqueryService.update("cronies.app.setting.setUserSecession", param);
            resultMap.put("message", "그동안 NAVY를 이용해주셔서 감사합니다. 회원탈퇴가 완료되었습니다.");
            resultMap.put("successYn", "Y");

        } catch (Exception ex) {
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
            resultMap.put("successYn", "N");
        }

        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getPointHistory(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.setting.getPointHistory", param);
        return resultList;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setChangeNicknameCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        param.put("userId", param.get("ssUserId"));
        String successYn = "N";

        // param 체크
        if (param.containsKey("nick")) {
            HashMap<String, Object> chkNick = exqueryService.selectOne("cronies.app.setting.setChangeNicknameCheck", param);
            // 입력받은 닉네임이 있다면 result = N
            successYn = chkNick.get("result").toString();
        }
        resultMap.put("successYn", successYn);

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setSaveNicknameCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        try {
            exqueryService.update("cronies.app.community.setSaveCommunityNickname", param);

            resultMap.put("successYn", "Y");
            resultMap.put("message", "닉네임이 변경 되었습니다.");
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserInvitePoint(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectOne("cronies.app.setting.getUserInvitePoint", param);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getPartnersPointHisList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> checkCode = new HashMap<String, Object>();
        HashMap<String, Object> currentPoint = new HashMap<String, Object>();

        try {
            List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
            checkCode = exqueryService.selectOne("cronies.app.setting.getPartnersCheckCode", param);
            if(checkCode != null){
                param.put("partnersCode",checkCode.get("partnersCode"));

                resultMap = exqueryService.selectPagingList("cronies.app.setting.getPartnersPointHisList", param,
                        Integer.parseInt(param.get("pageNo").toString()),
                        Integer.parseInt(param.get("pageSize").toString()));

                currentPoint = exqueryService.selectOne("cronies.app.setting.getPartnersCurrentPoint", param);
                resultMap.put("successYn", "Y");
                resultMap.put("message", "");
                resultMap.put("currentPoint",currentPoint.get("currentPoint"));
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "파트너스가 아니면 내역을 볼수 없습니다.");
            }
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");

        }
        return resultMap;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getPartnersList(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectList("cronies.app.setting.getPartnersList", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getBlockUserList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        resultMap = exqueryService.selectPagingList("cronies.app.setting.getBlockUserList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setUnblockUser(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        try {
            exqueryService.update("cronies.app.setting.setUnblockUser", param);
            resultMap.put("message", "차단 해제가 완료되었습니다.");
            resultMap.put("successYn", "Y");

        } catch (Exception ex) {
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
            resultMap.put("successYn", "N");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> choiceRegister(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        exqueryService.update("cronies.app.setting.choiceRegister", param);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> choiceYnCheck(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectOne("cronies.app.setting.choiceYnCheck", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getBadgeList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();

        List<HashMap<String, Object>> getBadgeSortate = exqueryService.selectList("cronies.app.setting.getBadgeSortate", param);

        if (getBadgeSortate.size() > 0) {
            Iterator<HashMap<String, Object>> iterator = getBadgeSortate.iterator();
            while(iterator.hasNext()) {
                HashMap<String, Object> sortate = iterator.next();
                sortate.put("userId", param.get("ssUserId"));
                List<HashMap<String, Object>> badgeList = exqueryService.selectList("cronies.app.setting.getBadgeList", sortate);
                resultMap.put(sortate.get("sortate").toString(), badgeList);
            }
        } else {
            resultMap.put("error", "등록된 뱃지리스트가 없습니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setBadgeCertification(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> dupCheck = exqueryService.selectOne("cronies.app.setting.getBadgedupCheck", param);
        if (MapUtils.isEmpty(dupCheck) || dupCheck == null) {
            if (exqueryService.insert("cronies.app.setting.setBadgeCertification", param) > 0){
                resultMap.put("message", "인증요청을 완료했습니다! 인증 시간은 최대 3일정도 소요됩니다!");
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "인증요청 중 문제가 발생했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
            }
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "이미 인증신청 하셨습니다. 인증 시간은 최대 3일정도 소요됩니다!");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getMyBadgeInfo(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectList("cronies.app.setting.getMyBadgeInfo", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getBadgeSituation(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectOne("cronies.app.setting.getBadgeSituation", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getUserBadgeInfo(HashMap<String, Object> param) throws Exception {
        param.put("userId", commonService.getDecoding(String.valueOf(param.get("userKey"))));
        return exqueryService.selectList("cronies.app.setting.getUserBadgeInfo", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getMyBadgeList(HashMap<String, Object> param) throws Exception{
        return exqueryService.selectList("cronies.app.setting.getMyBadgeList", param);
    }
}

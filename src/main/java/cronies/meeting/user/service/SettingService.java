package cronies.meeting.user.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface SettingService {

    public HashMap<String, Object> getSettingMainUserInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getProfilePic(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateProfilePic(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteProfilePic(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getSettingUserInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getMyLocation(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getSettingUserBaseInfo(HashMap<String, Object> param) throws Exception;


    /*****************쪽지 / START *****************/
    public List<HashMap<String, Object>> getReceptLetterList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getSendLetterList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getLetterDetail(HashMap<String, Object> param) throws Exception;
    /*****************쪽지 / END *****************/

    /*****************알림 / START *****************/
    public List<HashMap<String, Object>> getSettingUserSet(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setSettingUserSet(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getSettingUserTerm(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setSettingUserTerm(HashMap<String, Object> param) throws Exception;
    /*****************알림 / END *****************/

    /*****************공지사항/안내 / END *****************/
    public HashMap<String, Object> getNoticeList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getNoticeArticleOne(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getLoungeList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getPartyList(HashMap<String, Object> param) throws Exception;
    /*****************공지사항/안내 / START *****************/


    public HashMap<String, Object> getUserDetailUpdate(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserUpdate(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> changePassword(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> getDailyUserInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getKeywordList(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> getUserKeywordUpdate(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getInfoList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getUserInfoUpdate(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> changeUserNick(HashMap<String, Object> param) throws Exception;


    /***************** 신고 / START *****************/
    public List<HashMap<String, Object>> getPoliceList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> savePolice(HashMap<String, Object> param) throws Exception;
    /***************** 신고 / END *****************/

    /***************** 차단 / START *****************/
    public HashMap<String, Object> blockByUserInfo(HashMap<String, Object> param) throws Exception;
    /***************** 차단 / END *****************/

    /***************** 로그아웃 *****************/
    public HashMap<String, Object> logout(HashMap<String, Object> param) throws Exception;
    /***************** 회원탈퇴 *****************/
    public HashMap<String, Object> setUserSecession(HashMap<String, Object> param) throws Exception;

    /****************** 포인트 이용내역 ********************/
    public List<HashMap<String, Object>> getPointHistory(HashMap<String, Object> param) throws Exception;


    /****************** 닉네임 중복체크 및 저장 ********************/
    public HashMap<String, Object> setChangeNicknameCheck(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setSaveNicknameCheck(HashMap<String, Object> param) throws Exception;

    /****************** 초대포인트 조회 ********************/
    public HashMap<String, Object> getUserInvitePoint(HashMap<String, Object> param) throws Exception;

    /****************** 파트너스 포인트 조회 ********************/
    public HashMap<String, Object> getPartnersPointHisList(HashMap<String, Object> param) throws Exception;

    /****************** 제휴업체 조회 ********************/
    public List<HashMap<String, Object>> getPartnersList(HashMap<String, Object> param) throws Exception;

    /****************** 차단목록 조회 ********************/
    public HashMap<String, Object> getBlockUserList(HashMap<String, Object> param) throws Exception;

    /****************** 차단 해제 ********************/
    public HashMap<String, Object> setUnblockUser(HashMap<String, Object> param) throws Exception;


    public HashMap<String, Object> choiceRegister(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> choiceYnCheck(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> getBadgeList(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> setBadgeCertification(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> getMyBadgeInfo(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> getMyBadgeList(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> getBadgeSituation(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> getUserBadgeInfo(HashMap<String, Object> param) throws Exception;
}
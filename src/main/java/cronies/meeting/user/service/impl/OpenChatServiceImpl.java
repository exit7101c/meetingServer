package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.*;
import org.apache.commons.collections.MapUtils;
import org.aspectj.asm.IProgramElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("OpenChatService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OpenChatServiceImpl implements OpenChatService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MessageMainService messageMainService;

    @Autowired
    private PushService pushService;

    @Autowired
    private JoinService joinService;

    @Autowired
    private StoreCommonService storeCommonService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getPartitionList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        resultList = exqueryService.selectList("cronies.app.openChat.getPartitionList", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getPartitionSubList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        resultList = exqueryService.selectList("cronies.app.openChat.getPartitionSubList", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();

        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.openChat.getOpenChatList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if (resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
                if (result.get("fileKey") != null) {
                    result.put("fileKey", commonService.getEncoding(String.valueOf(result.get("fileKey"))));
                }
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getMyOpenChatList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();

        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.openChat.getMyOpenChatList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if (resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
                if (result.get("fileKey") != null) {
                    result.put("fileKey", commonService.getEncoding(String.valueOf(result.get("fileKey"))));
                }
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatDelYn(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> delYnMap = new HashMap<String, Object>();
        // Open Chat delYn여부
        delYnMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatDelYn", param);
        return delYnMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Boolean setViewCount(String openChatKey, String userId) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("openChatKey", openChatKey);
        Boolean res = true;
        exqueryService.selectOne("cronies.app.openChat.updateOpenChatViewCount", paramMap);

        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatDetail(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        //조회수 증가 처리
        if(param.get("viewAddYn").toString().equals("N")) {
            setViewCount(param.get("openChatKey").toString(), param.get("userId").toString());
        }
        // Open Chat delYn여부
        if(getOpenChatDelYn(param).get("delYn").equals("N")){
            resultMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatDetail", param);
            if(resultMap != null){
                resultMap.put("regUserKey", commonService.getEncoding(String.valueOf(resultMap.get("regUserKey"))));
//              resultMap.put("regUserIconFileKey", commonService.getEncoding(String.valueOf(resultMap.get("iconCd"))));
                if(resultMap.get("mainFileKey") != null){
                    resultMap.put("mainFileId", resultMap.get("mainFileKey").toString());
                    resultMap.put("mainFileKey", commonService.getEncoding(String.valueOf(resultMap.get("mainFileKey"))));
                }
            }

            int CntLimit = 100;
            switch(resultMap.get("subscribeCd").toString()) {
                case "NAVY_PLUS": CntLimit = 500; break;
                case "GOLD_PASS": CntLimit = 1000; break;
                case "PLATINUM_PASS": CntLimit = 1000; break;
                default: break;
            }
            resultMap.put("totalUserCount", CntLimit);
            resultMap.put("successYn","Y");
            resultMap.put("message","");
            resultMap.put("ssUserKey", commonService.getEncoding(param.get("ssUserId").toString()));
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getAttendInfo(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        // Open Chat delYn여부
        if(getOpenChatDelYn(param).get("delYn").equals("N")){
            resultList = exqueryService.selectList("cronies.app.openChat.getAttendInfo", param);

            for (HashMap<String, Object> result : resultList) {
                result.put("userKey", commonService.getEncoding(String.valueOf(result.get("userId"))));
                result.put("successYn","Y");
                result.put("message","");
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getChatRoomUserCountCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        try {
            if(getOpenChatDelYn(param).get("delYn").equals("N")){
                resultMap = exqueryService.selectOne("cronies.app.openChat.getChatRoomUserCountCheck", param);
                resultMap.put("successYn","Y");
                resultMap.put("message","");

            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","이미 삭제 된 소모임입니다.");
            }

        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> updateOpenChatAttendYn(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", commonService.getDecoding(String.valueOf(param.get("userKey"))));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        try {

            // Open Chat delYn여부
            if(getOpenChatDelYn(param).get("delYn").equals("N")){

                String message = "처리 되었습니다.";
                String successYn = "Y";

                if(param.get("type").equals("Y")){
                    /**
                     * 방장 구독 체크및 모임 인원제한 수지정
                     **/
                    int CntLimit = 0;
                    HashMap<String, Object> subscribeInfoMap = storeCommonService.getUserSubscribeInfo(param);
                    if(subscribeInfoMap.get("subscribeYn").toString().equals("Y")) {
                        switch(subscribeInfoMap.get("subscribeCd").toString()) {
                            case "NAVY_PLUS": CntLimit = 500; break;
                            case "GOLD_PASS": CntLimit = 1000; break;
                            case "PLATINUM_PASS": CntLimit = 1000; break;
                            default: break;
                        }
                    } else {
                        CntLimit = 100;
                    }
                    /**
                     * 채팅방 인원수 체크
                     **/
                    HashMap<String, Object>  userCnt = exqueryService.selectOne("cronies.app.openChat.getChatRoomUserCountCheck", param);
                    int userCount = Integer.parseInt(userCnt.get("roomUserCount").toString());

                    if(userCount < CntLimit ){
                        //수락일경우 대화참여처리 로직
                        exqueryService.update("cronies.app.openChat.setOpenChatAttendYn", param);

                        // 참여자 한줄소개 조회
                        HashMap<String, Object> aboutMe = exqueryService.selectOne("cronies.app.openChat.selectAboutMeInfo", param);
                        param.put("aboutMe", aboutMe.get("aboutMe"));

                        //현재 대화방의 채팅방 아이디 조회
                        HashMap<String, Object> chatroomMap = exqueryService.selectOne("cronies.app.openChat.selectOpenchatInfo", param);
                        if(chatroomMap != null){
                            param.put("chatroomId", chatroomMap.get("chatroomId"));
                            //이미 방에 들어가 있는지 체크
                            HashMap<String, Object> chatroomUserMap = exqueryService.selectOne("cronies.app.openChat.selectOpenchatUserInfo", param);

                            if(chatroomUserMap == null){
                                //대화방 참여 처리
                                exqueryService.insert("cronies.app.openChat.createChatroomAttend", param);
                                exqueryService.update("cronies.app.openChat.updateChatroomIsFirst", param);
                                exqueryService.update("cronies.app.openChat.updateChatroomUserAbout", param);

                                //기본메시지 처리
                                HashMap<String, Object> nickMap = exqueryService.selectOne("cronies.app.openChat.selectUserInfoNick", param);
                                // 누구누구님이 참여하였습니다.
                                String msg = nickMap.get("nick").toString()+"님이 참여하였습니다.";
                                param.put("message", msg);
                                param.put("regUserId", param.get("userId"));
                                messageMainService.sendMessageSingle(param);

                                //수락 메시지 발송
                                //alarmCd, userId, sendUserId, subMessage를 받아서 메시지 수신여부를 조회한다음, 허용일경우 메시지를 발송한다.
                                HashMap<String, Object> pushMap = new HashMap<String, Object>();
                                String alarmCd = "AL_C06_01";
                                pushMap.put("alarmCd", alarmCd);
                                pushMap.put("userId", param.get("userId").toString());
                                pushMap.put("sendUserId", "");
                                pushMap.put("subMessage", chatroomMap.get("title").toString());//모임명으로 사용함.
                                pushMap.put("pushType", "openChat");
                                pushMap.put("openChatKey", commonService.getEncoding(param.get("openChatKey").toString()));

                                pushService.sendPushMessage(pushMap);

                            } else {
                                successYn = "N";
                                message = "이미 수락처리된 사용자 입니다.";
                            }
                        } else {
                            param.put("chatroomId", "0");
                            successYn = "N";
                            message = "잘못된 접근입니다.";
                        }

                    } else {
                        successYn = "N";
                        message = "채팅방 참여자가 "+ CntLimit +"명을 초과하였습니다. 구독을 하시면 모임인원을 늘리 수 있습니다.";
                    }

                } else {
                    exqueryService.update("cronies.app.openChat.deleteOpenChatAttend", param);
                    successYn = "Y";
                    message = "거절 되었습니다.";
                }

                resultMap.put("successYn",successYn);
                resultMap.put("userKey",param.get("userKey"));
                resultMap.put("confirmYn",param.get("type"));
                resultMap.put("message", message);

            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","이미 삭제 된 소모임입니다.");
            }

        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "수락 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChatBookmark(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> bookmarkMap = new HashMap<String, Object>();
        HashMap<String, Object> bookmarkCntMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        // Open Chat delYn여부
        if(getOpenChatDelYn(param).get("delYn").equals("N")){
            bookmarkMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatBookmark", param);
            if(bookmarkMap == null){
                if(exqueryService.insert("cronies.app.openChat.setOpenChatBookmarkAdd", param) == 1){
                    bookmarkCntMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatBookmarkCnt", param);
                    resultMap.put("cntBookmark", bookmarkCntMap.get("cntBookmark"));
                    resultMap.put("successYn","Y");
                    resultMap.put("bookmarkYn","Y");
                    resultMap.put("message","찜이 설정 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","찜 설정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                if(exqueryService.delete("cronies.app.openChat.setOpenChatBookmarkDel", param) == 1){
                    bookmarkCntMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatBookmarkCnt", param);
                    resultMap.put("cntBookmark", bookmarkCntMap.get("cntBookmark"));
                    resultMap.put("successYn","Y");
                    resultMap.put("bookmarkYn","N");
                    resultMap.put("message","찜이 삭제 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","찜 설정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChatInvite(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> inviteMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));
        if(getOpenChatDelYn(param).get("delYn").equals("N")){
            inviteMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatInvite", param);
            // 참여요청 기록 없을 때
            if(inviteMap == null){
                if(exqueryService.insert("cronies.app.openChat.setOpenChatInvite", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("inviteYn","Y");
                    resultMap.put("message","참여가 요청 되었습니다.");

                    //방장에게 참여알림

                    //alarmCd, userId, sendUserId, subMessage를 받아서 메시지 수신여부를 조회한다음, 허용일경우 메시지를 발송한다.
                    HashMap<String, Object> pushMap = new HashMap<String, Object>();
                    HashMap<String, Object> leaderMap = exqueryService.selectOne("cronies.app.openChat.getLeaderUserInfo", param);
                    String alarmCd = "AL_C05_01";
                    pushMap.put("alarmCd", alarmCd);
                    pushMap.put("userId", leaderMap.get("leaderUserId").toString());
                    pushMap.put("sendUserId", "");
                    pushMap.put("subMessage", leaderMap.get("nick").toString());//닉네임으로 사용
                    pushMap.put("pushType", "openChat");
                    pushMap.put("openChatKey", commonService.getEncoding(param.get("openChatKey").toString()));
                    pushService.sendPushMessage(pushMap);

                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","참여 요청 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                // 리더여부
                if(inviteMap.get("isLeaderYn").equals("N")){
                    // 참여 취소 시
                    if(inviteMap.get("attenderCancelYn").toString().equals("N")){
                        if(exqueryService.update("cronies.app.openChat.setOpenChatInviteCancel", param) == 1){
                            resultMap.put("successYn","Y");
                            resultMap.put("inviteYn","N");
                            resultMap.put("message","참여가 취소 되었습니다.");
                        } else {
                            resultMap.put("successYn","N");
                            resultMap.put("message","참여 취소 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                        }
                    } else {
                        // 참여 재요청
                        HashMap<String, Object> cancleMap = exqueryService.selectOne("cronies.app.openChat.selectOpenchatCancleMin", param);
                        if(exqueryService.update("cronies.app.openChat.setOpenChatReInvite", param) == 1){
                            resultMap.put("successYn","Y");
                            resultMap.put("inviteYn","Y");
                            resultMap.put("message","참여가 요청 되었습니다.");

                            //방장에게 참여알림
                            int min = 0;

                            //최근에 취소하고 다시 신청했으면 발송하지 않는다.
                            if(cancleMap != null){
                                min = Integer.parseInt(cancleMap.get("cancleTime").toString());
                            } else {
                                min = 999;
                            }

                            if(min > 3){
                                //alarmCd, userId, sendUserId, subMessage를 받아서 메시지 수신여부를 조회한다음, 허용일경우 메시지를 발송한다.
                                HashMap<String, Object> pushMap = new HashMap<String, Object>();
                                HashMap<String, Object> leaderMap = exqueryService.selectOne("cronies.app.openChat.getLeaderUserInfo", param);
                                String alarmCd = "AL_C05_01";
                                pushMap.put("alarmCd", alarmCd);
                                pushMap.put("userId", leaderMap.get("leaderUserId").toString());
                                pushMap.put("sendUserId", "");
                                pushMap.put("subMessage", leaderMap.get("nick").toString());//닉네임으로 사용
                                pushMap.put("pushType", "openChat");
                                pushMap.put("openChatKey", commonService.getEncoding(param.get("openChatKey").toString()));
                                pushService.sendPushMessage(pushMap);
                            }
                        } else {
                            resultMap.put("successYn","N");
                            resultMap.put("message","참여 요청 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                        }
                    }
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","리더는 참여 및 탈퇴를 하실 수 없습니다.");
                }
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChat(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        HashMap<String, Object> chatroomMap = new HashMap<String, Object>();
        HashMap<String, Object> chatroomCheck = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        if(!param.get("location").toString().equals("")){
            param.put("locationYn", "Y");
        }

        //대화방 생성
        chatroomMap = exqueryService.selectOne("cronies.app.openChat.createChatroomNew", param);
        param.put("chatroomId", chatroomMap.get("chatroomId"));
        exqueryService.insert("cronies.app.openChat.createChatroomUser", param);

        returnMap = exqueryService.selectOne("cronies.app.openChat.setOpenChat", param);
        if(returnMap != null){
            param.put("openChatKey", commonService.getEncoding(returnMap.get("curId").toString()));
            param.put("openChatId", returnMap.get("curId"));
//            resultMap.put("openChatKey", commonService.getEncoding(String.valueOf(param.get("openChatKey"))));
            chatroomCheck = exqueryService.selectOne("cronies.app.openChat.chatroomCheck", param);
            resultMap.put("chatroomId",chatroomCheck.get("chatroomId"));
            resultMap.put("openChatKey",param.get("openChatKey"));
            resultMap.put("openChatId",returnMap.get("curId"));
            resultMap.put("successYn","Y");
            resultMap.put("message","작성이 완료 되었습니다.");
            // 작성자 Open Chat에 참여 추가
            exqueryService.insert("cronies.app.openChat.setOpenChatInviteRegUser", param);
            // 사진처리
            if(!param.get("fileId").equals("emtpy")){
                if(exqueryService.update("cronies.app.openChat.setOpenChatPhoto", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("message","작성이 완료 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","작성 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","작성 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChatDel(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> openChatInfoMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        // Open Chat delYn여부
        if(getOpenChatDelYn(param).get("delYn").equals("N")){
            openChatInfoMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatDetail", param);
            if(openChatInfoMap.get("regUserKey").toString().equals(param.get("userId").toString())){
                if(exqueryService.update("cronies.app.openChat.setOpenChatDel", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("message","소모임이 삭제 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","소모임 삭제 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","리더가 아니면 삭제 할 수 없습니다.");
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChatUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> openChatInfoMap = new HashMap<String, Object>();
        HashMap<String, Object> openChatPhotoMap = new HashMap<String, Object>();
        HashMap<String, Object> locationMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));
        param.put("fileKey", commonService.getDecoding(String.valueOf(param.get("fileKey"))));


        // Open Chat delYn여부
        if(getOpenChatDelYn(param).get("delYn").equals("N")) {
            openChatInfoMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatDetail", param);
            if (openChatInfoMap.get("regUserKey").toString().equals(param.get("userId").toString())) {

                param.put("locationYn", "N");
                if(!param.get("location").toString().equals("")){
//                    locationMap = commonService.Geocode(param.get("location").toString());
//                    param.put("lat", locationMap.get("lat"));
//                    param.put("lon", locationMap.get("lon"));
                    param.put("locationYn", "Y");
                }

                param.put("privateYn", openChatInfoMap.get("privateYn"));
                if(exqueryService.update("cronies.app.openChat.setOpenChatUpdate", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("message","수정되었습니다.");

                    //채팅방 타이틀을 변경한다.
                    exqueryService.update("cronies.app.openChat.updateChatroomTitle", param);

                    // 사진이 등록되어져 있는지 조회
                    openChatPhotoMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatPhoto", param);
                    if(param.get("fileId").equals("0")){
                        param.put("fileId", openChatPhotoMap.get("attachFileId").toString());
                    }
                    if(openChatPhotoMap != null){
                        if(exqueryService.update("cronies.app.openChat.setOpenChatPhotoUpdate", param) == 1){
                            resultMap.put("successYn","Y");
                            resultMap.put("message","수정되었습니다.");

                            //채팅방 사진을 변경한다.
                            exqueryService.update("cronies.app.openChat.updateChatroomImgId", param);
                        } else {
                            resultMap.put("successYn","N");
                            resultMap.put("message","수정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                        }
                    } else {
                        if(!param.get("fileId").toString().equals("emtpy")){
                            if(exqueryService.insert("cronies.app.openChat.setOpenChatPhoto", param) == 1){
                                // 수정이지만 신규등록
                                resultMap.put("successYn","Y");
                                resultMap.put("message","수정되었습니다.");

                                //채팅방 사진을 변경한다.
                                exqueryService.update("cronies.app.openChat.updateChatroomImgId", param);
                            } else {
                                resultMap.put("successYn","N");
                                resultMap.put("message","수정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                            }
                        }
                    }
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","수정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }

            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","리더가 아니면 수정 할 수 없습니다.");
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 소모임입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setOpenChatReport(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getLeaderCheck(HashMap<String, Object> param) throws Exception {
        param.put("openChatId", commonService.getDecoding(param.get("openChatKey").toString()));
        return exqueryService.selectOne("cronies.app.openChat.getLeaderCheck", param);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatMyList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.openChat.getOpenChatMyList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
//            resultList.get(i).put("regUserIconFileKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("iconCd"))));
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatInviteList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.openChat.getOpenChatInviteList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
//            resultList.get(i).put("regUserIconFileKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("iconCd"))));
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatMyBookmarkList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.openChat.getOpenChatMyBookmarkList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
//            resultList.get(i).put("regUserIconFileKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("iconCd"))));
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getDefaultImgList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("cronies.app.openChat.getDefaultImgList", param);
        for(int i = 0; i < resultList.size(); i++){
            if(resultList.get(i).get("fileKey") != null){
                resultList.get(i).put("fileId", resultList.get(i).get("fileKey").toString());
                resultList.get(i).put("fileKey", commonService.getEncoding(String.valueOf(resultList.get(i).get("`fileKey"))));
            }
        }
        return resultList;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setUserLatLon(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        exqueryService.update("cronies.app.openChat.setUserLatLon", param);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getOpenChatUserAddr(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatUserAddr", param);

        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getOpenChatLatLon(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatLatLon", param);
        if(resultList != null) {
            for (HashMap<String, Object> result : resultList) {
                result.put("openChatId", result.get("openChatKey"));
                result.put("openChatKey", commonService.getEncoding(String.valueOf(result.get("openChatKey"))));
                result.put("regUserKey", commonService.getEncoding(String.valueOf(result.get("regUserKey"))));
            }
        }
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getPrivateYnCountCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> subscribeInfoMap = storeCommonService.getUserSubscribeInfo(param);
        HashMap<String, Object> count = exqueryService.selectOne("cronies.app.openChat.setPrivateCount", param);

        int privateCount = Integer.parseInt(count.get("private").toString());
        String successYn = "N";
        if(subscribeInfoMap.get("subscribeYn").toString().equals("Y")) {
            switch(subscribeInfoMap.get("subscribeCd").toString()) {
                case "NAVY_PLUS":
                    if(privateCount < 3 ) {
                        successYn = "Y";
                    } else {
                        successYn = "N";
                        resultMap.put("message", "이미 활성화된 글의 수량이 초과되었습니다. 더 많은 글을 활성화 하려면 GOLD_PASS 로 변경해보세요.");
                        resultMap.put("subMessage", "구독화면으로 이동하시겠습니까?");
                        resultMap.put("router", "Y");
                    }
                    break;
                case "GOLD_PASS":
                    if(privateCount < 5 ) {
                        successYn = "Y";
                    } else {
                        successYn = "N";
                        resultMap.put("message", "이미 활성화된 글의 수량이 초과되었습니다.");
                        resultMap.put("subMessage", "");
                        resultMap.put("router", "N");
                    }
                    break;
                case "PLATINUM_PASS":
                    break;
                default:
                    resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
                    break;
            }
        } else {
            if(privateCount < 1 ) {
                successYn = "Y";
            } else {
                successYn = "N";
                resultMap.put("message", "이미 활성화된 글의 수량이 초과되었습니다. 더 많은 소모임을 활성화 하려면 구독서비스를 이용하세요.");
                resultMap.put("subMessage", "구독화면으로 이동하시겠습니까?");
                resultMap.put("router", "Y");
            }
        }

        resultMap.put("successYn", successYn);
        return resultMap;

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCheckPrivateYn(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatKey", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));

        // 현재 privateYn 값을 체크
        HashMap<String, Object> userPrivateYn = exqueryService.selectOne("cronies.app.openChat.userPrivateYnCheck", param);
        // 기존 값이 Y라면 N으로, N 이라면 Y로 설정
        String updatePrivateYn = "Y".equals(userPrivateYn.get("privateYn").toString()) ? "N" : "Y";
        try {
            // Open Chat delYn 여부
            if("N".equals(getOpenChatDelYn(param).get("delYn").toString())) {
                HashMap<String, Object> count = exqueryService.selectOne("cronies.app.openChat.setPrivateCount", param);
                HashMap<String, Object> subscribeInfoMap = storeCommonService.getUserSubscribeInfo(param);

                int privateCount = Integer.parseInt(count.get("private").toString());

                if(subscribeInfoMap.get("subscribeYn").toString().equals("Y")) {
                    switch(subscribeInfoMap.get("subscribeCd").toString()) {
                        case "NAVY_PLUS":
                            if(updatePrivateYn.equals("N")){
                                param.put("updatePrivateYn", updatePrivateYn);
                                exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                                resultMap.put("successYn", "Y");
                            } else {
                                if(privateCount < 3){
                                    param.put("updatePrivateYn", updatePrivateYn);
                                    exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                                    resultMap.put("successYn", "Y");
                                } else {
                                    resultMap.put("message", "활성화된 글의 수량이 초과되었습니다.");
                                    resultMap.put("successYn", "N");
                                }
                            }
                            break;
                        case "GOLD_PASS":
                            if(updatePrivateYn.equals("N")){
                                param.put("updatePrivateYn", updatePrivateYn);
                                exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                                resultMap.put("successYn", "Y");
                            } else {
                                if(privateCount < 5){
                                    param.put("updatePrivateYn", updatePrivateYn);
                                    exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                                    resultMap.put("successYn", "Y");
                                } else {
                                    resultMap.put("message", "활성화된 글의 수량이 초과되었습니다.");
                                    resultMap.put("successYn", "N");
                                }
                            }
                            break;
                        case "PLATINUM_PASS":
                            break;
                        default:
                            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
                            break;
                    }

                } else {
                    if(updatePrivateYn.equals("N")){
                        param.put("updatePrivateYn", updatePrivateYn);
                        exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                        resultMap.put("successYn", "Y");
                    } else {
                        if(privateCount < 1){
                            param.put("updatePrivateYn", updatePrivateYn);
                            exqueryService.update("cronies.app.openChat.setCheckPrivateYn", param);
                            resultMap.put("successYn", "Y");
                        } else {
                            resultMap.put("message", "활성화된 글의 수량이 초과되었습니다.");
                            resultMap.put("successYn", "N");
                        }
                    }
                }
                resultMap.put("privateYn", updatePrivateYn);
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "이미 삭제 된 소모임입니다.");
            }
        } catch (DuplicateKeyException ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }


    public int getOpenchatId(HashMap<String, Object> param) throws Exception {
        return Integer.parseInt(commonService.getDecoding(param.get("openChatKey").toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getOpenChatMeetCategory(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatMeetCategory", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatMeetList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        if (param.containsKey("openchatKey")) {
            param.put("openchatId", (commonService.getDecoding(param.get("openchatKey").toString())));
        }

        HashMap<String, Object> attendMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatAttend", param);
        if(attendMap != null){
            resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatMeetList", param);

            for(int i = 0; resultList.size() > i; i++){
                resultList.get(i).put("userKey", commonService.getEncoding(resultList.get(i).get("createdUserId").toString()));
            }

            resultMap.put("successYn", "Y");
            resultMap.put("message", "");
            resultMap.put("resultList", resultList);
        } else {
            if (param.containsKey("categoryCd")) {
                if(param.get("categoryCd").toString().equals("MEET_GROUP")){
                    resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatMeetList", param);
                    resultMap.put("resultList", resultList);
                }
            }
            resultMap.put("successYn", "N");
            resultMap.put("message", "해당 소모임에 가입 후 이용 가능합니다.");
        }

        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> createOpenChatMeet(HashMap<String, Object> param) throws Exception {
        if (param.containsKey("openchatKey")) {
            param.put("openchatId", commonService.getDecoding(param.get("openchatKey").toString()));
        }
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        if (exqueryService.insert("cronies.app.openChat.createOpenChatMeet", param) == 1) {
            HashMap<String, Object> meetMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatMeetOne", param);
            if (exqueryService.insert("cronies.app.openChat.addMeetUser", meetMap) == 1) {
                resultMap.put("meetMap", meetMap);
                resultMap.put("successYn", "Y");
                resultMap.put("message", "저장되었습니다.");
            }
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "저장에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> updateOpenChatMeet(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        param.put("userId", param.get("ssUserId"));
        param.put("openChatId", commonService.getDecoding(param.get("openChatKey").toString()));

        if (exqueryService.update("cronies.app.openChat.updateOpenChatMeet", param) > 0) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "저장되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "저장에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> deleteOpenChatMeet(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        if (exqueryService.update("cronies.app.openChat.deleteOpenChatMeet", param) > 0) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "삭제되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "삭제에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getMeetUserList(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.openChat.getMeetUserList", param);
        Iterator<HashMap<String, Object>> iterator = resultList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> meetUser = iterator.next();
            meetUser.put("userKey", commonService.getEncoding(meetUser.get("userId").toString()));
        }
        return resultList;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getAlbumList(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        param.put("openChatId", commonService.getDecoding(param.get("openChatKey").toString()));
        try {
            HashMap<String, Object> attendMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatAttend", param);
            if(attendMap != null){
                resultMap = exqueryService.selectPagingList("cronies.app.openChat.getAlbumList", param,
                    Integer.parseInt(param.get("pageNo").toString()),
                    Integer.parseInt(param.get("pageSize").toString()));
                resultMap.put("successYn", "Y");
                resultMap.put("message", "");
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "해당 소모임에 가입 후 이용 가능합니다.");
            }
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> addMeetUser(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> meetUserMap = exqueryService.selectOne("cronies.app.openChat.getAddMeetUserCheck", param);

        if(!MapUtils.isEmpty(meetUserMap) && meetUserMap.get("delYn") != null){
            if(Integer.parseInt(meetUserMap.get("attendRange").toString()) < Integer.parseInt(meetUserMap.get("meetRange").toString())){
                if (exqueryService.update("cronies.app.openChat.updateMeetUser", param) == 1) {
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "참여되었습니다.");
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "참여 가능한 인원이 초과되었습니다.");
            }
        } else if (!MapUtils.isEmpty(meetUserMap) && meetUserMap.get("delYn") == null) {
            if(Integer.parseInt(meetUserMap.get("attendRange").toString()) < Integer.parseInt(meetUserMap.get("meetRange").toString())){
                if(exqueryService.insert("cronies.app.openChat.addMeetUser", param) == 1){
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "참여되었습니다.");
                } else {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "참여에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "참여 가능한 인원이 초과되었습니다.");
            }
        } /*else if (meetUserMap.get("delYn").toString().equals("Y")) {
            if(Integer.parseInt(meetUserMap.get("attendRange").toString()) < Integer.parseInt(meetUserMap.get("meetRange").toString())){
                if(exqueryService.insert("cronies.app.openChat.addMeetUser", param) == 1){
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "참여되었습니다.");
                } else {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "참여에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "참여 가능한 인원이 초과되었습니다.");
            }
        }*/ else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "참여에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> deleteMeetUser(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> meetUserMap = exqueryService.selectOne("cronies.app.openChat.getMeetUserCheck", param);
        if (MapUtils.isEmpty(meetUserMap)) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "참여한 모임이 아닙니다.");
        } else if (exqueryService.insert("cronies.app.openChat.deleteMeetUser", param) == 1) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "참여취소 되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "취소에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatCommentList(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();

        HashMap<String, Object> contentMap = exqueryService.selectOne("cronies.app.openChat.getMeetContentData", param);
        contentMap.put("userKey", commonService.getEncoding(contentMap.get("createdUserId").toString()));
        resultMap.put("contentMap", contentMap);

        // 댓글조회
        List<HashMap<String, Object>> parentCommentList = exqueryService.selectList("cronies.app.openChat.getMeetCommentList", param);
        resultMap.put("commentList", parentCommentList);

        // 대댓글조회
        Iterator<HashMap<String, Object>> iterator = parentCommentList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> comment = iterator.next();
            comment.put("userId", comment.get("regUserId"));
            List<HashMap<String, Object>> childCommentList = exqueryService.selectList("cronies.app.openChat.getMeetChildCommentList", comment);
            comment.put("childCommentList", childCommentList);
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> addMeetComment(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));
        if (param.containsKey("isChild") && param.get("isChild").equals("Y") && param.containsKey("parentCommentId")) {
            param.put("parentCommentId", param.get("parentCommentId"));
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> delCheckMap = exqueryService.selectOne("cronies.app.openChat.openchatDelCheck", param);
        if (!MapUtils.isEmpty(delCheckMap)) {
            if (delCheckMap.get("delYn").equals("Y")) {
                resultMap.put("successYn", "Y");
                resultMap.put("message", "삭제된 모임입니다.");
            } else {
                HashMap<String, Object> seqMap = exqueryService.selectOne("cronies.app.openChat.getMeetCommentSeq", new HashMap<>());
                param.put("commentId", seqMap.get("commentId"));
                if (!param.get("mainFileId").toString().isEmpty()) {
                    exqueryService.insert("cronies.app.openChat.insertMeetCommentFile", param);
                }
                if (exqueryService.insert("cronies.app.openChat.addMeetComment", param) == 1) {
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "댓글이 등록되었습니다.");

                    HashMap<String, Object> pushMap = new HashMap<String, Object>();
                    String sendUserId = "";
                    boolean isSend = false;

                    if (param.get("state").toString().equals("new")){
                        HashMap<String, Object> articleUserMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatMeetRegUserId", param);
                        sendUserId = articleUserMap.get("createdUserId").toString();
                        pushMap.put("userId", sendUserId);
                        pushMap.put("alarmCd", "AL_C08_01");
                        isSend = true;
                    } else if (param.get("state").toString().equals("child")) {
                        HashMap<String, Object> commentUserMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatMeetCommentRegUserId", param);
                        sendUserId = commentUserMap.get("regUserId").toString();
                        pushMap.put("userId", sendUserId);
                        pushMap.put("alarmCd", "AL_C08_02");
                        isSend = true;
                    } else {
                        isSend = false;
                    }

                    pushMap.put("sendUserId", "");
                    pushMap.put("subMessage", "");

                    if (!sendUserId.equals(param.get("ssUserId").toString()) && isSend) {
                        pushMap.put("pushType", "openChatComment");
                        pushMap.put("openChatKey", commonService.getEncoding(param.get("meetId").toString()));
                        pushService.sendPushMessage(pushMap);
                    }
                } else {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "댓글 등록에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "모임이 없습니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> updateMeetComment(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        if (exqueryService.insert("cronies.app.openChat.updateMeetComment", param) == 1) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "수정되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "수정에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> deleteMeetComment(HashMap<String, Object> param) throws Exception {
        param.put("userId", param.get("ssUserId"));

        HashMap<String, Object> resultMap = new HashMap<>();
        if (exqueryService.insert("cronies.app.openChat.deleteMeetComment", param) == 1) {
            resultMap.put("successYn", "Y");
            resultMap.put("message", "댓글이 삭제 되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "삭제에 실패했습니다. 해당현상이 반복될 시 문의 부탁드립니다.");
        };
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getOpenChatMeetEdit(HashMap<String, Object> param) throws Exception {
        param.put("openChatId", commonService.getDecoding(param.get("openChatKey").toString()));
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.openChat.getOpenChatMeetEdit", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatAttendCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> attendMap = exqueryService.selectOne("cronies.app.openChat.getOpenChatAttendCheck", param);
        if (MapUtils.isEmpty(attendMap) || Integer.parseInt(attendMap.get("cnt").toString()) == 0) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "해당 소모임에 가입 후 이용 가능합니다.");
        } else {
            resultMap.put("successYn", "Y");
        }
        return resultMap;
    }

    // 한줄소개 업데이트
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatSendMessage(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();

        if(exqueryService.update("cronies.app.openChat.getOpenChatSendMessage", param) == 1 ){
            resultMap.put("successYn", "Y");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류");
        }
        return resultMap;
    }

    // 한줄소개 업데이트
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> updateAboutMeMessage(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();

        if(exqueryService.update("cronies.app.openChat.updateAboutMeMessage", param) == 1 ){
            resultMap.put("successYn", "Y");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> openChatMeetInfo(HashMap<String, Object> param) throws Exception {
        param.put("meetId", commonService.getDecoding(param.get("meetKey").toString()));

        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> infoMap = exqueryService.selectOne("cronies.app.openChat.openChatMeetInfo", param);
        if (!MapUtils.isEmpty(infoMap)) {
            if (infoMap.get("openchatDelYn").equals("Y")) {
                resultMap.put("successYn", "N");
                resultMap.put("message", "삭제된 소모임 입니다.");
            } else if (infoMap.get("meetDelYn").equals("Y")) {
                resultMap.put("successYn", "N");
                resultMap.put("message", "삭제된 게시글 입니다.");
            } else {
                resultMap.putAll(infoMap);
                resultMap.put("successYn", "Y");
            }
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "해당 소모임이 존재하지 않습니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getOpenChatKey(HashMap<String, Object> param) throws Exception {
        String openchatId = exqueryService.selectOne("cronies.app.openChat.getOpenChatKey", param).get("openchatId").toString();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("openChatKey", commonService.getEncoding(openchatId));
        return resultMap;
    }

}
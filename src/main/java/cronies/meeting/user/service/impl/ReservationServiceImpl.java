package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.InviteService;
import cronies.meeting.user.service.ReservationService;
import cronies.meeting.user.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@Service("ReservationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;

    @Autowired
    private InviteService inviteService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getIsReservation(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        HashMap<String, Object> setUserMap = new HashMap<String, Object>();
        HashMap<String, Object> completeMap = new HashMap<String, Object>();
        resultMap.put("successYn", "Y");

        try {
            // 이미 가입 된 사용자인지 (혹시몰라서)
            completeMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCodeByPhone", param);
            if(completeMap == null){

                returnMap = exqueryService.selectOne("cronies.app.reservation.getIsReservation", param);
                // 인증코드 생성
                String numCode = commonService.getNumCode(6);
                param.put("authCode", numCode);
                if(returnMap == null){
                    // 사전예약 첫 진행
                    // 유저 정보 임시 저장 ( 회원가입시 merge 로 처리 )
                    setUserMap = exqueryService.selectOne("cronies.app.reservation.setMergeUser", param);
                    param.put("userId", setUserMap.get("curId"));
                    // 해당 번호로 정보 insert 및 문자발송
                    exqueryService.insert("cronies.app.reservation.insertReservation", param);

                    Boolean isExist = false;
                    String nanoId = "";

                    // 초대코드생성 및 저장
                    while(!isExist){
                        // nanoId 생성
                        nanoId = commonService.getNanoId(6);
                        // nanoId 중복체크
                        param.put("nanoId", nanoId);
                        if(inviteService.checkNanoId(param) == null){
                            exqueryService.insert("cronies.app.invite.insertInviteInfo", param);
//                        resultMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
                            isExist = true;
                        }
                    }

                    // SMS 발송
                    HashMap<String, Object> smsMap = new HashMap<String, Object>();
                    smsMap.put("phoneNo", param.get("phone"));
                    smsMap.put("msg", "[NAVY] 본인확인을 위해 인증번호 ["+numCode+"]를 입력해주세요.");
                    exqueryService.insert("cronies.app.push.insertSmsSend", smsMap);

                    resultMap.put("message", "인증번호가 발송 되었습니다.");
                } else {
                    if(returnMap.get("reservationTime") == null){
                        // 사전예약이 끝나지 않은 사람이 재인증 하였을 때
                        // 해당 번호로 정보 update 및 문자발송
                        exqueryService.update("cronies.app.reservation.updateReservationAuthCode", param);

                        // SMS 발송
                        HashMap<String, Object> smsMap = new HashMap<String, Object>();
                        smsMap.put("phoneNo", param.get("phone"));
                        smsMap.put("msg", "[NAVY] 본인확인을 위해 인증번호 ["+numCode+"]를 입력해주세요.");
                        exqueryService.insert("cronies.app.push.insertSmsSend", smsMap);

                        resultMap.put("message", "인증번호가 발송 되었습니다.");
                    } else {
                        // 이미 사전예약을 하였을 경우
                        resultMap.put("successYn", "N");
                        resultMap.put("message", "이미 사전예약을 하셨습니다.");
                    }
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "이미 가입 된 사용자입니다.");
            }
        } catch (Exception e) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "본인인증 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setPreReservation(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        HashMap<String, Object> inviteMap = new HashMap<String, Object>();
        resultMap.put("successYn", "N");

        try {
            // 사전예약 데이터 확인
            returnMap = exqueryService.selectOne("cronies.app.reservation.getIsReservation", param);

            if(returnMap == null){
                // 인증코드 발송 시 row 데이터가 생성이 제대로 되지 않았을 듯..?
                resultMap.put("message", "인증 오류가 발생하였습니다. 새로고침 후 인증번호를 재발송 부탁드립니다.");
            } else {
                if(returnMap.get("reservationTime") == null){
                    // 인증코드 시간이 지났는지 여부
                    if(returnMap.get("isComplete").toString().equals("Y")){
                        // 시간이 지나지않음 - 정상
                        // 인증코드 비교
                        if(returnMap.get("authCode").toString().equals(param.get("inputAuthCode").toString())){
                            // 동일 할 시 사전예약 진행
                            // 초대한 사람 초대코드로 사용자 조회
                            // 초대코드가 유효한지 확인 (inviteCode 필요)
                            param.put("userId", returnMap.get("regUserId"));
                            inviteMap = exqueryService.selectOne("cronies.app.invite.checkTargetUserInviteCode", param);
                            if(inviteMap != null){
                                // 초대코드가 유효함 - 그대로 진행
                                param.put("targetUserId", inviteMap.get("userId"));
                                param.put("inviteUserId", returnMap.get("regUserId"));
                                param.put("pointCd", "NAVY01");
                                param.put("changePoint", 1000);
                                // 친구코드 등록
                                exqueryService.insert("cronies.app.invite.insertInviteCode", param);
                                exqueryService.insert("cronies.app.invite.insertInvitePointHis", param);
                                exqueryService.update("cronies.app.invite.updateInvitePoint", param);
                                inviteMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
                                // 사전예약 완료 update
                                exqueryService.update("cronies.app.reservation.completeReservation", param);
                                resultMap.put("successYn", "Y");
                                resultMap.put("message", "사전예약이 완료되었습니다!");

                                // 가입자 초대코드로 만든 URL SMS 발송
                                // SMS 발송
                                HashMap<String, Object> mmsMap = new HashMap<String, Object>();
                                mmsMap.put("phoneNo", param.get("phone"));
                                mmsMap.put("title", "[네이비]");
                                mmsMap.put("msg", "[네이비] \n" +
                                        "성공과 신뢰를 추구하는 하이엔드 데이팅 플랫폼 \"네이비\" 사전예약을 해주셔서 감사드립니다.  \n" +
                                        "\n" +
                                        "★ EVENT ★\n" +
                                        "“무한 감동의 포인트 쓰나미가 몰려온다“\n" +
                                        "\n" +
                                        "▶나의 링크\n" +
                                        "http://navy-sign.com/"+inviteMap.get("inviteCode")+
                                        "\n" +
                                        "■기간: 2023.8.1(화)~8.16(오픈예정)\n" +
                                        "\n" +
                                        "■참여방법\n" +
                                        "①나의 링크를 첨부하고 전달!\n" +
                                        "②링크를 통해 누군가 사전예약을 진행!\n" +
                                        "③한명당 1000원 무제한 적립!!\n" +
                                        "\n" +
                                        "지금까지는 없던 막대한 콘텐츠와 무한한 재미를 선사해 드립니다! \n" +
                                        "여러분들께 많은 관심과 초대를 부탁드리며 \n" +
                                        "저희는 8월 16일날 다시 뵙겠습니다 :) \n" +
                                        "\n" +
                                        "◈인스타그램 공식계정\n" +
                                        "https://www.instagram.com/navy_app/\n" +
                                        "\n" +
                                        "◈트위터 공식계정\n" +
                                        "https://twitter.com/navy_app");
                                exqueryService.insert("cronies.app.push.insertMmsSend", mmsMap);
                            } else {
                                // 초대코드가 유효하지 않음
//                                resultMap.put("message", "유효하지 않은 초대 URL입니다.");
                                // 초대코드 없이 사용자 등록 및 사전예약
                                param.put("inviteUserId", returnMap.get("regUserId"));
                                inviteMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
                                // 사전예약 완료 update
                                exqueryService.update("cronies.app.reservation.completeReservationNoTarget", param);
                                resultMap.put("successYn", "Y");
                                resultMap.put("message", "사전예약이 완료되었습니다!");
                                resultMap.put("inviteCode", inviteMap.get("inviteCode")); // 임시

                                // 가입자 초대코드로 만든 URL SMS 발송
                                // SMS 발송
                                HashMap<String, Object> mmsMap = new HashMap<String, Object>();
                                mmsMap.put("phoneNo", param.get("phone"));
                                mmsMap.put("title", "[네이비]");
                                mmsMap.put("msg", "[네이비] \n" +
                                        "성공과 신뢰를 추구하는 하이엔드 데이팅 플랫폼 \"네이비\" 사전예약을 해주셔서 감사드립니다. \n" +
                                        "\n" +
                                        "★ EVENT ★\n" +
                                        "“무한 감동의 포인트 쓰나미가 몰려온다“\n" +
                                        "\n" +
                                        "▶나의 링크\n" +
                                        "http://navy-sign.com/"+inviteMap.get("inviteCode")+
                                        "\n" +
                                        "■기간: 2023.8.1(화)~8.16(오픈예정)\n" +
                                        "■참여방법\n" +
                                        "①나의 링크를 첨부하고 전달!\n" +
                                        "②링크를 통해 누군가 사전예약을 진행!\n" +
                                        "③한명당 1000원 무제한 적립!!\n" +
                                        "\n" +
                                        "지금까지는 없던 막대한 콘텐츠와 무한한 재미를 선사해 드립니다! \n" +
                                        "여러분들께 많은 관심과 초대를 부탁드리며 \n" +
                                        "저희는 8월 16일날 다시 뵙겠습니다 :) \n" +
                                        "\n" +
                                        "◈인스타그램 공식계정\n" +
                                        "https://www.instagram.com/navy_app/\n" +
                                        "\n" +
                                        "◈트위터 공식계정\n" +
                                        "https://twitter.com/navy_app");
                                exqueryService.insert("cronies.app.push.insertMmsSend", mmsMap);
                            }
                        } else {
                            // 인증코드와 입력 한 코드가 다를 시
                            resultMap.put("message", "인증번호가 일치하지 않습니다. 다시 입력해주세요.");
                        }
                    } else {
                        // 시간이 지남
                        resultMap.put("message", "인증시간이 지났습니다. 인증번호를 다시 발송하고 진행해주세요.");
                    }
                } else {
                    // 이미 사전예약을 하였을 경우
                    resultMap.put("message", "이미 사전예약을 하셨습니다.");
                }
            }
        } catch (Exception e) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "인증 오류가 발생하였습니다. 새로고침 후 인증번호를 재발송 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getIsReservation2(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> userMap = new HashMap<String, Object>();
        resultMap.put("successYn", "Y");

        try {
            // 가입 된 사용자인지 확인
            userMap = exqueryService.selectOne("cronies.app.reservation.getUserByPhone", param);
            if(userMap != null){
                // 가입이 된 사용자 일 때
                // 인증코드 생성
                String numCode = commonService.getNumCode(6);
                param.put("verificationCode", numCode);
                param.put("userId", userMap.get("userId"));

                // 해당 번호로 정보 update
                exqueryService.update("cronies.app.reservation.mergeVerification", param);

                // SMS 발송
                HashMap<String, Object> smsMap = new HashMap<String, Object>();
                smsMap.put("phoneNo", param.get("phone"));
                smsMap.put("msg", "[NAVY] 본인확인을 위해 인증번호 ["+numCode+"]를 입력해주세요.");
                exqueryService.insert("cronies.app.push.insertSmsSend", smsMap);

                resultMap.put("message", "인증번호가 발송 되었습니다.");
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "사전예약을 하지 않으셨습니다.");
            }
        } catch (Exception e) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "본인인증 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getInvitePreReservationCnt(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        HashMap<String, Object> userMap = new HashMap<String, Object>();
        HashMap<String, Object> completeMap = new HashMap<String, Object>();
        resultMap.put("successYn", "N");

        try {

            // 가입 된 사용자인지 확인
            userMap = exqueryService.selectOne("cronies.app.reservation.getUserByPhone", param);
            if(userMap != null) {
                // 가입이 된 사용자 일 때
                param.put("userId", userMap.get("userId"));
                // 인증코드 데이터 확인
                returnMap = exqueryService.selectOne("cronies.app.reservation.getUserVerificationInfo", param);
                if(returnMap == null){
                    // 인증코드 발송 시 row 데이터가 생성이 제대로 되지 않았을 듯..?
                    resultMap.put("message", "인증 오류가 발생하였습니다. 새로고침 후 인증번호를 재발송 부탁드립니다.");
                } else {
                    // 인증코드 시간이 지났는지 여부
                    if(returnMap.get("isComplete").toString().equals("Y")){
                        // 시간이 지나지않음 - 정상
                        // 인증코드 비교
                        if(returnMap.get("verificationCode").toString().equals(param.get("inputAuthCode").toString())){
                            // 동일 할 시 초대 수 조회

                            // 본인 INVITE_CODE 가 생성되어져 있는지 확인
                            completeMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCodeByPhone2", param);
                            if(completeMap != null){
                                resultMap.put("successYn", "Y");
                                resultMap.put("point", completeMap.get("invitePoint"));
                                resultMap.put("count", completeMap.get("inviteCnt"));
                            } else {
                                // 본인초대코드가 등록되어져 있지 않을 경우 초대코드 등록
                                Boolean isExist = false;
                                String nanoId = "";

                                // 초대코드생성 및 저장
                                while(!isExist){
                                    // nanoId 생성
                                    nanoId = commonService.getNanoId(6);
                                    // nanoId 중복체크
                                    param.put("nanoId", nanoId);
                                    if(inviteService.checkNanoId(param) == null){
                                        exqueryService.insert("cronies.app.invite.insertInviteInfo", param);
                                        isExist = true;
                                    }
                                }
                                resultMap.put("successYn", "Y");
                                resultMap.put("point", "0");
                                resultMap.put("count", "0");
                            }
                        } else {
                            // 인증코드와 입력 한 코드가 다를 시
                            resultMap.put("message", "인증번호가 일치하지 않습니다. 다시 입력해주세요.");
                        }
                    } else {
                        // 시간이 지남
                        resultMap.put("message", "인증시간이 지났습니다. 인증번호를 다시 발송하고 진행해주세요.");
                    }
                }

            } else {
                resultMap.put("message", "사전예약을 하지 않으셨습니다.");
            }
        } catch (Exception e) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "인증 오류가 발생하였습니다. 새로고침 후 인증번호를 재발송 부탁드립니다.");
        }

        return resultMap;
    }

}

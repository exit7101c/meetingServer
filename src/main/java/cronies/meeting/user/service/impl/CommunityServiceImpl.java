package cronies.meeting.user.service.impl;

import com.nse.config.CryptoUtil;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.CommunityService;
import cronies.meeting.user.service.PushService;
import io.lettuce.core.ScriptOutputType;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;
import select.spring.util.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service("CommunityService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommunityServiceImpl implements CommunityService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;

    @Autowired
    HttpSession session;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        resultMap = exqueryService.selectPagingList("cronies.app.community.getCommunityList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        dataList = (List<HashMap<String, Object>>) resultMap.get("result");
        Iterator<HashMap<String, Object>> iterator = dataList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> data = iterator.next();
            data.put("articleKey", commonService.getEncoding(String.valueOf(data.get("articleKey"))));
            if(data.get("photoKey") != null){
                data.put("photoKey", commonService.getEncoding(String.valueOf(data.get("photoKey"))));
            }
            data.put("createdUserKey", commonService.getEncoding(String.valueOf(data.get("createdUserId"))));
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityFeedList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        List<HashMap<String, Object>> adList = new ArrayList<>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> userViewMap = new HashMap<String, Object>();

        resultList = exqueryService.selectList("cronies.app.community.getCommunityFeedList", param);

        Iterator<HashMap<String, Object>> iterator = resultList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> data = iterator.next();
            data.put("articleKey", commonService.getEncoding(String.valueOf(data.get("articleKey"))));
            if(data.get("photoKey") != null){
                data.put("photoKey", commonService.getEncoding(String.valueOf(data.get("photoKey"))));
            }
            data.put("createdUserKey", commonService.getEncoding(String.valueOf(data.get("createdUserId"))));


            param.put("articleId", String.valueOf(data.get("articleId")));
            //해당 글 조회여부 조회(최근 24시간)
            userViewMap = exqueryService.selectOne("cronies.app.community.getCommunityArticleIsView", param);
            //최근 조회이력이 없을경우 조회수 증가
            if(userViewMap == null){
                exqueryService.insert("cronies.app.community.insertCommunityArticleView", param);
                setViewCount(String.valueOf(data.get("articleId")));
            }

        }

        // 광고쪽조회수
        // 최근 광고 리스트 20건 중 1건 조회
        adList = exqueryService.selectList("cronies.app.community.getCommunityAdList", param);

        iterator = adList.iterator();
        while(iterator.hasNext()) {
            HashMap<String, Object> data = iterator.next();

            param.put("articleId", String.valueOf(data.get("articleId")));
            //해당 글 조회여부 조회(최근 24시간)
            userViewMap = exqueryService.selectOne("cronies.app.community.getCommunityArticleIsView", param);
            //최근 조회이력이 없을경우 조회수 증가
            if(userViewMap == null){
                exqueryService.insert("cronies.app.community.insertCommunityArticleView", param);
                setViewCount(String.valueOf(data.get("articleId")));
            }

        }

        resultMap.put("result", resultList);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Boolean setViewCount(String articleId) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("articleId", articleId);
        Boolean res = true;
        exqueryService.update("cronies.app.community.updateCommunityViewCount", paramMap);
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityDetail(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> articleMap = new HashMap<String, Object>();
        HashMap<String, Object> userViewMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> parentCommentList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));

        if(!param.get("type").toString().equals("Home")){
            param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        } else {
            param.put("articleId", param.get("articleKey"));
        }

        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){

            //해당 글 조회여부 조회(최근 24시간)
            userViewMap = exqueryService.selectOne("cronies.app.community.getCommunityArticleIsView", param);
            //최근 조회이력이 없을경우 조회수 증가
            if(userViewMap == null){
                exqueryService.insert("cronies.app.community.insertCommunityArticleView", param);
                setViewCount(param.get("articleId").toString());
            }

            articleMap = exqueryService.selectOne("cronies.app.community.getCommunityDetail", param);
            articleMap.put("articleKey", commonService.getEncoding(String.valueOf(articleMap.get("articleId"))));
            // attachFileId 임시사용
            articleMap.put("attachFileId", articleMap.get("attachFileKey"));
            if(articleMap.get("attachFileKey") != null){
                articleMap.put("attachFileKey",commonService.getEncoding(String.valueOf(articleMap.get("attachFileKey"))));
            }
            resultMap.put("articleMap", articleMap);

            // 댓글조회
            parentCommentList = exqueryService.selectList("cronies.app.community.getCommunityParentCommentList", param);
            for(int i=0; parentCommentList.size() > i; i++){
                List<HashMap<String, Object>> childCommentList = new ArrayList<>();
                param.put("commentId", parentCommentList.get(i).get("commentKey"));
                // 대댓글 조회
                childCommentList = exqueryService.selectList("cronies.app.community.getCommunityChildCommentList", param);
                for(int j=0; childCommentList.size() > j; j++){
                    childCommentList.get(j).put("commentKey",commonService.getEncoding(String.valueOf(childCommentList.get(j).get("commentKey"))));
                    childCommentList.get(j).put("parentCommentKey",commonService.getEncoding(String.valueOf(childCommentList.get(j).get("parentCommentKey"))));
                }
                parentCommentList.get(i).put("commentKey",commonService.getEncoding(String.valueOf(parentCommentList.get(i).get("commentKey"))));
                parentCommentList.get(i).put("childCommentList", childCommentList);
            }
            resultMap.put("commentList", parentCommentList);

            resultMap.put("successYn","Y");
            resultMap.put("message","");

        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityInsert(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        try {
            returnMap = exqueryService.selectOne("cronies.app.community.setCommunityInsert", param);
            param.put("articleId", returnMap.get("articleId"));

            //닉네임 업데이트
            exqueryService.update("cronies.app.community.updateCommunityNickUpdate", param);

            resultMap.put("articleKey", commonService.getEncoding(String.valueOf(param.get("articleId"))));
            resultMap.put("successYn","Y");
            resultMap.put("message","게시글이 등록되었습니다.");

        } catch (Exception e) {
            resultMap.put("successYn","N");
            resultMap.put("message","게시글 등록 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        // param.put("mainFileId", commonService.getDecoding(String.valueOf(param.get("mainFileId"))));

        if(exqueryService.update("cronies.app.community.setCommunityUpdate", param) == 1){

            //닉네임 업데이트
            exqueryService.update("cronies.app.community.updateCommunityNickUpdate", param);

            try {
                if(!param.get("mainFileId").toString().equals("")){
                    exqueryService.insert("cronies.app.community.setCommunityFileInsert", param);
                } else {
                    exqueryService.update("cronies.app.community.setCommunityFileNullUpdate", param);
                }
            } catch (Exception e) {
                exqueryService.update("cronies.app.community.setCommunityFileNullUpdate", param);
                e.printStackTrace();
            }
            resultMap.put("successYn","Y");
            resultMap.put("message","게시글이 수정되었습니다.");
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","게시글 수정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityDelYn(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> delYnMap = new HashMap<String, Object>();
        // Open Chat delYn여부
        delYnMap = exqueryService.selectOne("cronies.app.community.getCommunityDelYn", param);
        return delYnMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityBookmark(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> bookmarkMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));

        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){
            bookmarkMap = exqueryService.selectOne("cronies.app.community.getCommunityBookmark", param);
            if(bookmarkMap == null){
                if(exqueryService.insert("cronies.app.community.setCommunityBookmarkAdd", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("bookmarkYn","Y");
                    resultMap.put("message","찜이 설정 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","찜 설정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                if(exqueryService.delete("cronies.app.community.setCommunityBookmarkDel", param) == 1){
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
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityComment(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> parentCommentMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));

        if(!param.get("type").toString().equals("Home")){
            param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        } else {
            param.put("articleId", param.get("articleKey"));
        }

        if(param.get("contents").toString().equals("") && param.get("mainFileId").toString().equals("")){
            resultMap.put("successYn","N");
            resultMap.put("message","사진 또는 댓글내용을 작성해주세요.");
        } else {
            // Community delYn여부
            if(getCommunityDelYn(param).get("delYn").equals("N")){
                // 댓글 신규
                if(param.get("commentType").toString().equals("comment2")){
                    //대댓글일 경우 부모키가 있음
                    param.put("parentCommentId", commonService.getDecoding(String.valueOf(param.get("connectCommentKey"))));
                }

                //시퀀스 조회 COMMENT_ID_SEQ
                HashMap<String, Object> seqMap = exqueryService.selectOne("cronies.app.community.selectSeqComment", param);
                param.put("commentId", seqMap.get("commentId").toString());

                if(exqueryService.insert("cronies.app.community.setCommunityCommentInsert", param) == 1){
                    // 댓글카운트 올려버리기
                    exqueryService.update("cronies.app.community.updateArticleCnt", param);
                    if(!param.get("mainFileId").toString().equals("")){
                        exqueryService.insert("cronies.app.community.setCommunityCommentFileInsert", param);
                    } else {
                        exqueryService.update("cronies.app.community.setCommunityCommentFileNullUpdate", param);
                    }
                    resultMap.put("successYn","Y");
                    resultMap.put("message","댓글이 작성되었습니다.");

                    //게시글 작성자에게 메시지 발송하기
                    //alarmCd, userId, sendUserId, subMessage를 받아서 메시지 수신여부를 조회한다음, 허용일경우 메시지를 발송한다.
                    HashMap<String, Object> pushMap = new HashMap<String, Object>();
                    String sendUserId = "";
                    boolean isSend = false;


                    if(param.get("commentType").toString().equals("comment1")){
                        //일반댓글
                        //글작성자 userId조회
                        HashMap<String, Object> articleUserMap = exqueryService.selectOne("cronies.app.community.getCommunityRegUserId", param);
                        sendUserId = articleUserMap.get("createdUserId").toString();
                        pushMap.put("userId", sendUserId);
                        pushMap.put("alarmCd", "AL_C08_01");
                        isSend = true;
                    } else if(param.get("commentType").toString().equals("comment2")) {
                        //대댓글
                        HashMap<String, Object> commentUserMap = exqueryService.selectOne("cronies.app.community.getCommunityCommentRegUserId", param);
                        sendUserId = commentUserMap.get("regUserId").toString();
                        pushMap.put("userId", sendUserId);
                        pushMap.put("alarmCd", "AL_C08_02");
                        isSend = true;
                    } else {
                        isSend = false;
                    }

                    pushMap.put("sendUserId", "");
                    pushMap.put("subMessage", "");

                    //내가작성한 글일경우 발송하지 않는다.
                    if(!sendUserId.equals(param.get("userId").toString())){
                        //처리여부
                        if(isSend){
                            if(param.get("type").toString().equals("contestDetail")){
                                pushMap.put("pushType", "contestDetail");
                                pushMap.put("articleKey", commonService.getEncoding(param.get("articleId").toString()));
                                pushService.sendPushMessage(pushMap);
                            } else if (param.get("type").toString().equals("community")) {
                                pushMap.put("pushType", "community");
                                pushMap.put("articleKey", commonService.getEncoding(param.get("articleId").toString()));
                                pushService.sendPushMessage(pushMap);
                            }
                        }
                    }
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","댓글 작성 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }


            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","이미 삭제 된 게시글입니다.");
            }
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityCommentUpdate(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> parentCommentMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));


        if(!param.get("type").toString().equals("Home")){
            param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        } else {
            param.put("articleId", param.get("articleKey"));
        }

        if(param.get("contents").toString().equals("") && param.get("mainFileId").toString().equals("")){
            resultMap.put("successYn","N");
            resultMap.put("message","사진 또는 댓글내용을 작성해주세요.");
        } else {
            // Community delYn여부
            if(getCommunityDelYn(param).get("delYn").equals("N")){
                param.put("commentId", commonService.getDecoding(String.valueOf(param.get("commentKey"))));
                    // 댓글 수정
                if(exqueryService.update("cronies.app.community.setCommunityCommentUpdate", param) == 1){
                    //이미지 처리
                    if(param.get("mainFileId") != null && !param.get("mainFileId").toString().equals("")){
                        exqueryService.insert("cronies.app.community.setCommunityCommentFileInsert", param);
                    } else {
                        exqueryService.update("cronies.app.community.setCommunityCommentFileNullUpdate", param);
                    }
                    resultMap.put("successYn","Y");
                    resultMap.put("message","댓글이 수정되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","댓글 수정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","이미 삭제 된 게시글입니다.");
            }

        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getCategoryList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        resultList = exqueryService.selectList("cronies.app.community.getCategoryList", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getSubCategoryList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        resultList = exqueryService.selectList("cronies.app.community.getSubCategoryList", param);
        return resultList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityCommentDel(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> parentCommentMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){
            if(param.get("commentKey") != null){
                param.put("commentId", commonService.getDecoding(String.valueOf(param.get("commentKey"))));
                if(exqueryService.insert("cronies.app.community.setCommunityCommentDelete", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("message","댓글이 삭제되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","댓글 삭제 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }

        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }

    /** 커뮤니티 좋아요/싫어요 **/
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setThumbsUpDown(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        if(!param.get("pageType").toString().equals("Home")){
            param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        } else {
            param.put("articleId", param.get("articleKey"));
        }

        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){
            if(!param.get("type").equals("")){
                String type = (String) param.getOrDefault("type", "");

                // 이력 확인 쿼리 수행
                Map<String, Object> isLikeChkMap = exqueryService.selectOne("cronies.app.community.isLikeChk", param);

                // 이력이 없으면 INSERT
                if (isLikeChkMap == null) {
                    if ("up".equals(type)) {
                        exqueryService.insert("cronies.app.community.setLike", param);
                    } else if ("down".equals(type)) {
                        exqueryService.insert("cronies.app.community.setDislike", param);
                    }
                } else {
                    // 이력이 있으면 type에 따라 좋아요/싫어요 여부 확인
                    switch (type) {
                        case "up":
                                // 좋아요 취소
                                if (!Objects.isNull(isLikeChkMap.get("likeSeq"))) {
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike", param);
                                } else if (isLikeChkMap.get("dislikeSeq") != null) {
                                    // 싫어요 취소 후 좋아요 등록
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike", param);
                                    exqueryService.insert("cronies.app.community.setLike", param);
                                }
                            break;
                        case "down":
                                // 싫어요 취소
                                if (!Objects.isNull(isLikeChkMap.get("dislikeSeq"))) {
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike", param);
                                } else if (isLikeChkMap.get("likeSeq") != null) {
                                    // 좋아요 취소 후 싫어요 등록
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike", param);
                                    exqueryService.insert("cronies.app.community.setDislike", param);
                                }
                            break;
                        default:
                            break;
                    }
                }
                // 좋아요, 싫어요 테이블 조회해서 카운팅 후 그 수만큼 INSERT
                HashMap<String, Object> countLikeMap = exqueryService.selectOne("cronies.app.community.selectCountLike", param);
                HashMap<String, Object> countDislikeMap = exqueryService.selectOne("cronies.app.community.selectCountDislike", param);
                param.put("likeCnt", countLikeMap.get("likeCnt"));
                param.put("badCnt", countDislikeMap.get("badCnt"));

                if(exqueryService.update("cronies.app.community.setThumbsUpDown", param) == 1){
                    resultMap.put("likeCnt",param.get("likeCnt"));
                    resultMap.put("badCnt",param.get("badCnt"));
                    resultMap.put("successYn","Y");
                    resultMap.put("message","이벤트 처리가 완료되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","이벤트 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }

    /** 댓글 좋아요/싫어요 **/
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setThumbsUpDown2(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        param.put("userId", param.get("ssUserId"));
//        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        if(!param.get("pageType").toString().equals("Home")){
            param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
            param.put("commentId", commonService.getDecoding(String.valueOf(param.get("commentKey"))));
        } else {
            param.put("articleId", param.get("articleKey"));
            param.put("commentId", commonService.getDecoding(String.valueOf(param.get("commentKey"))));
        }
        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){
            if(!param.get("type").equals("")){
                String type = (String) param.getOrDefault("type", "");

                // 이력 확인 쿼리 수행
                Map<String, Object> isLikeChkMap = exqueryService.selectOne("cronies.app.community.isLikeChk2", param);

                // 이력이 없으면 INSERT
                if (isLikeChkMap == null) {
                    if ("up".equals(type)) {
                        exqueryService.insert("cronies.app.community.setLike2", param);
                    } else if ("down".equals(type)) {
                        exqueryService.insert("cronies.app.community.setDislike2", param);
                    }
                } else {
                    // 이력이 있으면 type에 따라 좋아요/싫어요 여부 확인
                    switch (type) {
                        case "up":
                                // 좋아요 취소
                                if (!Objects.isNull(isLikeChkMap.get("likeSeq"))) {
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike2", param);
                                } else if (isLikeChkMap.get("dislikeSeq") != null) {
                                    // 싫어요 취소 후 좋아요 등록
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike2", param);
                                    exqueryService.insert("cronies.app.community.setLike2", param);
                                }
                            break;
                        case "down":
                                // 싫어요 취소
                                if (!Objects.isNull(isLikeChkMap.get("dislikeSeq"))) {
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike2", param);
                                } else if (isLikeChkMap.get("likeSeq") != null) {
                                    // 좋아요 취소 후 싫어요 등록
                                    exqueryService.delete("cronies.app.community.deleteLikeAndDislike2", param);
                                    exqueryService.insert("cronies.app.community.setDislike2", param);
                                }
                            break;
                        default:
                            break;
                    }
                }
                // 좋아요, 싫어요 테이블 조회해서 카운팅 후 그 수만큼 INSERT
                HashMap<String, Object> countLikeMap = exqueryService.selectOne("cronies.app.community.selectCountLike2", param);
                HashMap<String, Object> countDislikeMap = exqueryService.selectOne("cronies.app.community.selectCountDislike2", param);
                param.put("likeCnt", countLikeMap.get("likeCnt"));
                param.put("badCnt", countDislikeMap.get("badCnt"));

                if(exqueryService.update("cronies.app.community.setThumbsUpDown2", param) == 1){
                    resultMap.put("likeCnt",param.get("likeCnt"));
                    resultMap.put("badCnt",param.get("badCnt"));
                    resultMap.put("successYn","Y");
                    resultMap.put("message","이벤트 처리가 완료되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","이벤트 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityDel(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> parentCommentMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));

        // Community delYn여부
        if(getCommunityDelYn(param).get("delYn").equals("N")){
            parentCommentMap = exqueryService.selectOne("cronies.app.community.getCommunityDetail", param);


            if(parentCommentMap.get("isRegYn").equals("Y")){
                if(exqueryService.update("cronies.app.community.setCommunityDel", param) == 1){
                    resultMap.put("successYn","Y");
                    resultMap.put("message","게시글이 삭제 되었습니다.");
                } else {
                    resultMap.put("successYn","N");
                    resultMap.put("message","게시글 삭제 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            } else {
                resultMap.put("successYn","N");
                resultMap.put("message","관리자가 아니면 삭제 할 수 없습니다.");
            }
        } else {
            resultMap.put("successYn","N");
            resultMap.put("message","이미 삭제 된 게시글입니다.");
        }

        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityMyList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.community.getCommunityMyList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("articleId", result.get("articleKey"));
                result.put("articleKey", commonService.getEncoding(String.valueOf(result.get("articleKey"))));
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityCommentList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.community.getCommunityCommentList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("articleId", result.get("articleKey"));
                result.put("commentId", result.get("commentKey"));
                result.put("articleKey", commonService.getEncoding(String.valueOf(result.get("articleKey"))));
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityMyBookmarkList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.community.getCommunityMyBookmarkList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        if(resultMap != null) {
            resultList = (List<HashMap<String, Object>>) resultMap.get("result");

            for (HashMap<String, Object> result : resultList) {
                result.put("articleId", result.get("articleKey"));
                result.put("articleKey", commonService.getEncoding(String.valueOf(result.get("articleKey"))));
                result.put("isLoaded", true);
            }
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getCommunityUserNick(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.community.getCommunityUserNick", param);
        return resultList;
    }

    // 닉네임 중복체크
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getCommunityNickCheck(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        String successYn = "N";

        // param 체크
        if (param.containsKey("nick")) {
            HashMap<String, Object> chkNick = exqueryService.selectOne("cronies.app.community.getCommunityNickCheck", param);
            // 입력받은 닉네임이 있다면 result = N
            successYn = chkNick.get("result").toString();
        }
        resultMap.put("successYn", successYn);

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setSaveCommunityNickname(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        try {
            exqueryService.update("cronies.app.community.setSaveCommunityNickname", param);

            resultMap.put("successYn", "Y");
            resultMap.put("message", "커뮤니티 게시판 정보를 변경 하였습니다.");
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setCommunityChatRoom(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> chatroomMap = new HashMap<String, Object>();
        HashMap<String, Object> pushMap = new HashMap<>();
        param.put("type", param.get("messageType"));

        if(param.containsKey("partnersCode") && !param.get("partnersCode").toString().isEmpty()) {
            HashMap<String, Object> partnersUserInfo = exqueryService.selectOne("cronies.app.partners.partnersUserInfo", param);
            if (!MapUtils.isEmpty(partnersUserInfo)) {
                String targetUserId = partnersUserInfo.get("userId").toString();
                String targetUserNick = partnersUserInfo.get("partnersName").toString();
                String targetUserIconCd = partnersUserInfo.get("icon").toString();
                String message = "[ " + partnersUserInfo.get("partnersName").toString() + " ] 파트너님께 질문이 있어요!";

                param.put("targetUserId", targetUserId);
                param.put("targetUserNick", targetUserNick);
                param.put("targetUserIconCd", targetUserIconCd);
                param.put("message", message);

                if (targetUserId.equals(param.get("ssUserId"))) {
                    // 그럴일은 반드시 없어야 하지만 내 초이스에 내 광고가 뜨고 1:1문의를 하면? pk error SQLServeException 발생
                    param.put("sameId", "Y");
                    partnersUserInfo = exqueryService.selectOne("cronies.app.partners.partnersUserInfo", param);
                    if (!MapUtils.isEmpty(partnersUserInfo)) {
                        targetUserId = partnersUserInfo.get("userId").toString();
                        targetUserNick = partnersUserInfo.get("partnersName").toString();
                        targetUserIconCd = partnersUserInfo.get("icon").toString();
                        message = "[ " + partnersUserInfo.get("partnersName").toString() + " ] 파트너님께 질문이 있어요!";

                        param.put("targetUserId", targetUserId);
                        param.put("targetUserNick", targetUserNick);
                        param.put("targetUserIconCd", targetUserIconCd);
                        param.put("message", message);
                    } else {
                        resultMap.put("successYn", "N");
                        resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
                        return resultMap;
                    }
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
                return resultMap;
            }
        }

        try {
            //대화방 생성
            chatroomMap = exqueryService.selectOne("cronies.app.community.createCommunityChatroomNew", param);
            param.put("chatroomId", chatroomMap.get("chatroomId"));
            exqueryService.insert("cronies.app.community.createCommunityChatroomUser", param);
            exqueryService.insert("cronies.app.community.createCommunityChatroomTarget", param);

            HashMap<String, Object> returnMap = new HashMap<String, Object>();
            returnMap = exqueryService.selectOne("cronies.app.messageTalk.getDropOutYn", param);
            if(returnMap != null){
                //시간조회
                HashMap<String, Object> timeMap = exqueryService.selectOne("cronies.app.messageTalk.selectSysDateTime", param);
                HashMap<String, Object> timeMmMap = exqueryService.selectOne("cronies.app.messageTalk.selectSysDateTimeMm", param);
                HashMap<String, Object> seqMap = exqueryService.selectOne("cronies.app.messageTalk.selectMessageIdSeq", param);

                /**
                 message	    messageId		 regTime		 regUserId
                 */
                param.put("sysDateTime", timeMap.get("sysDateTime").toString());
                param.put("sysDateTimeMm", timeMmMap.get("sysDateTimeMm").toString());
                param.put("messageId", seqMap.get("messageId").toString());
                //메세지 마스터 저장
                exqueryService.insert("cronies.app.messageTalk.sendMessageMasterCommunityLoginSeq", param);

                //메시지 수신자별 대상자 조회
                List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
                userList = exqueryService.selectList("cronies.app.messageTalk.selectChatroomUserList", param);

                for (int i=0, len=userList.size(); i < len; i++){
                    HashMap<String, Object> userParamMap = new HashMap<String, Object>();
                    userParamMap.put("userId", userList.get(i).get("userId").toString());

                    String receptYn = "N";

                    //발송자는 자동읽음처리
                    if( userList.get(i).get("userId").toString().equals(param.get("ssUserId").toString()) ){
                        receptYn = "Y";
                    }

                    userParamMap.put("messageId", param.get("messageId"));
                    userParamMap.put("receptYn", receptYn);
                    userParamMap.put("chatroomId", param.get("chatroomId"));
                    userParamMap.put("userId", userList.get(i).get("userId").toString());


                    //대상자별 기록한다.
                    exqueryService.insert("cronies.app.messageTalk.sendMessageDetailSingleUserSeq", userParamMap);

                    //마지막 체팅 기록 업데이트
                    exqueryService.update("cronies.app.messageTalk.updateMessageDetailLastChat", param);

                }


                // 푸시 전송
                String alarmCd = "AL_C04_02";
                alarmCd = "AL_C04_02";
                pushMap.put("alarmCd", alarmCd);
                pushMap.put("userId", param.get("targetUserId").toString());
                pushMap.put("sendUserId", "");
                pushMap.put("subMessage", "");
                //pushMap.put("pushType", "communityChat");
                pushMap.put("chatroomId", chatroomMap.get("chatroomId").toString());

                HashMap<String, Object> chatroomTypeMap = exqueryService.selectOne("cronies.app.messageTalk.selectChatroomType", pushMap);
                //채팅방별 알람수신여부를 조회하고 처리한다
                if(chatroomTypeMap.get("chatroomType").equals("anonymous")){
                    //익명메시지 인지 확인한다.
                    pushMap.put("communityLastNick", "Y");
                    pushMap.put("pushType", "temp");
                } else {
                    pushMap.put("pushType", "communityChat");
                }

                pushService.sendPushMessage(pushMap);

                resultMap.put("chatroomKey", commonService.getEncoding(String.valueOf(param.get("chatroomId"))));
                resultMap.put("chatroomId", param.get("chatroomId"));
                resultMap.put("successYn", "Y");
                resultMap.put("message", "익명채팅 대화방으로 이동하시겠습니까?");

            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
            }

        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setAdminHideCommunity(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        param.put("articleId", commonService.getDecoding(String.valueOf(param.get("articleKey"))));
        if(exqueryService.update("cronies.app.community.setAdminHideCommunity", param) == 1){
            resultMap.put("successYn", "Y");
            resultMap.put("message", "처리되었습니다.");
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "에러발생");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getPopList(HashMap<String, Object> param) throws Exception {
        param.put("popListBeforeDay", -3);
        HashMap<String, Object> resultMap = exqueryService.selectPagingList("cronies.app.community.getCommunityList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) resultMap.get("result");
        for (int i = 0; dataList.size() > i; i++) {
            dataList.get(i).put("articleKey", commonService.getEncoding(String.valueOf(dataList.get(i).get("articleKey"))));
            if(dataList.get(i).get("photoKey") != null){
                dataList.get(i).put("photoKey", commonService.getEncoding(String.valueOf(dataList.get(i).get("photoKey"))));
            }
        }

        Iterator<HashMap<String, Object>> listIter = dataList.iterator();
        while(listIter.hasNext()) {
            HashMap<String, Object> item = listIter.next();

            int likeCnt = Integer.parseInt(String.valueOf(item.get("likeCnt")));
            int badCnt = Integer.parseInt(String.valueOf(item.get("badCnt")));
            int commentCnt = Integer.parseInt(String.valueOf(item.get("commentCnt")));

            int popPoint = ((likeCnt * 3) - (badCnt * 3)) + commentCnt;

            item.put("popPoint", popPoint);
        }

        Collections.sort(dataList, (v1, v2)-> {
            int a = Integer.parseInt(v1.get("popPoint").toString());
            int b = Integer.parseInt(v2.get("popPoint").toString());
            return b - a;
        });

        resultMap.put("result", dataList);

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getSelectUserIcon(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.community.getSelectUserIcon", param);

        return resultMap;
    }
}

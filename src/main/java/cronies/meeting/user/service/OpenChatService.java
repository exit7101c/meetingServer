package cronies.meeting.user.service;

import java.util.HashMap;
import java.util.List;

public interface OpenChatService {

    public List<HashMap<String, Object>> getPartitionList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getPartitionSubList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getMyOpenChatList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatDetail(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getAttendInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getChatRoomUserCountCheck(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateOpenChatAttendYn(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChatBookmark(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChatInvite(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChat(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChatDel(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChatUpdate(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setOpenChatReport(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatMyList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatInviteList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatMyBookmarkList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getDefaultImgList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setUserLatLon(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getOpenChatUserAddr(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> getOpenChatLatLon(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatDelYn(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> getPrivateYnCountCheck(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setCheckPrivateYn(HashMap<String, Object> param) throws Exception;

    // 소모임
    public int getOpenchatId(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getLeaderCheck(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getOpenChatMeetCategory(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatMeetList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> createOpenChatMeet(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateOpenChatMeet(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteOpenChatMeet(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getMeetUserList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getAlbumList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> addMeetUser(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteMeetUser(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatCommentList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> addMeetComment(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateMeetComment(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteMeetComment(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getOpenChatMeetEdit(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatAttendCheck(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatSendMessage(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateAboutMeMessage(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> openChatMeetInfo(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getOpenChatKey(HashMap<String, Object> param) throws Exception;

}
package cronies.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface SettingService {

    public List<HashMap<String, Object>> getUserOne(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateUser(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updatePw(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> uploadFile(HashMap<String, Object> param, MultipartFile file) throws Exception;
    public HashMap<String, Object> changeFile(HashMap<String, Object> param, MultipartFile file) throws Exception;

    public HashMap<String, Object> deleteProfilePhoto(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteUserPhoto(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> resetMainPhoto(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateMainPhoto(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateUserLocation(HashMap<String, Object> param) throws Exception;
}
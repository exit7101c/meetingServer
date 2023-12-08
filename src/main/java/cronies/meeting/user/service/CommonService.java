package cronies.meeting.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface CommonService {

    public String getEncoding(String str) throws Exception;
    public String getDecoding(String str) throws Exception;
    public String getNanoId(Integer num) throws Exception;
    public String getNumCode(Integer num) throws Exception;

    /**
     * by pjo 2023-04-03
     * 010-1234-5678
     * @param {addr:::}
     * @param param
     * @return {lat, lon}
     * @throws Exception
     */
    public HashMap<String, Object> Geocode(String param) throws Exception;

    public int insertWithFileOnFtp(String key, HashMap<String, Object> param, MultipartFile file);
}
package cronies.meeting.user.controller;

//import cronies.meeting.user.config.FTPConnect;
import cronies.meeting.user.service.CommonService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ExqueryService exqueryService;

    /**
     * 이미지 byte 를 읽어서 반환한다.
     * @param "fileId"
     * @return byteArray
     * @throws Exception
     */
    @RequestMapping(value = "/getImage/{fileId}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("fileId") String fileId) throws Exception {
        //System.out.println("imgKey : " + imgKey);
        //fileId로 변환
       // String fileId = commonService.getDecoding(imgKey);

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("fileId", fileId);
        HashMap<String,Object> image = exqueryService.selectOne("cronies.app.attachFile.selectImageFileByImageId", param);

        String fileName = (String)image.get("imageFileName");
        String fileDir = (String)image.get("imageFileDir");
        String fileHost = (String)image.get("serverIp");

       FTPClient fClient=new FTPClient();
       fClient.connect(fileHost, 21);
       fClient.enterLocalPassiveMode();
        boolean success = fClient.login("crony","crony!@34");

            if(success){

            }
       InputStream in = fClient.retrieveFileStream(fileDir+fileName);
       HttpHeaders headers = new HttpHeaders();
       MediaType contentType = getMimeType(fileName);
       headers.setContentType(contentType);

       if(contentType == MediaType.APPLICATION_OCTET_STREAM){
           headers.add("Content-Disposition","attachment; filename=\""+new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");
       }

        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers, HttpStatus.OK);

        return entity;
    }

    /**
     * 이미지 byte 를 읽어서 반환한다.
     * @param "fileId"
     * @return byteArray
     * @throws Exception
     */
    @RequestMapping(value = "/getImageThumb/{fileId}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<byte[]> getImageThumb(@PathVariable("fileId") String fileId) throws Exception {
        //System.out.println("imgKey : " + imgKey);
        //fileId로 변환
       // String fileId = commonService.getDecoding(imgKey);

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("fileId", fileId);
        HashMap<String,Object> image = exqueryService.selectOne("cronies.app.attachFile.selectImageFileByImageIdThumb", param);

        String fileName = (String)image.get("imageFileName");
        String fileDir = (String)image.get("imageFileDir");
        String fileHost = (String)image.get("serverIp");

       FTPClient fClient=new FTPClient();
       fClient.connect(fileHost, 21);
       fClient.enterLocalPassiveMode();
        boolean success = fClient.login("crony","crony!@34");

        if(success){

        }

       InputStream in = fClient.retrieveFileStream(fileDir+fileName);
       HttpHeaders headers = new HttpHeaders();
       MediaType contentType = getMimeType(fileName);
       headers.setContentType(contentType);

       if(contentType == MediaType.APPLICATION_OCTET_STREAM){
           headers.add("Content-Disposition","attachment; filename=\""+new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");
       }

        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers, HttpStatus.OK);

        //연결종료
        fClient.disconnect();

        return entity;
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "setImage", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setImage(HttpServletRequest request, @RequestParam HashMap<String, Object> param, @RequestParam(value="file", required=false) MultipartFile file) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();

        int saveId = commonService.insertWithFileOnFtp("file", param, file);

        //image id로 cdnNm, cdnThumbNm 추가하여 리턴한다.
        HashMap<String, Object> cdnMap = new HashMap<String, Object>();
        param.put("saveId", saveId);
        cdnMap = exqueryService.selectOne("cronies.app.attachFile.selectCdnInfo", param);

        res.put("fileKey", commonService.getEncoding(Integer.toString(saveId)));
        res.put("fileId", saveId);
        res.put("cdnNm", cdnMap.get("cdnNm").toString());
        res.put("cdnThumbNm", cdnMap.get("cdnThumbNm").toString());

        return res;
    }

    private MediaType getMimeType(String fileName){

        String ext = fileName.substring(fileName.lastIndexOf("."));

        Map<String, MediaType>map = new HashMap<String, MediaType>();

        map.put("jpg", MediaType.IMAGE_JPEG);
        map.put("png", MediaType.IMAGE_PNG);
        map.put("gif", MediaType.IMAGE_GIF);
        map.put("jpeg", MediaType.IMAGE_JPEG);

        MediaType result = map.get(ext);

        if(result == null){
            result = MediaType.APPLICATION_OCTET_STREAM;
        }
        return result;
    }

}

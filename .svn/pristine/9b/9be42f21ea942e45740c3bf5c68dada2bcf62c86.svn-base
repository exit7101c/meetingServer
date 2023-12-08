package cronies.meeting.user.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import cronies.meeting.user.config.NanoIdUtils;
import cronies.meeting.user.service.CommonService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import select.spring.exquery.dao.ExqueryDao;
import select.spring.exquery.service.ExqueryService;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Scanner;
//import org.json.JSONArray;
//import org.json.JSONObject;
import select.spring.util.ApplicationProperty;
import select.spring.util.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.metadata.*;
import java.nio.file.DirectoryStream;

@Service("CommonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommonServiceImpl implements CommonService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

    @Inject
    @Named("exqueryDao")
    private ExqueryDao exqueryDao;

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    HttpSession session;

    private String algorithm = "AES";
    //private String configScretKey = ApplicationProperty.get("cmmn.encryption.secretKeyAES128S");
    private String configScretKey = "p@#*dTeeMo!j.8as";
    //private String configInitVector = ApplicationProperty.get("cmmn.encryption.initVectorAES128S");
    private String configInitVector = "ihsa76d56adn81qo";


    private String uploadPath = ApplicationProperty.get("upload.path");

    private static final String  NEW_INIT = "newInit";
    private static final String  NEW_MODIFIED = "newModified";
    private static final String  NOT_MODIFIED = "notModified";
    private static final String  DATA_MODIFIED = "dataModified";
    private static final String  DATA_DELETED = "dataDeleted";

    /**
     * @param input 암호화할 Input
     * Default AES 128
     * */
    private String encrypt(String input) {

        return encrypt(configScretKey, configInitVector, input);

    }

    /**
     * @param secretKey secretKey 값
     * @param initVector initVector 값
     * @param input 암호화할 string
     * */
    private String encrypt(String secretKey, String initVector, String input) {
        try {
            // Key 생성
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), this.algorithm);

            // 암호화
            Cipher cipher = Cipher.getInstance(this.algorithm + "/CBC/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(input.getBytes());

            // 결과
            return Base64.encodeBase64String(encrypted);
        }  catch (NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * @param secretKey secretKey 값
     * @param initVector initVector 값
     * @param encrypted 암호화된 string
     * */
    public String decrypt(String secretKey, String initVector, String encrypted) {
        try {
            // Key 생성
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), this.algorithm);

            // 암호화된 String 복구
            Cipher cipher = Cipher.getInstance(this.algorithm + "/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));


            // 결과
            return new String(original);
        } catch (NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getEncoding(String str) throws Exception {

        String encodingStr = "";
        try {
            encodingStr = encrypt(str);
        } catch (Exception e){
            encodingStr = "error";
        }

        encodingStr = (encodingStr == null || encodingStr.equals("")) ? "emtpy" : encodingStr;

        return encodingStr;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getDecoding(String str) throws Exception {

        String decodingStr = "";
        try {

            decodingStr = decrypt(configScretKey, configInitVector, str);
        } catch (Exception e){
            decodingStr = "error";
        }

        decodingStr = (decodingStr == null || decodingStr.equals("")) ? "emtpy" : decodingStr;

        return decodingStr;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> Geocode(String param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+param.replaceAll(" ", "")+"&components=country:KR&key=AIzaSyDiNSGBUfZLnHpAl0EFDRlksEDodFVKEXc";

        String jsonString = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        JSONObject json = new JSONObject(jsonString);

        JSONArray results = json.getJSONArray("results");
        JSONObject result = results.getJSONObject(0);
        JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
        double lat = location.getDouble("lat");
        double lon = location.getDouble("lng");

        resultMap.put("lat", lat);
        resultMap.put("lon", lon);

        return resultMap;
    }

    public static byte[] getContentsAndDeleteFile(File file) throws IOException {
        byte[] data = Files.readAllBytes(file.toPath());
        file.delete();
        return data;
    }


    @SuppressWarnings("unchecked")
    public int insertWithFileOnFtp(String key, HashMap<String, Object> param, MultipartFile file) {

        HashMap<String, Object> pMap = new HashMap<String, Object>();

        // 파일정보 설정 & 실제 저장될 파일명
        int saveId = 0;

        //pMap = (HashMap<String, Object>)param.get("data");
        String fileState = "newModified";
        // 신규입력 상태에서는 파일상태가 아래의 상태들만 가능함
        if (fileState.equals(NEW_INIT)) {
            // 처리할 내용 없음
            saveId = 0;

        } else if (fileState.equals(NEW_MODIFIED)) {
            // new 신규입력후 id 구함
            // 파일 저장
            try {


                String[] fileExt = {"PNG","JPG","JPEG","BMP", "GIF"};
                long maxSize = 45440000;

                String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
                long fileSize = file.getSize();

                boolean isImage = false;
                for (int i = 0; i < fileExt.length; i++) {
                    if(fileType.toUpperCase().equals(fileExt[i])){
                        isImage = true;
                    }
                }

                //위에 이미지 확장자 중 하나가 아니라면 0을 리턴한다.
                if(!isImage){
                    return 0;
                }
                //용량이 제한치보다 크다면 저장하지 않는다.
                if(fileSize > maxSize){
                    return 0;
                }


                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyyMM");
                String today = formatter.format(new java.util.Date());
                String tempPath = uploadPath + dataFormatter.format(new java.util.Date()) + "\\";

                File Folder = new File(uploadPath);
                if(!Folder.exists()){
                    try {
                        Folder.mkdir();
                    } catch (Exception e){

                    }
                }

                String filePath = dataFormatter.format(new java.util.Date()) + "/";
                String realFileNm = "";
                String thumbFileNm = today + "_" + UUID.randomUUID().toString()  + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                String orgFileNm = FileUtils.saveUniqeName(file.getOriginalFilename(), tempPath, file.getBytes());
                String thumbFile = FileUtils.saveUniqeName(thumbFileNm, tempPath, file.getBytes());

                File convFile = new File(tempPath + orgFileNm);
                File convFile2 = new File(tempPath + thumbFile);
                file.transferTo(convFile);
                //file.transferTo(convFile2);
                //썸네일 이미지 저장(원본 이미지 읽어들임)

                BufferedImage buffImage = ImageIO.read(convFile);

                int orientation = 1;
                Metadata metadata;
                Directory directory;
                JpegDirectory jpegDirectory;

                if(!fileType.toUpperCase().equals("MP4")){


                    metadata = ImageMetadataReader.readMetadata(convFile);
                    directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                    jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
                    if(directory != null){
                        if(directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){
                            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                        } else {
                            orientation = 1;
                        }
                    }
                    // 회전 시킨다.
                    switch (orientation) {
                        case 6:
                            buffImage = Scalr.rotate(buffImage, Scalr.Rotation.CW_90, null);
                            break;
                        case 1:
                            break;
                        case 3:
                            buffImage = Scalr.rotate(buffImage, Scalr.Rotation.CW_180, null);
                            break;
                        case 8:
                            buffImage = Scalr.rotate(buffImage, Scalr.Rotation.CW_270, null);
                            break;
                        default:
                            orientation=1;
                            break;
                    }

                    Integer imageWidth = buffImage.getWidth();
                    Integer targetWidth = 600; //본문 target widhth
                    Integer targetWidth2 = 200; //썸네일 widhth
                    Integer targetHeight = 800;
                    Integer targetHeight2 = 250;

                    //가로사진, 세로사진에 따라 대상 계산식이 달라짐
                    if(buffImage.getWidth() >= buffImage.getHeight()){
                        //가로사진
                        if(imageWidth <= targetWidth){
                            //크기를 조정하지 않음.
                            targetWidth = imageWidth;
                            targetHeight = buffImage.getHeight();
                            targetWidth2 = imageWidth;
                            targetHeight2 = buffImage.getHeight();
                        } else {
                            //높이값을 기존 높이값의 비율로 조정
                            targetHeight = targetWidth * buffImage.getHeight() / buffImage.getWidth();
                            targetHeight2 = targetWidth2 * buffImage.getHeight() / buffImage.getWidth();
                        }
                    } else {
                        //세로사진
                        if(buffImage.getHeight() <= targetHeight){
                            //크기를 조정하지 않음.
                            targetWidth = buffImage.getWidth();
                            targetHeight = buffImage.getHeight();
                            targetWidth2 = buffImage.getWidth();
                            targetHeight2 = buffImage.getHeight();
                        } else {
                            //높이값을 기존 높이값의 비율로 조정
                            targetWidth = targetHeight * buffImage.getWidth() / buffImage.getHeight();
                            targetWidth2 = targetHeight2 * buffImage.getWidth() / buffImage.getHeight();
                        }
                    }



                    if(!fileType.toUpperCase().equals("GIF")){
                    ////////////////////// 썸네일 시작 /////////////////////////
                        BufferedImage destImg = Scalr.resize(buffImage, targetWidth, targetHeight);
                        BufferedImage destImg2 = Scalr.resize(buffImage, targetWidth2, targetHeight2);
                        ImageIO.write(destImg, "png", convFile);
                        ImageIO.write(destImg2, "png", convFile2);
                    ////////////////////// 썸네일 끝 /////////////////////////
                        destImg.flush();
                        destImg2.flush();
                    } else {
                        ImageIO.write(buffImage, "png", convFile2);
                    }

                }
                ////////////////////// FTP upload 수정부분 /////////////////////////

                //String fileHost = "114.207.244.39"; // 개발서버
                /*카페24*/
                /*String fileHost = "cmdgimg.cafe24.com"; // 운영(테스트cdn)
                String ftpUser = "cmdgimg";
                String ftpPassword = "crony!@34";*/
                /*스피디*/
                String domain = "cmdg.speedycdn.net"; //호출할 경로
                String fileHost = "fileupload.cdn.cloudn.co.kr"; //ftp 주소
                String ftpUser = "gabia_cmdg";
                String ftpPassword = "gmc!489@";

                FTPClient fClient = new FTPClient();
                int reply = 0;

                try {


                    fClient.connect(fileHost, 21);
                    //로그인
                    fClient.login(ftpUser,ftpPassword);
                    //showServerReply(fClient);

                    reply = fClient.getReplyCode();

                    fClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

                    if(!FTPReply.isPositiveCompletion(reply)){ //서버 응답코드 체크
                        fClient.disconnect();
                    }else{ // 정상일 경우 진행
                        //showServerReply(fClient);

                        //디렉토리 변경 및 생성
                        if(!fClient.changeWorkingDirectory(filePath)){
                            fClient.makeDirectory(filePath);
                            fClient.changeWorkingDirectory(filePath);
                        }
                        //showServerReply(fClient);

                        // Active Mode -> Passive Mode
                        fClient.enterLocalPassiveMode();
                        //showServerReply(fClient);

                        FileInputStream fis = null;
                        FileInputStream fis2 = null;
                        fis = new FileInputStream(convFile);
                        fis2 = new FileInputStream(convFile2);

                        fClient.setFileType(FTP.BINARY_FILE_TYPE);

                        // ftp에 파일 업로드
                        boolean isSuccess = fClient.storeFile(orgFileNm, fis);
                        boolean isSuccess2 = fClient.storeFile(thumbFileNm, fis2);

                        // 업로드 성공 시
                        //if(isSuccess && isSuccess2){
                        //System.out.println("업로드 성공");
                        //연결종료
                        fis.close();
                        fis2.close();
                        fClient.logout();
                        fClient.disconnect();
                        //}
                    }

                } catch(Exception e){
                    //do nothing
                } finally {
                    if(fClient.isConnected()){
                        try {
                            fClient.disconnect();
                        } catch(Exception e){
                            //do nothing
                        }
                    }
                }


                if(convFile.exists()){
                    convFile.delete();
                }
                if(convFile2.exists()){
                    convFile2.delete();
                }

                buffImage.flush();


                ////////////////////// FTP upload 수정부분 /////////////////////////

                pMap.put("fileType", fileType);
                pMap.put("orgFileName", file.getOriginalFilename());
                pMap.put("saveFileName", orgFileNm);
                pMap.put("thumbnail", thumbFileNm);
                pMap.put("savePath", filePath);
                pMap.put("fileSize", fileSize);
                pMap.put("userId", "jeh");
                pMap.put("serverIp", domain);

                pMap.put("width", "0");
                pMap.put("height", "0");

                HashMap<String, Object> seqMap =exqueryDao.selectOne("cronies.app.attachFile.selectAttachFileIdSeq", pMap);
                pMap.put("attachFileId", seqMap.get("attachFileId"));
                exqueryDao.insert("cronies.app.attachFile.insert", pMap);
                saveId = ((Integer)pMap.get("attachFileId")).intValue();

                //file.cre
            } catch (IOException | MetadataException | ImageProcessingException e) {
                e.printStackTrace();
                // TODO:: 저장오류 발생시 처리 보완
            } finally {

            }
        }

        // 나머지 데이터 처리 로직

        return saveId;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getNanoId(Integer num) throws Exception {
        Random random = new Random();
        char[] alphabet = {'0','1','2','3','4','5','6','7','8','9'
                ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int size = num;
        String nanoIdStr = NanoIdUtils.randomNanoId(random, alphabet, size);
        return nanoIdStr;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getNumCode(Integer num) throws Exception {
        Random random = new Random();
        char[] numCode = {'0','1','2','3','4','5','6','7','8','9'};
        int size = num;
        String numCodeStr = NanoIdUtils.randomNanoId(random, numCode, size);
        return numCodeStr;
    }


    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }


    private static void showServerReply (FTPClient fClient){
        String[] replies = fClient.getReplyStrings();
        if(replies!= null && replies.length > 0){
            for(String aReply : replies){
                //System.out.println("SERVER : " + aReply);
            }
        }
    }
}

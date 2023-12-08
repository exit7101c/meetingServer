package com.nse.config;

import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author Jay Lee
 * PMS Application 에서 사용하는 공통 값들
 * */
public class Common {

    // new line escape 간격
    // private static final int ERROR_MESSAGE_ESCAPE_INTERVAL = 5;


    // Application Context 에 관한 Key 값들
    public interface ContextKeys {
        String LANGUAGE = "language";               // 시스템 언어
    }

    // 사용자 API 정의
    public interface ResponseApi {
        String DATA = "rtnData";                        // Query 또는 HTTP Request 로 받은 데이터
        String HAS_ERROR = "rtnErrFlag";                // 오류 발생 error flag
        String MESSAGE = "rtnMsg";                      // 오류 / 성공 메시지
        String ROWS_AFFECTED = "rowsAffected";          // 업데이트 된 row 수
        String ROW_COUNT_MESSAGE = "rowCountMessage";   // Row Count message

        // Pagination
        String PAG_START_INDEX = "pagStartIndex";       // Offset index
        String PAG_COUNT = "pagCount";                  // data batch 에 있는 row 건수

        // Rest API 메뉴 key
        String REST_API_URL = "restApiUrl";
    }

    /**
     * Get user-defined app properties
     */
    public static ResourceBundle getApplicationProperties() {
        try {
            return ResourceBundle.getBundle("app");
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public static String getI18DirectoryPath() {
        // return new ApplicationHome(Common.class).getDir().getAbsolutePath();
        return System.getProperty("user.dir") + "/i18n/";
    }

    /**
     * 시스템의 working directory 가져오기
     * */
    public static String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * 파일 업로드 경로 가져오기
     * */
    public static String getFileUploadPath() {
        String path = Objects.requireNonNull(getApplicationProperties()).getString("cmmn.upload.directory");
        if (Paths.get(path).isAbsolute()) {
            return path;
        }
        return getCurrentWorkingDirectory() + path;
    }

    /**
     * HTTP / HTTPS 체크
     * */
    public static boolean isHttps(HttpServletRequest request) {
        return request.isSecure();
    }

    /**
     * i18n
     * =======================
     * Spring Context 에서 messageSource 가져오기
     * 참고: 2018년 10월 04일 기준으로 다국어 Message 는
     * message.properties 에서 안 가져오고,
     * database 에 저장된다.
     * Message.properties 에서 가져오는 부분은 임시 주석처리
     * */
    public static ResourceBundle getMessagesBundle() {
        return ResourceBundle.getBundle("locale/messages", LocaleContextHolder.getLocale(), new UTF8Control());
    }

    /**
     * Resource bundle 가져와서 다국어 message key 값으로 가져오기
     * */
    public static String getMessage(String key) {
        return getMessagesBundle().getString(key);
    }

    /**
     * 메시지에 Parameter 추가
     * */
    public static String getMessage(String key, Object...params) {
        return MessageFormat.format(getMessage(key), params);
    }

    /**
     * 현재 Bundle 에서 Message 가져오기
     * */
    public static String getMessage(ResourceBundle bundle, String key) {
        return bundle.getString(key);
    }

    public static String getMessage(ResourceBundle bundle, String key, Object...params) {
        return MessageFormat.format(getMessage(bundle, key), params);
    }

    /**
     * messageSource 사용해서 Message 가져오기
     * */
    public static String getMessage(MessageSource messageSource, String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    public static String getMessage(MessageSource messageSource, String key, Locale locale, Object...params) {
        return messageSource.getMessage(key, params, locale);
    }

    /**
     * 언어 수정값 적용
     * */
    public static boolean saveMessage(Map<String, String> messages) {

        Properties prop = new Properties();
        return true;
    }

    /**
     * 공통 API 에 error message set 함수
     * */
    public static void setErrorMsg(Map<String, Object> responseApi, String key, Object...params) {
        responseApi.put(ResponseApi.HAS_ERROR, true);
        responseApi.put(ResponseApi.MESSAGE, getMessage(key, params));
    }

    public static void setErrorMsg(Map<String, Object> responseApi, ResourceBundle bundle, String key, Object...params) {
        responseApi.put(ResponseApi.HAS_ERROR, true);
        responseApi.put(ResponseApi.MESSAGE, getMessage(bundle, key, params));
    }

    /**
     * Exception message 전송
     * */
    public static void setExceptionMsg(Map<String, Object> responseApi, String exceptionMsg) {
        responseApi.put(ResponseApi.HAS_ERROR, true);
        responseApi.put(ResponseApi.MESSAGE, getMessage("config.exception", exceptionMsg));
    }

    /**
     * Exception message 전송
     * */
    public static void setExceptionMsg(HttpServletResponse response, Map<String, Object> responseApi, String exceptionMsg) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseApi.put(ResponseApi.HAS_ERROR, true);
        responseApi.put(ResponseApi.MESSAGE, getMessage("config.exception", exceptionMsg));
    }

    /**
     * 성고 메시지 설정
     * */
    public static void setSuccessMessage(Map<String, Object> responseApi, String messageKey) {
        responseApi.put(ResponseApi.HAS_ERROR, false);
        responseApi.put(ResponseApi.MESSAGE, getMessage(messageKey));
    }

    /**
     * HttpStatus 400 으로 설정하고 message 값 추가
     * */
    public static void setBadRequestErrorMsg(HttpServletResponse response,
                                             Map<String, Object> responseApi,
                                             String key, Object...params) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        setErrorMsg(responseApi, key, params);
    }

    public static void setBadRequestErrorMsg(Logger log, HttpServletResponse response,
                                             Map<String, Object> responseApi,
                                             String key, Object...params) {
        setBadRequestErrorMsg(response, responseApi, key, params);
        if (log.isDebugEnabled()) {
            log.debug(responseApi.get(ResponseApi.MESSAGE).toString());
        }
    }

    /**
     * HttpStatus 500 으로 설정하고 message 값 추가
     * */
    public static void setInternalServerErrorMsg(HttpServletResponse response,
                                                 Map<String, Object> responseApi,
                                                 String key, Object...params) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        setErrorMsg(responseApi, key, params);
    }

    public static void setInternalServerErrorMsg(Logger log, HttpServletResponse response,
                                                 Map<String, Object> responseApi,
                                                 String key, Object...params) {
        setInternalServerErrorMsg(response, responseApi, key, params);
        log.error(responseApi.get(ResponseApi.MESSAGE).toString());
    }

    /**
     * 에러 체크
     * */
    public static boolean hasError(Map<String, Object> result) {
        return (boolean) result.get(ResponseApi.HAS_ERROR);
    }

    public static boolean isValid(Map<String, Object> result) {
        return !hasError(result);
    }

    /**
     * 결과 값 존재성 체크
     * */
    public static boolean hasResults(Map<String, Object> result) {
        boolean hasResult = result.containsKey(ResponseApi.DATA) &&
                result.get(ResponseApi.DATA) != null;
        if (hasResult) {
            Object data = result.get(ResponseApi.DATA);
            if (data instanceof List) {
                return ((List) data).size() > 0;
            }
            if (data instanceof Map) {
                Map rtnData = (Map) data;
                if (rtnData.containsKey(ResponseApi.ROWS_AFFECTED)) {
                    return (int) rtnData.get(ResponseApi.ROWS_AFFECTED) > 0;
                }
                return rtnData.size() > 0;
            }
        }
        return false;
    }

    /**
     * Parameter 로 찍는 String 생성
     * @param params
     * */
    public static String generateParamErrorLog(Map<String, Object> params) {
        String paramKeyMsg = "Param Key: ";
        String valueMsg = ". Value: ";
        String cage = "\n-----------------------------------------------\n";
        StringBuilder sb = new StringBuilder(cage);
        for (String key : params.keySet()) {
            sb.append("|  ").append(paramKeyMsg).append(key).append(valueMsg)
                    .append(StringUtils.isEmpty(params.get(key)) ? "''" : params.get(key))
                    .append('\n');
        }
        sb.append(cage);
        return sb.toString();
    }

    /**
     * IP 주소를 HttpServletRequest 객체에서 가져오기.
     * */
    public static InetAddress getInetAddress(final HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            return ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1") ?
                    InetAddress.getLocalHost() :
                    InetAddress.getByName(ip);
        } catch (UnknownHostException e) { }
        return null;
    }

    /**
     * 서버 url 가져오기 예: http://127.0.0.1:8080
     * */
    public static String getServerUrl(HttpServletRequest request) {
        String requestProtocol;
        if (isHttps(request)) {
            requestProtocol = "https://";
        } else {
            requestProtocol = "http://";
        }
        return requestProtocol + Objects.requireNonNull(getInetAddress(request)).getHostAddress() + ":" +
                request.getServerPort();
    }

    /**
     * 서버의 Local URL 가져오기
     *  localhost 또는 IP 주소 입력하면 느리니까
     * */
    public static String getLocalUrl(HttpServletRequest request) {
        String requestProtocol;
        if (isHttps(request)) {
            requestProtocol = "https://";
        } else {
            requestProtocol = "http://";
        }
        return requestProtocol + "127.0.0.1:" +
                request.getServerPort();
    }

    public static String getCurrentUrl(HttpServletRequest request) {
        return getServerUrl(request) + request.getRequestURI();
    }


    /**
     * Private methods
     * =======================
     * */

    /**
     * NOTE: 내부 2018년 09월 19일 오후 2:00 - 4:35
     * 회의하면서 나온 요구사항. 나중에 필요하면 적용.
     * @param input
     * @param escapeInterval new line escape 하는 간격.
     *                       예: 40 이면, 각 라인에 최대 40 char 있음
     * */
     /*private static String lineEscapeMessage(String input, int escapeInterval) {
        int initialCount = escapeInterval;
        StringBuilder sb = new StringBuilder(input);
        while (escapeInterval < sb.length()) {
            sb.insert(escapeInterval, '\n');
            escapeInterval += initialCount;
        }
        return sb.toString();
     }*/
}
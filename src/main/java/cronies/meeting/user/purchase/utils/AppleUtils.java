package cronies.meeting.user.purchase.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class AppleUtils {

    private Logger logger = LoggerFactory.getLogger(AppleUtils.class);

    private final String APPLE_PRODUCTION_URL = "https://buy.itunes.apple.com/verifyReceipt";

    private final String APPLE_SANDBOX_URL = "https://sandbox.itunes.apple.com/verifyReceipt";

    private final String PASSWORD = "fdc0a7fb8960479084a9b3c200403a6d";

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * App에서 전달받은 receipt-data를 Apple에 유효성 확인 요청
     * Apple Document URL ‣ https://developer.apple.com/documentation/appstorereceipts/verifyreceipt
     *
     * @throws JsonProcessingException
     */
    public HashMap<String, Object> verifyReceipt(HashMap<String, Object> param) throws JsonProcessingException, IllegalStateException {
        Map<String, String> appStoreRequest = new HashMap<>();
        appStoreRequest.put("receipt-data", param.get("receiptData").toString());
        appStoreRequest.put("password", PASSWORD);

        String response = HttpClientUtils.doPost(APPLE_PRODUCTION_URL, appStoreRequest);
        HashMap<String, Object> appStoreResponse = objectMapper.readValue(response, new TypeReference<HashMap<String, Object>>(){});

        int statusCode = Integer.parseInt(appStoreResponse.get("status").toString());
        if (statusCode == 21007) {
            response = HttpClientUtils.doPost(APPLE_SANDBOX_URL, appStoreRequest);
            appStoreResponse = objectMapper.readValue(response, new TypeReference<HashMap<String, Object>>(){});
        } else if (statusCode != 0) {
            verifyStatusCode(statusCode);
        }

        return appStoreResponse;
    }

    private void verifyStatusCode(int statusCode) {
        switch (statusCode) {
            case 21000:
                logger.error("[Status code: " + statusCode + "] The request to the App Store was not made using the HTTP POST request method.");
                break;
            case 21001:
                logger.error("[Status code: " + statusCode + "] This status code is no longer sent by the App Store.");
                break;
            case 21002:
                logger.error("[Status code: " + statusCode + "] The data in the receipt-data property was malformed or the service experienced a temporary issue. Try again.");
                break;
            case 21003:
                logger.error("[Status code: " + statusCode + "] The receipt could not be authenticated.");
                break;
            case 21004:
                logger.error("[Status code: " + statusCode + "] The shared secret you provided does not match the shared secret on file for your account.");
                break;
            case 21005:
                logger.error("[Status code: " + statusCode + "] The receipt server was temporarily unable to provide the receipt. Try again.");
                break;
            case 21006:
                logger.error("[Status code: " + statusCode + "] This receipt is valid but the subscription has expired. When this status code is returned to your server, the receipt data is also decoded and returned as part of the response. Only returned for iOS 6-style transaction receipts for auto-renewable subscriptions.");
                break;
            case 21008:
                logger.error("[Status code: " + statusCode + "] This receipt is from the production environment, but it was sent to the test environment for verification.");
                break;
            case 21009:
                logger.error("[Status code: " + statusCode + "] Internal data access error. Try again later.");
                break;
            case 21010:
                logger.error("[Status code: " + statusCode + "] The user account cannot be found or has been deleted.");
                break;
            default:
                logger.error("[Status code: " + statusCode + "] The receipt for the App Store is incorrect.");
                break;
        }

        throw new IllegalStateException("[/verifyReceipt] The receipt for the App Store is incorrect.");
    }

}

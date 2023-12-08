package cronies.meeting.user.purchase.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class IapTicData {
    public IapTicData() {}

    private static final String APP_NAME = "cmdg.navy.client";
    private static final String APP_SECRET_KEY = "c83df663-2e86-4db5-98f7-5e0657d7f537";
    private static final String APPLICATION_USER_NAME = "demo-user";
    private static final String VALIDATE_BASE_URL = "https://validator.iaptic.com/v3/";

    public String getAppName(){
        return this.APP_NAME;
    }
    public String getAppSecretKey(){
        return this.APP_SECRET_KEY;
    }
    public String getApplicationUsername(){
        return this.APPLICATION_USER_NAME;
    }
    public String getValidateBaseUrl() {
        return this.VALIDATE_BASE_URL;
    }

    public HashMap<String, Object> getCustomers() throws Exception {
        String getCustomersURL = getValidateBaseUrl() + "customers";
        String _authorization = getAppName() + ":" + getAppSecretKey();
        String authorization = "Basic " + Base64.getEncoder().encodeToString(_authorization.getBytes());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getCustomersURL)
                .get()
                .addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, authorization)
                .addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> responseJSON = mapper.readValue(response.body().string(), new TypeReference<HashMap<String, Object>>() {});

        return responseJSON;
    }

    public List<HashMap<String, Object>> getTransactions() throws Exception {
        String getCustomersURL = getValidateBaseUrl() + "customers/" + getApplicationUsername() + "/transactions";
        String _authorization = getAppName() + ":" + getAppSecretKey();
        String authorization = "Basic " + Base64.getEncoder().encodeToString(_authorization.getBytes());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getCustomersURL)
                .get()
                .addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, authorization)
                .addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> responseJSON = mapper.readValue(response.body().string(), new TypeReference<HashMap<String, Object>>() {});

        List<HashMap<String, Object>> responseArrayList = (ArrayList<HashMap<String, Object>>) responseJSON.get("transactions");

        return responseArrayList;
    }
}

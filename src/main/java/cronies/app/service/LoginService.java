package cronies.app.service;

import java.util.HashMap;
import java.util.List;

public interface LoginService {

    public List<HashMap<String, Object>> userLogin(HashMap<String, Object> param) throws Exception;

}

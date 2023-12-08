package select.spring.exquery.vo;

public class ExResult {
	
	public String code = "0";		// 0: 정상,  -1: 오류
	public String message = "";	// 처리 메시지
	public Object data;			// 결과 데이터

	public ExResult(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ExResult(String code, String message) {
		this(code, message, null);
	}

	public ExResult(Object data) {
		this("0", "OK", data);
	}
	
}

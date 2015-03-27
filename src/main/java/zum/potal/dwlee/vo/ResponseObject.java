package zum.potal.dwlee.vo;

public class ResponseObject {

	private boolean result=false;
	private String message;
	
	public ResponseObject() {
	}
	
	public ResponseObject(boolean result) {
		this.result = result;
	}

	public ResponseObject(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}

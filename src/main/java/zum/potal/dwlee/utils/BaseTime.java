package zum.potal.dwlee.utils;

import java.sql.Timestamp;

public class BaseTime {

	private Timestamp time;
	
	public BaseTime() {
		this.time = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getNowTime() {
		return time;
	}
}

package zum.potal.dwlee.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class User {
	
	@Id
	private int no;
	
	@NotNull
	@Size(min=1, max=100, message="1자에서 100자사이의 값만 가능합니다.")
	@Pattern(regexp="^[a-zA-Z0-9]*$")
	private String id;
	
	@NotNull
	private String password;
	
	@NotNull
	@Pattern(regexp="^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$")
	private String email;
	
	private char status='Y';
	
	public User() {
	}
	
	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}



	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
}

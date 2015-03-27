package zum.potal.dwlee.dao;

import zum.potal.dwlee.vo.User;

public interface UserDao {

	//사용자 조회(id)
	public User getUser(String id);
	
	//사용자 조회(id & password)
	public User getUser(User user);
	
	//사용자 등록
	public void add(User user);
	
	//회원정보수정
	public void update(User user);
	
	//회원 탈퇴
	public void delete(User user);
}

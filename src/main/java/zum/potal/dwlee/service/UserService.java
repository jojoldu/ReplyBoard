package zum.potal.dwlee.service;

import zum.potal.dwlee.vo.User;

public interface UserService {

	//ID중복 확인
	public boolean checkDuplicateId(String id);
	
	//사용자정보 조회
	public User getUser(User user);
	
	//회원가입
	public boolean add(User insert);

	//비밀번호 확인
	public boolean checkPassword(User user);

	//회원정보 수정
	public boolean update(User user);
	
	//회원 탈퇴
	public void delete(User user);
}

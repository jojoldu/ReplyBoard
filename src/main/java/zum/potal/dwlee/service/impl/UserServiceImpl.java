package zum.potal.dwlee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public boolean checkDuplicateId(String id)  {
		return userDao.getUser(id) == null;
	}
	
	@Override
	public User getUser(User user) {
		return userDao.getUser(user);
	}

	@Override
	public boolean add(	User user)  {

		userDao.add(user);
		return true;
	}

	@Override
	public boolean checkPassword(User user){
	
		return userDao.getUser(user) != null;
	}

	@Override
	public boolean update(User user) {
		
		User update = userDao.getUser(user);
		
		if(update == null){
			return false;
		}
		
		update.setPassword(user.getPassword());
		update.setEmail(user.getEmail());
		
		return true;
	}

	@Override
	public void delete(User user) {
		user.setStatus('N');
		userDao.update(user);
	}
}
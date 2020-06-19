package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.UserDao;
import cn.fy.cjgl.entity.User;
import cn.fy.cjgl.service.IUserService;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	public int addUser(User user) {
		int nResult = 0;
		if((nResult = this.userDao.checkUser(user)) == 0) {
			this.userDao.addUser(user);
		} 
		return nResult;
	}
	
	public int modUser(User user) {
		int nResult = 0;
		if((nResult = this.userDao.checkUser(user)) == 0) {
			this.userDao.modUser(user);
		}
		return nResult;
	}
	
	public void delUser(User user) {
		this.userDao.delUser(user);
	}
	
	public User queryUser(User user){
		return this.queryUser(user);
	}
	
	public List<User> queryUserList(User user){
		return this.userDao.queryUserList(user);
	}
	
	public Integer checkUser(User user) {
		return this.userDao.checkUser(user);
	}
	
	public User getUserByLoginname(User user) {
		return this.userDao.getUserByLoginname(user);
	}
	
}

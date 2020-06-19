package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.User;

public interface IUserService {
	public int addUser(User user);
	public int modUser(User user);
	public void delUser(User user);
	public User queryUser(User user);
	public List<User> queryUserList(User user);
	public Integer checkUser(User user);
	public User getUserByLoginname(User user);
}

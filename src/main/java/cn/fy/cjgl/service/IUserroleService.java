package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Userrole;

public interface IUserroleService {
	public void addUserrole(Userrole userrole);
	public void modUserrole(Userrole userrole);
	public void delUserrole(Userrole userrole);
	public void delUserroleByUserid(Userrole userrole);
	public Userrole queryUserrole(Userrole userrole);
	public List<Userrole> queryUserroleList(Userrole userrole);
	public void saveUserrole(Userrole userrole, String roleids);
	public List<Userrole> queryUserroleListByUserid(Userrole userrole);
}

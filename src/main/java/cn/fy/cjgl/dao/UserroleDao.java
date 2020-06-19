package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.Userrole;

@Mapper
public interface UserroleDao {
	public void addUserrole(Userrole userrole);
	public void modUserrole(Userrole userrole);
	public void delUserrole(Userrole userrole);
	public void delUserroleByUserid(Userrole userrole);
	public Userrole queryUserrole(Userrole userrole);
	public List<Userrole> queryUserroleList(Userrole userrole);
	public List<Userrole> queryUserroleListByUserid(Userrole userrole);
}

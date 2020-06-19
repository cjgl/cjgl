package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.UserroleDao;
import cn.fy.cjgl.entity.Userrole;
import cn.fy.cjgl.service.IUserroleService;

@Service
@Transactional
public class UserroleService implements IUserroleService {
	
	@Resource(name = "userroleDao")
	private UserroleDao userroleDao;
	
	public void addUserrole(Userrole userrole) {
		this.userroleDao.addUserrole(userrole);
	}
	
	public void modUserrole(Userrole userrole) {
		this.userroleDao.modUserrole(userrole);
	}
	
	public void delUserrole(Userrole userrole) {
		this.userroleDao.delUserrole(userrole);
	}
	
	public void delUserroleByUserid(Userrole userrole) {
		this.userroleDao.delUserroleByUserid(userrole);
	}
	
	public Userrole queryUserrole(Userrole userrole) {
		return this.userroleDao.queryUserrole(userrole);
	}
	
	public List<Userrole> queryUserroleList(Userrole userrole){
		return this.userroleDao.queryUserroleList(userrole);
	}
	
	public void saveUserrole(Userrole userrole, String roleids) {
		this.userroleDao.delUserroleByUserid(userrole);
		if(roleids != null && !"".equals(roleids)) {
			String[] roleidArray = roleids.split(",");
			Userrole tempUserrole = new Userrole();
			tempUserrole.setUserid(userrole.getUserid());
			for(String roleid : roleidArray) {
				tempUserrole.setRoleid(Integer.parseInt(roleid));
				this.userroleDao.addUserrole(tempUserrole);
			}
		}
	}

	@Override
	public List<Userrole> queryUserroleListByUserid(Userrole userrole) {
		return this.userroleDao.queryUserroleListByUserid(userrole);
	}
}

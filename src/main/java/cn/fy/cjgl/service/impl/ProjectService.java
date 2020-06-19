package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.ProjectDao;
import cn.fy.cjgl.entity.Project;
import cn.fy.cjgl.service.IProjectService;

@Service
@Transactional
public class ProjectService implements IProjectService {
	
	@Resource(name = "projectDao")
	private ProjectDao projectDao;
	
	@Override
	public int addProject(Project project) {
		int nResult = 0;
		if((nResult = this.projectDao.checkProject(project)) == 0) {
			this.projectDao.addProject(project);
		}
		return nResult;
	}
	
	@Override
	public int modProject(Project project) {
		int nResult = 0;
		if((nResult = this.projectDao.checkProject(project)) == 0) {
			this.projectDao.modProject(project);
		}
		return nResult;
	}
	
	@Override
	public void delProject(Project project) {
		this.projectDao.delProject(project);
	}
	
	@Override
	public Project queryProject(Project project) {
		return this.projectDao.queryProject(project);
	}
	
	@Override
	public List<Project> queryProjectList(Project project){
		return this.projectDao.queryProjectList(project);
	}
	
	@Override
	public Integer checkProject(Project project) {
		return this.projectDao.checkProject(project);
	}
	
}

package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Project;

public interface IProjectService {
	public int addProject(Project project);	
	public int modProject(Project project);
	public void delProject(Project project);
	public Project queryProject(Project project);
	public List<Project> queryProjectList(Project project);
	public Integer checkProject(Project project);
}

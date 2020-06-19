package cn.fy.cjgl.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fy.cjgl.entity.Project;
import cn.fy.cjgl.service.IProjectService;
import cn.fy.cjgl.util.DateTimeUtil;

@Controller
@RequestMapping("project")
public class ProjectController {
	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
    private IProjectService projectService;
	
	@RequestMapping("/projectPage")
	public ModelAndView projectPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("projectPage");
		
		modelAndView.setViewName("project/projectPage");
		return modelAndView;
	}
	
	@RequestMapping("/queryProjectList")
	@ResponseBody
	public Map<String, Object> queryProjectList(Project project,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		project.setDelflag("0");
		List<Project> projectList = this.projectService.queryProjectList(project);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projectList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", projectList);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	@RequestMapping(value= {"/addProject"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addProject(Project project, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		project.setCreatetime(now);
		project.setUpdatetime(now);
		project.setDelflag("0");
		
		int nResult = this.projectService.addProject(project);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "名称重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/modProject"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modProject(Project project, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		project.setUpdatetime(now);
		project.setDelflag("0");
		int nResult = 0;
		String msg = "";
		if(project.getProjectid() != 0) {
			nResult = this.projectService.modProject(project);
			if(nResult != 0) {
				msg = "名称重复";
			} else {
				msg = "操作成功";
			}
		} else {
			nResult = 1;
			msg = "内置项目不可修改";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/delProject"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delProject(Project project, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(project.getProjectid() != 0) {
			this.projectService.delProject(project);
			map.put("nResult", "0");
			map.put("msg", "操作成功");
		} else {
			map.put("nResult", "1");
			map.put("msg", "内置项目不可删除");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	
	@RequestMapping("/queryProjectCombobox")
	@ResponseBody
	public List<Project> queryProjectCombobox(Project project,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		//PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		project.setDelflag("0");
		List<Project> projectList = this.projectService.queryProjectList(project);

		return projectList;
	}
	
}

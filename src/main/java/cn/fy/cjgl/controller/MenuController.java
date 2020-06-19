package cn.fy.cjgl.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import cn.fy.cjgl.entity.Menu;
import cn.fy.cjgl.entity.Project;
import cn.fy.cjgl.entity.SubSystem;
import cn.fy.cjgl.service.IMenuService;
import cn.fy.cjgl.service.IProjectService;
import cn.fy.cjgl.service.ISubSystemService;

@Controller
@RequestMapping("menu")
public class MenuController {
private static final Logger log = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
    private IMenuService menuService;
	
	@Autowired
    private IProjectService projectService;
	
	@Autowired
    private ISubSystemService subSystemService;
	
	@RequestMapping("/menuPage")
	public ModelAndView menuPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("menuPage");
		
		modelAndView.setViewName("menu/menuPage");
		return modelAndView;
	}
	
	@RequestMapping("/queryAllMenu")
	@ResponseBody
	public List<Map<String, Object>> queryAllMenu(HttpServletRequest request, HttpServletResponse response) {

		Project project = new Project();
		project.setDelflag("0");
		
		PageHelper.orderBy("t.createtime ASC");
		List<Project> projectList = this.projectService.queryProjectList(project);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Project p : projectList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectid", p.getProjectid());
			map.put("id", "p"+p.getProjectid());
			map.put("pId", "-1");
			map.put("text", p.getProjectname());
			map.put("state", "open");
			map.put("iconCls", "icon-application");
			
			addSubSystem(map);
			
			list.add(map);
		}

		return list;
	}
	
	private void addSubSystem(Map<String, Object> map) {
		SubSystem subSystem = new SubSystem();
		subSystem.setProjectid((int)map.get("projectid"));
		subSystem.setDelflag("0");
		map.remove("projectid");
		
		PageHelper.orderBy("t.createtime ASC");
		List<SubSystem> subSystemList = this.subSystemService.querySubSystemList(subSystem);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(SubSystem s : subSystemList) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", "s"+s.getSubsystemid());
			hashMap.put("pId", map.get("id"));
			hashMap.put("text", s.getSubsystemname());
			hashMap.put("state", "open");
			hashMap.put("iconCls", "icon-mapadd");
			
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("subsystemid", s.getSubsystemid());
			hashMap.put("attributes", attributes);
			
			addMenu(hashMap, s.getSubsystemid(), 0);
			
			list.add(hashMap);
		}
		
		map.put("children", list);
	}
	
	private void addMenu(Map<String, Object> map, int subsystemid, int pmenuid) {
		Menu menu = new Menu();
		menu.setSubsystemid(subsystemid);
		menu.setPmenuid(pmenuid);
		
		PageHelper.orderBy("t.seqno ASC");
		List<Menu> menuList = this.menuService.queryMenuList(menu);
		
		if(menuList.size() == 0) {
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Menu m : menuList) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", ""+m.getMenuid());
			hashMap.put("pId", map.get("id"));
			hashMap.put("text", m.getMenuname());
			hashMap.put("state", "open");
			hashMap.put("iconCls", m.getIconcls());
			
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("subsystemid", subsystemid);
			attributes.put("menuurl", m.getMenuurl());
			attributes.put("seqno", m.getSeqno());
			hashMap.put("attributes", attributes);
			
			addMenu(hashMap, subsystemid, m.getMenuid());
			
			list.add(hashMap);
		}
		
		map.put("children", list);
		
	}
	
	@RequestMapping(value= {"/addMenu"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addMenu(Menu menu, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int nResult = this.menuService.addMenu(menu);
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
	
	@RequestMapping(value= {"/modMenu"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modMenu(Menu menu, HttpServletRequest request, HttpServletResponse response) throws IOException {

		int nResult = this.menuService.modMenu(menu);
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
	
	@RequestMapping(value= {"/delMenu"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delMenu(Menu menu, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		this.menuService.delMenu(menu);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", "0");
		map.put("msg", "操作成功");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
}

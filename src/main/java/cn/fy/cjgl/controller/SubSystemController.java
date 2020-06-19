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

import cn.fy.cjgl.entity.SubSystem;
import cn.fy.cjgl.service.ISubSystemService;
import cn.fy.cjgl.util.DateTimeUtil;

@Controller
@RequestMapping("subsystem")
public class SubSystemController {
	
	private static final Logger log = LoggerFactory.getLogger(SubSystemController.class);
	
	@Autowired
    private ISubSystemService subSystemService;
	
	@RequestMapping("/subSystemPage")
	public ModelAndView subSystemPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("subSystemPage");
		
		modelAndView.setViewName("subsystem/subSystemPage");
		return modelAndView;
	}
	
	@RequestMapping("/querySubSystemList")
	@ResponseBody
	public Map<String, Object> querySubSystemList(SubSystem subSystem,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		subSystem.setDelflag("0");
		List<SubSystem> subSystemList = this.subSystemService.querySubSystemList(subSystem);
		PageInfo<SubSystem> pageInfo = new PageInfo<SubSystem>(subSystemList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", subSystemList);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	@RequestMapping(value= {"/addSubSystem"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addSubSystem(SubSystem subSystem, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		subSystem.setCreatetime(now);
		subSystem.setUpdatetime(now);
		subSystem.setDelflag("0");
		
		int nResult = this.subSystemService.addSubSystem(subSystem);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "名称或类型重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/modSubSystem"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modSubSystem(SubSystem subSystem, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		subSystem.setUpdatetime(now);
		subSystem.setDelflag("0");
		int nResult = 0;
		String msg = "";
		if(subSystem.getSubsystemid() != 0) {
			nResult = this.subSystemService.modSubSystem(subSystem);
			
			if(nResult != 0) {
				msg = "名称或类型重复";
			} else {
				msg = "操作成功";
			}
		} else {
			nResult = 1;
			msg = "内置子系统无法修改";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/delSubSystem"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delSubSystem(SubSystem subSystem, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(subSystem.getSubsystemid() != 0) {
			this.subSystemService.delSubSystem(subSystem);

			map.put("nResult", "0");
			map.put("msg", "操作成功");
		} else {
			map.put("nResult", "1");
			map.put("msg", "内置子系统无法删除");
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping("/querySubSystemCombobox")
	@ResponseBody
	public List<SubSystem> querySubSystemCombobox(SubSystem subSystem,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		//PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		subSystem.setDelflag("0");
		List<SubSystem> subSystemList = this.subSystemService.querySubSystemList(subSystem);

		return subSystemList;
	}
	
}

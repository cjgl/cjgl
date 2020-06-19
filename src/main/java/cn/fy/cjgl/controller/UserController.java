package cn.fy.cjgl.controller;

import java.io.File;
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

import cn.fy.cjgl.entity.User;
import cn.fy.cjgl.service.IUserService;
import cn.fy.cjgl.util.DateTimeUtil;
import cn.fy.cjgl.util.PoiWriteExcel;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private IUserService userService;
	
	@RequestMapping("/userPage")
	public ModelAndView userPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("userPage");
		
		modelAndView.setViewName("user/userPage");
		return modelAndView;
	}
	
	@RequestMapping("/queryUserList")
	@ResponseBody
	public Map<String, Object> queryUserList(User user, Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		//PageHelper.startPage(offset, limit, sort + " " + order);
		PageHelper.offsetPage((page-1)*rows, rows, true);
		PageHelper.orderBy("t.createtime DESC");
		
		user.setDelflag("0");
		List<User> userList = this.userService.queryUserList(user);
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", userList);
		map.put("total", pageInfo.getTotal());

		return map;
	}
	
	@RequestMapping(value= {"/addUser"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addUser(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		user.setCreatetime(now);
		user.setUpdatetime(now);
		user.setDelflag("0");
		
		int nResult = this.userService.addUser(user);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "登录名重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/modUser"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modUser(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		user.setUpdatetime(now);
		user.setDelflag("0");
		
		int nResult = 0;
		String msg = "";
		
		if(user.getUserid() != 0) {
			nResult = this.userService.modUser(user);
			
			if(nResult != 0) {
				msg = "登录名重复";
			} else {
				msg = "操作成功";
			}
		} else {
			nResult = 1;
			msg = "内置用户无法修改";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/delUser"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delUser(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(user.getUserid() != 0) {
			this.userService.delUser(user);

			map.put("nResult", "0");
			map.put("msg", "操作成功");
		} else {
			map.put("nResult", "1");
			map.put("msg", "内置用户无法删除");
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value= {"/exportUser"})
	public void exportUser(User user, HttpServletRequest request, HttpServletResponse response) {

		try {
			String path = request.getSession().getServletContext().getRealPath("");
			path = path + "export";
			File fileFolder = new File(path);
			if (!fileFolder.isDirectory()) {
				fileFolder.mkdirs();
			}
			String tempName = "用户-" + DateTimeUtil.getDateTime17() + ".xls";
			path = path + File.separator + tempName;
			
			// 设置分页
			PageHelper.orderBy("t.createtime DESC");
			
			user.setDelflag("0");
			List<User> userlist = this.userService.queryUserList(user);

			PoiWriteExcel<User> pwe = new PoiWriteExcel<User>();

			if (pwe.build("用户", path, User.EXPORT_HEADERS, userlist).export()) {
				PoiWriteExcel.downloadFile(request, response, path, tempName);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) {

		// 设置为下载application/x-download
		response.setContentType("application/x-download");

		// 中文编码转换
		try {
			displayName = new String(displayName.getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + displayName);
		try {
			java.io.OutputStream os = response.getOutputStream();
			java.io.FileInputStream fis = new java.io.FileInputStream(filePath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

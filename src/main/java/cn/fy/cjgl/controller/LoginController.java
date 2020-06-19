package cn.fy.cjgl.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.fy.cjgl.entity.ConfigEntity;
import cn.fy.cjgl.entity.Result;
import cn.fy.cjgl.entity.Rolemenu;
import cn.fy.cjgl.entity.User;
import cn.fy.cjgl.entity.Userrole;
import cn.fy.cjgl.service.IRolemenuService;
import cn.fy.cjgl.service.IUserroleService;
import cn.fy.cjgl.util.Consts;

@Controller
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ConfigEntity config;

	@Autowired
	private IUserroleService userroleService;

	@Autowired
	private IRolemenuService rolemenuService;

	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
		log.info(config.getUploadPath());
		modelAndView.setViewName("index");
		modelAndView.addObject("sessionId", request.getSession().getId());
		return modelAndView;
	}

	@RequestMapping("/")
	public ModelAndView index0(ModelAndView modelAndView, HttpServletRequest request) {
		log.info(config.getUploadPath());
		modelAndView.setViewName("index");
		modelAndView.addObject("sessionId", request.getSession().getId());
		return modelAndView;
	}

	//@RequestMapping(value = "/login", produces="application/json")
	@RequestMapping(value= "/login", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String login(User user, HttpServletRequest request) throws IOException {
		Result result = null;
		
		ObjectMapper mapper = new ObjectMapper();

		String validateCode = request.getParameter("validateCode");
		String s_validateCode = (String) request.getSession().getAttribute(Consts.S_VALIDATECODE);
		if(s_validateCode == null || "".equals(s_validateCode)) {
			result = new Result("-1", "验证码错误");
			return mapper.writeValueAsString(result);
		}
		if (!validateCode.equalsIgnoreCase(s_validateCode)) {
			result = new Result("-2", "验证码错误");
			return mapper.writeValueAsString(result);
		}

		// 组建Subject主体.
		Subject subject = SecurityUtils.getSubject();

		// 创建 token 令牌
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPwd());

		// 用户登录操作.
		try {
			subject.login(token);
			result = new Result("0", "登录成功");
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			result = new Result("-2", e.getMessage());
		}
		return mapper.writeValueAsString(result);
	}
	
	@RequestMapping("/relogin")
	public ModelAndView relogin(ModelAndView modelAndView, HttpServletRequest request) {
		log.info(config.getUploadPath());
		modelAndView.setViewName("relogin");
		return modelAndView;
	}

	@RequestMapping("/main")
	public ModelAndView main(ModelAndView modelAndView, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Consts.S_USER);

		Userrole userrole = new Userrole();
		userrole.setUserid(user.getUserid());
		userrole.setDelflag("0");
		List<Userrole> userroleList = this.userroleService.queryUserroleListByUserid(userrole);

		List<Integer> roleidList = new ArrayList<Integer>();
		for (Userrole e : userroleList) {
			roleidList.add(e.getRoleid());
		}

		List<Rolemenu> rolemenuList = null;
		if(userroleList.size() > 0) {
			Rolemenu rolemenu = new Rolemenu();
			rolemenu.setRoleidList(roleidList);
			rolemenu.setPmenuid(0);
			rolemenuList = this.rolemenuService.queryRolemenuListByRoleids(rolemenu);

			for (Rolemenu e : rolemenuList) {
				rolemenu.setPmenuid(e.getMenuid());
				List<Rolemenu> rolemenuList1 = this.rolemenuService.queryRolemenuListByRoleids(rolemenu);
				e.setRolemenuList(rolemenuList1);
			}
		}

		modelAndView.setViewName("main");
		modelAndView.addObject("rolemenuList", rolemenuList);
		return modelAndView;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView modelAndView, HttpServletRequest request) {
		// 组建Subject主体.
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/unauthorized")
	public ModelAndView unauthorized(ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("unauthorized");
		modelAndView.addObject("sessionId", request.getSession().getId());
		return modelAndView;
	}

	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("validateCode");

		// 在内存中创建图象
		int width = 65, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = codeArray[random.nextInt(61)] + "";// String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}
		// 将认证码存入SESSION
		request.getSession().setAttribute(Consts.S_VALIDATECODE, sRand);
		log.debug(Consts.S_VALIDATECODE + " : " + sRand);
		// 图象生效
		g.dispose();

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ImageOutputStream imageOut = ImageIO.createImageOutputStream(response.getOutputStream());
		ImageIO.write(image, "JPEG", imageOut);
		imageOut.close();

	}

	public static char[] codeArray = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	/*
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}

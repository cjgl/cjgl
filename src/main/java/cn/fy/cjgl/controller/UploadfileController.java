package cn.fy.cjgl.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("file")
public class UploadfileController {
	private static final Logger log = LoggerFactory.getLogger(UploadfileController.class);
	
	@RequestMapping(value="uploadFile.do", method = RequestMethod.POST,
			produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String uploadFile(@RequestParam("name") String name,
	        @RequestParam("upload") MultipartFile upload,
	        HttpServletRequest request, HttpServletResponse response){
		log.info("uploadFile");
		
		try {
			String folderPath = request.getSession().getServletContext().getRealPath("")+File.separator+"uploadFile";
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			File f = new File(folderPath+File.separator+name);
			log.info(f.getPath());
			byte[] bytes = upload.getBytes();
			log.info("OriginalFileName:"+upload.getOriginalFilename());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
			stream.write(bytes); 
	        stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		return "{\"msg\":\""+"上传成功！"+"\",\"success\":true}";
	}   

}

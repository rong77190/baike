package com.baike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 上传图片的控制器
 * @author xiechur
 *
 */


@Controller
public class UploadController {
	
	@RequestMapping("/upload")
	@ResponseBody
	public Object addUser(@RequestParam("image") CommonsMultipartFile file, HttpServletRequest request) throws IOException{
		System.out.println("fileName---->" + file.getOriginalFilename());
		//项目暂时只需要image
		HashMap<String, String> extMap = new HashMap<String, String>();
		Map<String, Object> resultmap = new HashMap<String,Object>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		String dirName = "image";
		String fileName = file.getOriginalFilename();
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		System.out.println("<<<<<<<<<<<fileExt<<<<<<<<<<<<<"+fileExt);
		if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
			resultmap.put("success",false);
			resultmap.put("errors","上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
			return resultmap;
		}
		if(!file.isEmpty()){
			String basePath = "resources/upload";//上传基本路径
			String savePath = request.getSession().getServletContext().getRealPath(basePath);//上传保存路径，绝对路径
//			System.out.println("savePath》》》》》》》》》》》》》》》》》》》》》"+savePath);
			String newImageName = new Date().getTime() + file.getOriginalFilename();//文件重命名，防止重复
			String imagePath = savePath + "/"+ newImageName;//文件保存绝对路径
//			System.out.println("imagePath》》》》》》》》》》》》》》》》》》》》》"+imagePath);
			try {
				FileOutputStream os = new FileOutputStream(imagePath);
				InputStream in = file.getInputStream();
				int b = 0;
				while((b=in.read()) != -1)	{
					os.write(b);
				}
				os.flush();
				os.close();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				resultmap.put("success",false);
				resultmap.put("errors","文件上传错误");
				return resultmap;
			}
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("image",basePath+"/"+newImageName);
			resultmap.put("success",true);
			return resultmap;
		}else {
			resultmap.put("success",false);
			resultmap.put("errors","上传文件为空");
			return resultmap;
		}

	}


	@RequestMapping("/upload2")
	public String upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
		System.out.println("________________________________________"+request.getSession().getServletContext()
		);
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			
			Iterator<String>  iter = multiRequest.getFileNames();
			while(iter.hasNext()){
					MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					String fileName = "demoUpload" + file.getOriginalFilename();
					String path = "D:/" + fileName;
					
					File localFile = new File(path);
					
					file.transferTo(localFile);
				}
				
			}
			
			
			
		}
		return "/success";
	}
	
//	@RequestMapping("/toUpload")
//	public String toUpload(){
//		return "/upload";	}

}


package com.ssmueditor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;
import com.ssmueditor.util.FastDFSClient;
import com.ssmueditor.util.JsonUtils;

@Controller
@RequestMapping(value = "/ueditor")
public class UploadController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/upload-img")
	public String upload() {
		return "upload";
	}

	@RequestMapping(value = "/upload")
	public void config(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/uploadimage")
	@ResponseBody
//	public Map<String,String> uploadImage(@RequestParam("upfile")  CommonsMultipartFile upfile,HttpServletRequest request,Model model) throws Exception{ 
//		
//		//得到路径 C:/tomcat/webapps/testDemo/
//        String rootPath = request.getSession().getServletContext().getRealPath("/");
//		rootPath = rootPath.replaceAll("\\\\", "/");
//		
//		String path = rootPath + "/ueditor/jsp/upload/image";
//		
//		File f = new File(path);
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//	
//		FileItem item = upfile.getFileItem();
//		//文件路径
//		String pathFileName = item.getName();
//		//字节数
//		long l = item.getSize();
//		String fileSize = Long.toString(l);
//		//文件名
//		int start = pathFileName.lastIndexOf("\\");
//		String fileName = pathFileName.substring(start + 1);
//		//后缀 .jpg
//		int indexName = fileName.lastIndexOf('.');
//		String subName = fileName.substring(indexName);
//		//新文件名
//		String nowName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) +"_"+ fileName;
//			
//		item.write(new File(path, nowName));
//		
//		String strBackUrl = "http://" + request.getServerName() //服务器地址
//        + ":"
//        + request.getServerPort()           //端口号  
//        + request.getContextPath();      //项目名称
//		
//		Map<String,String> map = new HashMap<String,String >();
//		//文件原名称 
//		map.put("title", nowName);
//		//现在文件名称
//		map.put("original", fileName);
//		//文件大小（字节数）
//		map.put("size", fileSize);
//		//是否上传成功
//		map.put("state", "SUCCESS");
//        //文件类型 .+后缀名
//        map.put("type", subName);
//        //文件路径
//        map.put("url",  strBackUrl+"/ueditor/jsp/upload/image/"+nowName);
//		
//		return map;
//		
//	}

	public String picUpload(MultipartFile uploadFile){
		try {
			//接收上传的文件
			//取扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url = IMAGE_SERVER_URL + url;
			System.out.println(url);
			//响应上传图片的url
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}

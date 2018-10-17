package com.ssmueditor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssmueditor.ueditor.ActionEnter;
import com.ssmueditor.util.FastDFSClient;

@Controller
@RequestMapping(value = "/ueditor")
public class UploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/upload-img")
	public String upload() {
		return "upload";
	}

	@RequestMapping(value = "/ueditorConfig")
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
//	}

	public Map uploadImage(@RequestParam("upfile") CommonsMultipartFile upfile, HttpServletRequest request, Model model)
			throws Exception {
		System.out.println("进入上传 controller");
		try {
			String fid = null;
			// 上传文件名+后缀，保存到数据库，以后与 fid 进行匹配
			String fileName = upfile.getOriginalFilename();
			// 获得文件后缀
			String extraName = fileName.substring(fileName.lastIndexOf(".")+ 1);
			// String fileId = FileCopy.upload(file.getBytes(),fileName);
			// 上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			// 获得上传后的图片路径。eg：group1/M00/00/00/wKgZr1vEiB-AWZF-AADW-NO57MA881.jpg
			String pathAndFileName = fastDFSClient.uploadFile(upfile.getBytes(), extraName);
			// 获得 FID
//			String[] afterFileName = pathAndFileName.split("/");
//			fid = afterFileName[afterFileName.length - 1].split("\\.")[0];
			fid = "";
			// 拼接获得 图片完整路径
			String url = IMAGE_SERVER_URL + pathAndFileName;
			return resultMap("SUCCESS", url, upfile.getSize(), fid, fileName, extraName);
		} catch (Exception e) {
			return resultMap("文件上传失败", "", 0, "", "", "");
		}

	 }
	private Map<String, Object> resultMap(String state, String url, long size, String title, String original,
			String type) {
		Map<String, Object> result = new HashMap();
		result.put("state", state);// 上传成功与否
		result.put("url", url);// 上传后图片 完整的url
		result.put("size", size);// 图片大小
		result.put("title", title);// 上传后 的图片名，即FID（fastdfs处理后的文件名）
		result.put("original", original);// 上传前文件名
		result.put("type", type);// 文件 后缀
		return result;
	}

	// public String picUpload(MultipartFile uploadFile){
	// try {
	// //接收上传的文件
	// //取扩展名
	// String originalFilename = uploadFile.getOriginalFilename();
	// String extName =
	// originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
	// //上传到图片服务器
	// FastDFSClient fastDFSClient = new
	// FastDFSClient("classpath:resource/client.conf");
	// String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
	// url = IMAGE_SERVER_URL + url;
	// System.out.println(url);
	// //响应上传图片的url
	// Map result = new HashMap<>();
	// result.put("error", 0);
	// result.put("url", url);
	// return JsonUtils.objectToJson(result);
	// } catch (Exception e) {
	// e.printStackTrace();
	// Map result = new HashMap<>();
	// result.put("error", 1);
	// result.put("message", "图片上传失败");
	// return JsonUtils.objectToJson(result);
	// }
	// }
}

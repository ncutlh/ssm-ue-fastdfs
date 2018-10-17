package com.ssmueditor.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.ssmueditor.util.FastDFSClient;

public class TestFastDFS {

	@Test
	public void uploadFile() throws Exception {
		//1、向工程中添加jar包
		//2、创建一个配置文件。配置tracker服务器地址
		//3、加载配置文件
		ClientGlobal.init("E:/LocalDocument/ssm-ue-fastdfs/ssm-ueditor/src/main/resources/resource/client.conf");
		//4、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		//5、使用TrackerClient对象获得trackerserver对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		//6、创建一个StorageServer的引用null就可以。
		StorageServer storageServer = null;
		//7、创建一个StorageClient对象。trackerserver、StorageServer两个参数。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//8、使用StorageClient对象上传文件。
		String[] strings = storageClient.upload_file("E:/1.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
			
		}
	}
	/**
	 * 上传
	 * @throws Exception
	 */
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("E:/dev_software/WorkSpace/JavaEE/workspace/taotao-manager-web/src/main/resources/resource/client.conf");
		String string = fastDFSClient.uploadFile("E:/1.jpg");
		System.out.println(string);
	}
	
	//http://192.168.25.175/group1/M00/00/00/wKgZr1vBlbmAHDboAADW-NO57MA564.jpg
	/**
	 * 下载 
	 * @throws Exception
	 */
	@Test
	public void testFastDfsClientDownLoad() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("E:/dev_software/WorkSpace/JavaEE/workspace/taotao-manager-web/src/main/resources/resource/client.conf");
		String group = "group1";
		String pathAndFilename = "M00/00/00/wKgZr1vBlbmAHDboAADW-NO57MA564.jpg";
		fastDFSClient.downloadFile(group, pathAndFilename);
	}
	/**
	 * 删除
	 * @throws Exception
	 */
	@Test
	public void testFastDfsClientDelete() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("E:/dev_software/WorkSpace/JavaEE/workspace/taotao-manager-web/src/main/resources/resource/client.conf");
		String group = "group1";
		String pathAndFilename = "M00/00/00/wKgZr1vBlbmAHDboAADW-NO57MA564.jpg";
		fastDFSClient.deleteFile(group, pathAndFilename);
	}
}

package file.upload;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;
 
public class FileReceiver extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String uploadPath = "D:\\upload\\XM09"; // 上传文件的目录
	File tempPathFile;
	// 重写doPost方法，处理事件识别请求
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
 		System.out.println("FileReceiver start");
		String currentFilename="";
 		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(30720); // 设置缓冲区大小，这里是30kb
			factory.setRepository(tempPathFile);// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(31457280); // 设置最大文件尺寸，这里是30MB
 			System.out.println("已设置最大文件尺寸为30MB");
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			System.out.println("i");
			while (i.hasNext()) {
				FileItem fi = i.next();
				System.out.println("i.next()");
				String fileName = fi.getName();
				if (fileName != null) {
					currentFilename=fileName;
					System.out.println("filename="+fileName);
					File fullFile = new File(new String(fi.getName().getBytes(), "utf-8")); // 解决文件名乱码问题
					System.out.println("fullFile");
					File savedFile = new File(uploadPath, fullFile.getName());
					System.out.println("savedFile");
					
					fi.write(savedFile);
					
					System.out.println("fi.write(savedFile)");
				}
			}
			System.out.println("upload succeed");
		} catch (Exception e) {
 			System.out.println("upload fail");
		}
		//返回结果
		JSONObject json=new JSONObject();
		try {
			System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
			String objectId="OBJECT_"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new java.util.Date());
			String fileUrl="/uploadfile/"+currentFilename;		//要设置虚拟路径
			json.put("attachment_filename",currentFilename);
			json.put("attachment_object_id",objectId);
			json.put("attachment_file_url",fileUrl);//可以拷贝到浏览器地址栏访问来测试
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
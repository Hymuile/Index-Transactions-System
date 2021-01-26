package wechat_transaction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.ylx_db;

public class SellDao extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String indexName = request.getParameter("index_name");
		//String hold = request.getParameter("hold");
		request.setCharacterEncoding("UTF-8");
		System.out.println("FileDao-页面传递过来的数据获取完毕");
		System.out.println("id=" + id + ",index_name=" + indexName );

		ylx_db insert_db = new ylx_db("xm09");
		
		String sql = "delete from own_index where index_name = '"+indexName+"' ";
		System.out.println("即将执行的SQL语句是：" + sql);
		
		insert_db.executeUpdate(sql);
		insert_db.close();
		System.out.println("操作数据完毕，关闭了数据库！");
		System.out.println("FileDao页面执行完毕！");
		
		// 返回结果
		JSONObject json = new JSONObject();
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}

package wechat_transaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class LoginDao extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ///////////////////////////////////////////////////////////
        // 链接数据库
        // 获取add_file.jsp页面提交后传递过来的参数，在form里的参数才能传递过来，注意name和id的区别
        String id = request.getParameter("id");
        String Tel = request.getParameter("tel");
        String Name = request.getParameter("name");
        String Url = request.getParameter("url");
        request.setCharacterEncoding("UTF-8");
        System.out.println("FileDao-页面传递过来的数据获取完毕");
        System.out.println("id=" + id + ",name=" + Name+ ",tel=" + Tel+ ",url=" + Url );

        // 开始连接数据库，需要先把mysql-connector-java-5.0.4-bin.jar和json.jar拷贝到ROOT/WEB-INF/lib下
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        System.out.println("加载了JDBC驱动");

        // 然后链接数据库，开始操作数据表
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3366/xm09?user=XM09&password=123456&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT");
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");
            String sql = "update wechat_login set tel= '"+Tel+"' , name= '"+Name+"', url= '"+Url+"' where tag='a'";
            System.out.println("即将执行的SQL语句是：" + sql);
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
            System.out.println("操作数据完毕，关闭了数据库！");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        System.out.println("FileDao页面执行完毕！");
        ///////////////////////////////////////////////////////////
        // 返回结果
        JSONObject json = new JSONObject();
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

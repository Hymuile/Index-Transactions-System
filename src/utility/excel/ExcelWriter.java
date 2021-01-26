package utility.excel;

import jxl.*;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExcelWriter {

	private String dbase = "";
	private String tablename = "";

	public void CreExcel(JSONObject jsonObj,String tablenick, String[] cols, String filePath) throws JSONException {
		JSONArray arr=jsonObj.getJSONArray("aaData");
		String targetfile = filePath;// 输出的excel文件名
		String worksheet = tablenick;// 输出的excel文件工作表名

		WritableWorkbook workbook;
		try {

			OutputStream os = new FileOutputStream(targetfile);
			workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet(worksheet, 0);
			jxl.write.Label label;

			List excel_col_name = (List) new java.util.ArrayList();
			List excel_col_type = (List) new java.util.ArrayList();
			List excel_col_nick = (List) new java.util.ArrayList();

			for (int i = 0; i < cols.length; i++) {

				excel_col_name.add(cols[i].split("⊙")[1]);
				excel_col_type.add(cols[i].split("⊙")[0]);
				excel_col_nick.add(cols[i].split("⊙")[2]);

			}

			for (int i = 0; i < excel_col_nick.size(); i++) {
				String col_nick = (String) excel_col_nick.get(i);

				label = new jxl.write.Label(i, 0, col_nick); // put the title in
				// row1
				sheet.addCell(label);

			}

			List data = (List) new java.util.ArrayList();

			int n = 1;

			for(int j=0;j<arr.length();j++) {
				ArrayList list=(ArrayList) arr.get(j);
				for (int i = 0; i < excel_col_name.size(); i++) {
					String col_name = (String) excel_col_name.get(i);
					String col_type = (String) excel_col_type.get(i);

					if (col_type.indexOf("text") != -1 || col_type.indexOf("mediumtext") != -1 || col_type.indexOf("varchar") != -1) {

						label = new jxl.write.Label(i, n, list.get(i)+"");
						sheet.addCell(label);
					} else {

						label = new jxl.write.Label(i, n, list.get(i)+"");
						sheet.addCell(label);
					}
				}
				n++;
			}
			workbook.write();
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {

		String targetfile = "c:/out.xls";// 输出的excel文件名
		String worksheet = "List";// 输出的excel文件工作表名
		String[] title = { "ID", "NAME", "DESCRIB" };// excel工作表的标题

		WritableWorkbook workbook;
		try {
			// 创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下
			// workbook = Workbook.createWorkbook(new File("output.xls"));
			OutputStream os = new FileOutputStream(targetfile);
			workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet(worksheet, 0);

			jxl.write.Label label;
			for (int i = 0; i < title.length; i++) {
				for (int j = 0; j < 50; j++) {
					// Label(列号,行号 ,内容 )
					label = new jxl.write.Label(i, j, title[i]); // put the
					// title in
					// row1
					sheet.addCell(label);
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
package futures.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.json.JSONException;
import futures.dao.IndexDao;
import futures.dao.Index;

public class GetIndexData implements ServletContextListener {
	private Timer timer = null;

	public void contextDestroyed(ServletContextEvent arg0) {}

	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer(true);
		MyTimerTask task = new MyTimerTask();
		timer.schedule(task, 2000,10000);		//延迟2秒后，每隔10秒运行一次
	}

	public class MyTimerTask extends TimerTask {
		public void run() {
			try {//多用函数
				getIndexData();
				getStockData();
			} catch (MalformedURLException | JSONException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void getIndexData() throws MalformedURLException, JSONException, SQLException{
		try {
			//创建一个URL实例
			URL url = new URL("http://hq.sinajs.cn/rn=kpu7x&list=hf_AHD,hf_BO,hf_BTC,hf_C,hf_CAD,hf_CHA50CFD,hf_CL,hf_CT,hf_ES,hf_FCPO,hf_GC,hf_HG,hf_HSI,hf_LHC,hf_NG,hf_NID,hf_NK,hf_NQ,hf_OIL,hf_PBD,hf_RS,hf_S,hf_SI,hf_SM,hf_SND,hf_VX,hf_W,hf_XAG,hf_XAU,hf_XPD,hf_XPT,hf_YM,hf_ZSD");
			//URL url2 = new URL("http://hq.sinajs.cn/rn=wjsnq&list=gb_aapl,gb_acm,gb_acn,gb_acxm,gb_adbe,gb_adi,gb_adsk,gb_akam,gb_amat,gb_amd,gb_amzn,gb_aph,gb_arrs,gb_ati,gb_atvi,gb_avgo,gb_azpn,gb_brcd,gb_ca,gb_cdns,gb_cern,gb_cmbm,gb_cree,gb_crm,gb_crwd,gb_csco,gb_ctsh,gb_ctxs,gb_cvlt,gb_dcm,gb_ddd,gb_dell,gb_dlb,gb_dt,gb_ea,gb_ear,gb_ebay,gb_eqix,gb_eric,gb_fb,gb_ffiv,gb_ftnt,gb_ftr,gb_fvrr,gb_glw,gb_goog,gb_grpn,gb_hpq,gb_ibm,gb_igt,gb_intc,gb_intu,gb_jbl,gb_jmia,gb_jnpr,gb_klac,gb_lrcx,gb_lyft,gb_mchp,gb_mdla,gb_mdso,gb_mmi,gb_mon,gb_msft,gb_msi,gb_mu,gb_mwk,gb_mxim,gb_nflx,gb_nok,gb_now,gb_ntap,gb_ntdoy,gb_nuan,gb_nvda,gb_opra,gb_orcl,gb_pins,gb_pmtg,gb_pypl,gb_qcom,gb_qrvo,gb_rht,gb_s,gb_sap,gb_sats,gb_snap,gb_sne,gb_snow,gb_sono,gb_spce,gb_ssys,gb_stx,gb_symc,gb_synt,gb_t,gb_tdc,gb_tds,gb_ter,gb_trip");
			
			try {
				//通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is,"GBK");

				//为字符输入流添加缓冲
				BufferedReader br = new BufferedReader(isr);
				String data = br.readLine();//读取数据
				while (data!=null){//循环读取数据
					IndexDao indexDao= new IndexDao();
					Index index = new Index();
					String[] list = data.split("=");
					String left = list[0];
					String right = list[1];
					String[] leftList = left.split("_");
					String indexId = leftList[leftList.length-1];
					right = right.replace("\"", "");
					right = right.replace(";", "");
					String[] rightList=right.split(",");
					
					String latestPrice = rightList[0];
					String buyPrice = rightList[2];
					String sellPrice = rightList[3];
					String highestPrice = rightList[4];
					String lowestPrice = rightList[5];
					String priceYesterday = rightList[7];
					String priceToday = rightList[8];
					String inventory=rightList[9];
					String indexName=rightList[13];

					Double changeAmount = (Double.parseDouble(latestPrice) -Double.parseDouble(priceYesterday)) ;
					index.setIndexId(indexId);
					index.setLatestPrice(latestPrice);
					index.setBuyPrice(buyPrice);
					index.setSellPrice(sellPrice);
					index.setHighestPrice(highestPrice);
					index.setLowestPrice(lowestPrice);
					index.setPriceYesterdat(priceYesterday);
					index.setPriceToday(priceToday);
					index.setIndexName(indexName);
					index.setChangeAmount(changeAmount);
					index.setInventory(inventory);
					index.setDbName("xm09");
					indexDao.updateIndex(index);
					data = br.readLine();
				}
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void getStockData() throws MalformedURLException, JSONException, SQLException{
		try {
			//创建一个URL实例
			URL url = new URL("http://hq.sinajs.cn/rn=x0yfl&list=DINIW,fx_saudcny,fx_saudusd,fx_scadcny,fx_schfcny,fx_scnyhkd,fx_scnyjpy,fx_scnykrw,fx_seurcny,fx_seurusd,fx_sgbpcny,fx_sgbpusd,fx_smyrcny,fx_snzdcny,fx_snzdusd,fx_srubcny,fx_ssgdcny,fx_stwdcny,fx_susdbrl,fx_susdcad,fx_susdchf,fx_susdcny,fx_susdcny,fx_susdhkd,fx_susdinr,fx_susdjpy,fx_susdkrw,fx_susdmop,fx_susdmyr,fx_susdrub,fx_susdsgd,fx_susdthb,fx_susdtwd,fx_susdzar");
			try {
				//通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is,"GBK");

				//为字符输入流添加缓冲
				BufferedReader br = new BufferedReader(isr);
				String data = br.readLine();//读取数据
				while (data!=null){//循环读取数据
					IndexDao indexDao= new IndexDao();
					Index index = new Index();
					String[] list = data.split("=");
					String left = list[0];
					String right = list[1];
					left = left.replace("hq_str_fx_s", "hq_str_fx_");
					String[] leftList = left.split("_");
					String indexId = leftList[leftList.length-1].toUpperCase();
					
					right = right.replace("\"", "");
					right = right.replace(";", "");
					String[] rightList=right.split(",");
					
					String latestPrice = rightList[1];
					String buyPrice = rightList[1];
					String sellPrice = rightList[2];
					String priceYesterday = rightList[3];
					String priceToday = rightList[5];
					String highestPrice = rightList[6];
					String lowestPrice = rightList[7];
					Double changeAmount = (Double.parseDouble(latestPrice) -Double.parseDouble(priceYesterday)) ;
					String indexName=rightList[9];

					index.setIndexId(indexId);
					index.setLatestPrice(latestPrice);
					index.setBuyPrice(buyPrice);
					index.setSellPrice(sellPrice);
					index.setHighestPrice(highestPrice);
					index.setLowestPrice(lowestPrice);
					index.setPriceYesterdat(priceYesterday);
					index.setPriceToday(priceToday);
					index.setIndexName(indexName);
					index.setChangeAmount(changeAmount);
					index.setInventory("0");
					index.setDbName("xm09");
					indexDao.updateIndex(index);
					data = br.readLine();
				}
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

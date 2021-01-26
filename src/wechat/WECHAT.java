package wechat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONObject;
import org.xml.sax.InputSource;
public class WECHAT {
	HttpSession session = null;
	public String module = "";
	public String wechatName = "";
	public String wechatId = "";
	public String mobile = "";				//平台客服电话

	public String appId = "";
	public String appSecret = "";
	public String tradeKey = "";
	public String tradeId = "";
	public String accessToken="";
	public int    expiresIn=0;
	public String databaseName = "ylxdb";
	public String createTime="";
	
	public String wechatServerAddress="";	//微信平台上认证的域名所在服务器地址
	public String serverAddress="";			//管理平台的服务器地址
	public String serverPort="";			//管理平台的服务器端口
	//个人信息
	public String openId = "";	//这个是个人的open_id
	public String userId ="";
	public String nickName="";
	public String avatar="";
	public String sex = "";
	public String language = "";
	public String city = "";
	public String province = "";
	public String country = "";
	public String subscribeTime ="";
	public String roleId ="";
	public String groupId ="";

	public void setSession(HttpSession session) throws Exception {
		try {
			this.session = session;
			if (session.getAttribute("unit_db_name") == null)
				session.setAttribute("unit_db_name", databaseName);
		} catch (Exception e) {
			showDebug("初始化Bean出现错误！" + e.getMessage());
		}
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/WECHAT]"+msg);
	}

	public String getAccessToken(String appId, String appSecret) {
		String token = "";
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
		try {
			StringBuffer document = new StringBuffer();
			URL url = new URL(tokenUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line + " ");
			reader.close();
			String json = document.toString();// 返回值
			showDebug("返回json:" + json);
			// 开始解构
			JSONObject jsonObject = new JSONObject(json);
			Iterator keyIter = jsonObject.keys();
			String key = null;
			String value = null;
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.getString(key);
				if (key.equals("access_token")) {
					token = value;
				}
			}
		} catch (Exception ex) {
			showDebug(ex.getMessage());
		}
		return token;
	}

	public String getAccessToken() {
		showDebug("[WECHAT-getAccessToken]开始：createTime="+createTime+",appId="+appId+",appSecret="+appSecret+",tradeKey="+tradeKey+",tradeId="+tradeId+",accessToken="+accessToken+",expiresIn="+expiresIn);
		String token = "";
		String expire = "";
		if ((session != null) && (accessToken!=null) && (!(accessToken.isEmpty()))) {
			token = accessToken;
			showDebug("[WECHAT-getAccessToken()]检查到accessToken存在："+accessToken);
		} else {
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
			showDebug("准备运行去获取accessToke的url是："+url);
			try {
				URL urlGet = new URL(url);
				HttpURLConnection httpGet = (HttpURLConnection) urlGet.openConnection();
				httpGet.setRequestMethod("GET"); // 必须是get方式请求
				httpGet.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpGet.setDoOutput(true);
				httpGet.setDoInput(true);
				System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
				System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
				httpGet.connect();
				InputStream is = httpGet.getInputStream();
				int size = is.available();
				byte[] jsonBytes = new byte[size];
				is.read(jsonBytes);
				String message = new String(jsonBytes, "UTF-8");
				showDebug("wechat getAccessToken返回的json是==================================================："+message);
				JSONObject demoJson = new JSONObject(message);
				token = demoJson.getString("access_token");
				expire = demoJson.getString("expires_in");
				//showDebug("获取的关键字是：" + token);
				//记录创建时间
				String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date());
				is.close();
				accessToken=token;
				expiresIn=Integer.parseInt(expire);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return token;
	}
	/*
	 * 功能：获取用户的open_id
	 * 参数：需要提供的参数是：redirectUrl，wechatServerAddress,serverAddress,serverPort,appId,
	 */
	public void getOpenid(HttpServletRequest request, HttpServletResponse response,String resultUrl) throws IOException{
		String redirectUrl="http://"+wechatServerAddress+"/wechat/auth_callback.php";
		String wechatUrl="https://open.weixin.qq.com/connect/oauth2/authorize";
		wechatUrl=wechatUrl+"?appid="+appId;
		wechatUrl=wechatUrl+"&redirect_uri="+redirectUrl;
		wechatUrl=wechatUrl+"&response_type=code";
		wechatUrl=wechatUrl+"&scope=snsapi_base";
		//wechatUrl=wechatUrl+"&state=http://"+serverAddress+":"+serverPort+"/ylxxt/"+resultUrl+","+appId+","+appSecret+"#wechat_redirect";
		wechatUrl=wechatUrl+"&state="+resultUrl+","+appId+","+appSecret+"#wechat_redirect";
		showDebug("[WECHAT]getOpenid函数：wechatUrl="+wechatUrl);
		response.sendRedirect(wechatUrl);
	}
	/*
	 * 
	 * <xml> <appid>wxfee15e23416475c6</appid>
	 * <openid>oCPo1tzyGy8V3jxGrdhwjd8mS-hI</openid> <attach>鏀粯娴嬭瘯</attach>
	 * <body>鏌愭煇鍟嗗搧</body> <mch_id>1305684801</mch_id>
	 * <nonce_str>CAA202034F268232C26FAC9435F54E15</nonce_str>
	 * <notify_url>http://www.xxx.com/wechat/unifiedorder_callback.php</notify_url>
	 * <out_trade_no>LP000000000000000000000000000112</out_trade_no>
	 * <spbill_create_ip>127.0.0.1</spbill_create_ip> <total_fee>2</total_fee>
	 * <trade_type>JSAPI</trade_type> <device_info>1000</device_info>
	 * <sign>D0A127D0A389BB2795E54B4B98EB7F1B</sign> </xml> 成功后返回：
	 * <xml><return_code><![CDATA[SUCCESS]]></return_code>
	 * <return_msg><![CDATA[OK]]></return_msg>
	 * <appid><![CDATA[wxfee15e23416475c6]]></appid>
	 * <mch_id><![CDATA[1305684801]]></mch_id>
	 * <device_info><![CDATA[1000]]></device_info>
	 * <nonce_str><![CDATA[ui8gHNhR3UTR9DXk]]></nonce_str>
	 * <sign><![CDATA[3E47ABF814F89CA53D8587AFBEB5C71B]]></sign>
	 * <result_code><![CDATA[SUCCESS]]></result_code>
	 * <prepay_id><![CDATA[wx20160224132612d8e35aa0250132143861]]></prepay_id>
	 * <trade_type><![CDATA[JSAPI]]></trade_type> </xml>
	 */
	public String getUnifiedOrderXml(WxPaySendData data) throws IOException {
		String xml = "";
		Element xmlTag = new Element("xml");
		xmlTag.addContent(new Element("appid").setText(data.getAppid()));
		xmlTag.addContent(new Element("openid").setText(data.getOpenid()));
		xmlTag.addContent(new Element("attach").setText(data.getAttach()));
		xmlTag.addContent(new Element("body").setText(data.getBody()));
		xmlTag.addContent(new Element("mch_id").setText(data.getMch_id()));
		xmlTag.addContent(new Element("nonce_str").setText(data.getNonce_str()));
		xmlTag.addContent(new Element("notify_url").setText(data.getNotify_url()));
		xmlTag.addContent(new Element("out_trade_no").setText(data.getOut_trade_no()));
		xmlTag.addContent(new Element("spbill_create_ip").setText(data.getSpbill_create_ip()));
		xmlTag.addContent(new Element("total_fee").setText((data.getTotal_fee() + "")));
		xmlTag.addContent(new Element("trade_type").setText(data.getTrade_type()));
		xmlTag.addContent(new Element("device_info").setText(data.getDevice_info()));
		xmlTag.addContent(new Element("sign").setText(data.getSign()));
		Document doc = new Document(xmlTag);
		xml = doc2str(doc);
		return xml;
	}
	public String getUrl(String url, String param) {
		String res = "";
		try {
			URL urlGet = new URL(url);
			HttpURLConnection httpGet = (HttpURLConnection) urlGet.openConnection();
			httpGet.setRequestMethod("GET"); // 必须是get方式请求
			httpGet.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpGet.setDoOutput(true);
			httpGet.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			httpGet.connect();
			// 写入输入参数
			OutputStream op = httpGet.getOutputStream();
			OutputStreamWriter opw = new OutputStreamWriter(op);
			opw.write(param.toString().toCharArray(), 0, param.toString().length());
			opw.flush();
			opw.close();
			// 获取输出结果
			InputStream is = httpGet.getInputStream();
			int size = is.available();
			byte[] isBytes = new byte[size];
			is.read(isBytes);
			res = new String(isBytes, "UTF-8");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public String getPrepayId(String xml) throws JDOMException, IOException {
		String str = "";
		StringReader xmlReader = new StringReader(xml);
		InputSource xmlSource = new InputSource(xmlReader);
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(xmlSource);
		org.jdom.Element elt = doc.getRootElement();
		org.jdom.Element body = elt.getChild("prepay_id");
		if(body!=null){
			showDebug(body.getName() + "---------->" + body.getText());
			str = body.getText();
		}
		return str;
	}

	public String getPaySign(String appId, String timeStamp, String nonceStr, String packageStr, String signType) {
		String sign = "";
		WxPaySendData payData = new WxPaySendData();
		payData.setAppid(appId);
		payData.setTimeStamp(timeStamp);
		payData.setNonce_str(nonceStr);
		payData.setPackageStr(packageStr);
		payData.setSignType(signType);
		WxSign.createPaySign(payData, tradeKey);
		sign = payData.getSign();
		return sign;
	}
	public String doc2str(Document doc) throws IOException {
		String xml = "";
		// XML转字符串
		Format format = Format.getCompactFormat();
		// format.setEncoding("UTF-8"); //设置XML文件的字符为UTF-8
		format.setIndent("	");// 设置缩进
		XMLOutputter xmlout = new XMLOutputter(format);// 定义输出 ,在元素后换行，每一层元素缩排四格
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		xmlout.output(doc, bo);
		xml = bo.toString();
		return xml;
	}
}

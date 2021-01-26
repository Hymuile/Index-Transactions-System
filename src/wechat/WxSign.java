package wechat;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import utility.MD5Util;

public class WxSign {
	private static String characterEncoding = "UTF-8";

	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<Object, Object> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		System.out.println("MD5转码之前："+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(),characterEncoding).toUpperCase();
		return sign;
	}

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)),characterEncoding).toUpperCase();
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	public static String createUnifiedOrderSign(WxPaySendData data,String key){
		//生成sign
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", data.getAppid()); 
        parameters.put("openid", data.getOpenid());
        parameters.put("attach", data.getAttach());
        parameters.put("body", data.getBody());
        parameters.put("mch_id", data.getMch_id());
        parameters.put("nonce_str", data.getNonce_str());
        parameters.put("notify_url", data.getNotify_url());
        parameters.put("out_trade_no", data.getOut_trade_no());
        parameters.put("spbill_create_ip", data.getSpbill_create_ip());
        parameters.put("total_fee", data.getTotal_fee());
        parameters.put("trade_type", data.getTrade_type());
        parameters.put("device_info", data.getDevice_info());
		String paySign = WxSign.createSign(parameters,key);
		System.out.println("生成的统一订单签名UnifiedOrderSIGN:"+paySign);
		data.setSign(paySign);
		return paySign;
	}
	public static String createPaySign(WxPaySendData data,String key){
		//生成sign
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appId", data.getAppid()); 
        parameters.put("timeStamp", data.getTimeStamp());
        parameters.put("nonceStr", data.getNonce_str());
        parameters.put("package", data.getPackageStr());
        parameters.put("signType", data.getSignType());
		String paySign = WxSign.createSign(parameters,key);
		System.out.println("生成的支付签名PaySIGN:"+paySign);
		data.setSign(paySign);
		return paySign;
	}
}

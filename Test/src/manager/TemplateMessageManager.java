package manager;

import org.junit.Test;

import service.WxService;
import util.Util;

public class TemplateMessageManager {

	// ������ҵ
	@Test
	public void set() {
		String at = WxService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + at;
		String data = "{\r\n" + "    \"industry_id1\":\"1\",\r\n" + "    \"industry_id2\":\"4\"\r\n" + "}\r\n" + "";
		String result = Util.post(url, data);
		System.out.println(result);
	}

	// ��ȡ��ҵ
	@Test
	public void get() {
		String at = WxService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + at;
		String result = Util.get(url);
		System.out.println(result);
	}

	// ����ģ����Ϣ
	@Test
	public void sendTemplateMessage() {
		String at = WxService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + at;
		String data = "  {\r\n" + "          \"touser\":\"oHmR45x7e1m4ucw7pvHD_Qsvee4U\",\r\n"
				+ "          \"template_id\":\"BBs8YCXDfIKVdUSzxfr7rJ749dDQ1Pv5HmHkrAoj2nI\",       \r\n"
				+ "          \"data\":{\r\n" + "                  \"first\": {\r\n"
				+ "                      \"value\":\"���Ķ���1002 ���˿�ɹ���\",\r\n"
				+ "                      \"color\":\"#173177\"\r\n" + "                  },\r\n"
				+ "                  \"storeName\":{\r\n" + "                      \"value\":\"��è����\",\r\n"
				+ "                      \"color\":\"#173177\"\r\n" + "                  },\r\n"
				+ "                  \"orderId\": {\r\n" + "                      \"value\":\"1002\",\r\n"
				+ "                      \"color\":\"#173177\"\r\n" + "                  },\r\n"
				+ "                  \"orderType\": {\r\n" + "                      \"value\":\"����\",\r\n"
				+ "                      \"color\":\"#173177\"\r\n" + "                  },\r\n"
				+ "                  \"remark\":{\r\n" + "                      \"value\":\"�˿��123456789.00Ԫ\",\r\n"
				+ "                      \"color\":\"#ff0000\"\r\n" + "                  }\r\n" + "          }\r\n"
				+ "      }";
		String result = Util.post(url, data);
		System.out.println(result);
	}

}

package manager;

import org.junit.Test;

import service.WxService;
import util.Util;

public class TemplateMessageManager {

	// 设置行业
	@Test
	public void set() {
		String at = WxService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + at;
		String data="{\r\n" + 
				"    \"industry_id1\":\"1\",\r\n" + 
				"    \"industry_id2\":\"4\"\r\n" + 
				"}\r\n" + 
				"";
		String result=Util.post(url, data);
		System.out.println(result);
	}
	
	// 获取行业
	@Test
	public void get() {
		String at = WxService.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + at;
		String result=Util.get(url);
		System.out.println(result);
	}

}

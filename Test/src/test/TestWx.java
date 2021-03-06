package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baidu.aip.ocr.AipOcr;
import com.thoughtworks.xstream.XStream;

import entity.AbstractButton;
import entity.Button;
import entity.ClickButton;
import entity.PhotoOrAlbumButton;
import entity.SubButton;
import entity.TextMessage;
import entity.ViewButton;
import net.sf.json.JSONObject;
import service.WxService;

public class TestWx {

	// 设置APPID/AK/SK
	public static final String APP_ID = "22487802";
	public static final String API_KEY = "7wMFnw4nDUmMPAibuUgQ02Kr";
	public static final String SECRET_KEY = "ITBgzkxW2oszWS6LVSpoyilPPZb6NEDi";

	@Test
	public void testQrCode() {
		String ticket = WxService.getQrCodeTicket();
		System.out.println(ticket);
	}

	@Test
	public void test() {
		System.out.println(WxService.getAccessToken());
	}

	// 添加临时素材
	@Test
	public void testUpload() {
		String file = "C:\\Users\\Admin\\Desktop\\2.png";
		String result = WxService.upload(file, "image");
		System.out.println(result);
	}

	@Test
	public void testPic() {
		// 初始化一个AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
		// client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
		// client.setSocketProxy("proxy_host", proxy_port); // 设置socket代理

		// 可选：设置log4j日志输出格式，若不设置，则使用默认配置
		// 也可以直接通过jvm启动参数设置此环境变量
		// System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		// 调用接口
		String path = "C:\\Users\\Admin\\Desktop\\2.png";
		org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
		System.out.println(res.toString(2));

	}

	@Test
	public void testButton() {
		// 创建菜单对象
		Button btn = new Button();
		// 第一个一级菜单
		btn.getButton().add(new ClickButton("一级点击", "1"));
		// 第二个一级菜单
		btn.getButton().add(new ViewButton("一级跳转", "http://www.baidu.com"));
		// 创建第三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		// 为第三个一级菜单增加子菜单
		sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
		// 加入第三个一级菜单
		btn.getButton().add(sb);

		// 转换为Json
		JSONObject jsonObject = JSONObject.fromObject(btn);
		System.out.println(jsonObject.toString());

//{"button":[
//{"key":"1","name":"一级点击","type":"1"},
//{"name":"一级跳转","type":"view","url":"http://www.baidu.com"},
//{"name":"有子菜单","sub_button":[
//{"key":"31","name":"传图","sub_button":[],"type":"pic_photo_or_album"},
//{"key":"","name":"点击","type":"32"},
//{"name":"网易新闻","type":"view","url":"http://news.163.com"}]}]}

	}

	@Test
	public void testToken() {
		System.out.println(WxService.getAccessToken());
		System.out.println(WxService.getAccessToken());
	}

	@Test
	public void testMsg() {
		Map<String, String> map = new HashMap<>();
		map.put("ToUserName", "to");
		map.put("FromUserName", "from");
		map.put("MsgType", "type");
		TextMessage tm = new TextMessage(map, "你好");

		// System.out.println(tm);

		XStream stream = new XStream();
		// 将 <entity.TextMessage> 替换为 <xml>,替换别名
		stream.processAnnotations(TextMessage.class);
		String xml = stream.toXML(tm);
		System.out.println(xml);
	}
}

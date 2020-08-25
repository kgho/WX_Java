package service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import entity.BaseMessage;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.VideoMessage;
import entity.VoiceMessage;
import util.Util;

public class WxService {
	private static final String TOKEN = "test";
	private static final String APPKEY = "a5f61cd9833a23816d252e6cd86309cb";

	/**
	 * 验证签名
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public static boolean check(String timestamp, String nonce, String signature) {

		// TODO Auto-generated method stub1）将token、timestamp、nonce三个参数进行字典序排序
		String[] strs = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(strs);

		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		String str = strs[0] + strs[1] + strs[2];
		String mysig = sha1(str);
		System.out.println(mysig);
		System.out.println(signature);

		// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信urn false;

		return mysig.equalsIgnoreCase(signature);
	}

	private static String sha1(String src) {

		try {
			// 获取一个加密对象
			MessageDigest md = MessageDigest.getInstance("sha1");
			// 加密
			byte[] digest = md.digest(src.getBytes());
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			StringBuilder sb = new StringBuilder();
			// 处理加密结果
			for (byte b : digest) {
				sb.append(chars[(b >> 4) & 15]);
				sb.append(chars[b & 15]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解析xml数据包
	 * 
	 * @param is
	 * @return
	 */
	public static Map<String, String> parseRequest(InputStream is) {
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		try {
			// 读取输入流，获取文档对象
			Document document = reader.read(is);
			// 根据文档对象获取根节点
			org.dom4j.Element root = document.getRootElement();
			// 获取根节点的所有子节点
			List<org.dom4j.Element> elements = root.elements();
			for (org.dom4j.Element e : elements) {
				map.put(e.getName(), e.getStringValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 用于处理所有的事件和消息的回复
	 * 
	 * @param requestMap
	 * @return 返回的数据包
	 */
	public static String getResponse(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String msgType = requestMap.get("MsgType");
		switch (msgType) {
		// 处理文本消息
		case "text":
			msg = dealTextMessage(requestMap);
			break;
		case "image":

			break;
		case "voice":

			break;
		case "video":

			break;
		case "shortvideo":

			break;
		case "location":

			break;
		case "link":

			break;
		}

		if (msg != null) {
			// 把消息对象处理为xml数据包
			return beanToXml(msg);
		}
		return null;

	}

	private static String beanToXml(BaseMessage msg) {
		XStream stream = new XStream();
		// 将 <entity.TextMessage> 替换为 <xml>,替换别名
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		String xml = stream.toXML(msg);
		return xml;
	}

	private static BaseMessage dealTextMessage(Map<String, String> requestMap) {
		// 用户发来的内容
		String msg = requestMap.get("Content");
		// 调用方法返回聊天的内容
		String resp = chat(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		return tm;
	}

	private static String chat(String msg) {

		int ran1 = (int) (Math.random()*(9)+1); 
		int ran2 = (int) (Math.random()*(19)+1);
		
		String result = null;
		
		//String url = "http://japi.juhe.cn/joke/content/list.from";// 请求接口地址
		
		String url = "http://v.juhe.cn/joke/content/list.php";// 请求接口地址
			
		Map params = new HashMap();// 请求参数
		params.put("sort", "asc");// 类型，desc:指定时间之前发布的，asc:指定时间之后发布的
		params.put("page", ran1);// 当前页数,默认1
		params.put("pagesize", ran1);// 每次返回条数,默认1,最大20
		params.put("time", "1418816972");// 时间戳（10位），如：1418816972
		params.put("key", APPKEY);// 您申请的key

		try {
			result = Util.net(url, params, "GET");
			System.out.println("GET");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

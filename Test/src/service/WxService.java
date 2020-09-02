package service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.baidu.aip.ocr.AipOcr;
import com.thoughtworks.xstream.XStream;

import entity.AccessToken;
import entity.Article;
import entity.BaseMessage;
import entity.ImageMessage;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.VideoMessage;
import entity.VoiceMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Util;

public class WxService {
	private static final String TOKEN = "test";
	private static final String APPKEY = "a5f61cd9833a23816d252e6cd86309cb";

	// ΢�Ź��ں�
	private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String APPID = "wx6f2f69bf193c2c3f";
	private static final String APPSECRET = "3470f23a5f8d75c24762793851e596ee";

	// �ٶ�AI
	public static final String APP_ID = "22487802";
	public static final String API_KEY = "7wMFnw4nDUmMPAibuUgQ02Kr";
	public static final String SECRET_KEY = "ITBgzkxW2oszWS6LVSpoyilPPZb6NEDi";

	// ���� token
	private static AccessToken at;

	// ��ȡtoken
	private static void getToken() {
		String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		String tokenStr = Util.get(url);
		// System.out.println(tokenStr);
		JSONObject jsonObject = JSONObject.fromObject(tokenStr);
		String token = jsonObject.getString("access_token");
		String expireIn = jsonObject.getString("expires_in");
		// ���� token ����
		at = new AccessToken(token, expireIn);
	}

	// �ⲿ��ȡ token ����
	public static String getAccessToken() {
		// ��� token �����˾����»�ȡ
		if (at == null || at.isExpired()) {
			getToken();
		}
		return at.getAccessToken();
	}

	/**
	 * ��֤ǩ��
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public static boolean check(String timestamp, String nonce, String signature) {

		// TODO Auto-generated method stub1����token��timestamp��nonce�������������ֵ�������
		String[] strs = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(strs);

		// 2�������������ַ���ƴ�ӳ�һ���ַ�������sha1����
		String str = strs[0] + strs[1] + strs[2];
		String mysig = sha1(str);
		System.out.println(mysig);
		System.out.println(signature);

		// 3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��urn false;

		return mysig.equalsIgnoreCase(signature);
	}

	private static String sha1(String src) {

		try {
			// ��ȡһ�����ܶ���
			MessageDigest md = MessageDigest.getInstance("sha1");
			// ����
			byte[] digest = md.digest(src.getBytes());
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			StringBuilder sb = new StringBuilder();
			// ������ܽ��
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
	 * ����xml���ݰ�
	 * 
	 * @param is
	 * @return
	 */
	public static Map<String, String> parseRequest(InputStream is) {
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		try {
			// ��ȡ����������ȡ�ĵ�����
			Document document = reader.read(is);
			// �����ĵ������ȡ���ڵ�
			org.dom4j.Element root = document.getRootElement();
			// ��ȡ���ڵ�������ӽڵ�
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
	 * ���ڴ������е��¼�����Ϣ�Ļظ�
	 * 
	 * @param requestMap
	 * @return ���ص����ݰ�
	 */
	public static String getResponse(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String msgType = requestMap.get("MsgType");
		switch (msgType) {
		// �����ı���Ϣ
		case "text":
			msg = dealTextMessage(requestMap);
			break;
		case "image":
			msg = dealImage(requestMap);
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
		case "event":
			msg = dealEvent(requestMap);
			break;
		default:

			break;
		}

		if (msg != null) {
			// ����Ϣ������Ϊxml���ݰ�
			return beanToXml(msg);
		}
		return null;

	}

	private static BaseMessage dealImage(Map<String, String> requestMap) {

		// ��ʼ��һ��AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// ��ѡ�������������Ӳ���
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// ���ýӿ�
		String path = requestMap.get("PicUrl");

		// ����ͼƬ
		// org.json.JSONObject res = client.basicGeneral(path, new HashMap<String,
		// String>());
		// ����ͼƬ
		org.json.JSONObject res = client.generalUrl(path, new HashMap<String, String>());

		System.out.println(res.toString(2));

		String json = res.toString();
		// תΪjsonObject
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONArray jsonArray = jsonObject.getJSONArray("words_result");
		Iterator<JSONObject> it = jsonArray.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			JSONObject next = it.next();
			sb.append(next.getString("words"));
		}

		return new TextMessage(requestMap, sb.toString());
	}

	// �����¼�����
	private static BaseMessage dealEvent(Map<String, String> requestMap) {
		String event = requestMap.get("Event");
		switch (event) {
		case "CLICK":
			return dealClick(requestMap);
		case "VIEW":
			return dealView(requestMap);
		default:

			break;
		}
		return null;
	}

	// ����View ���Ͱ�ť���
	private static BaseMessage dealView(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	// ����Click ���Ͱ�ť���
	private static BaseMessage dealClick(Map<String, String> requestMap) {
		String key = requestMap.get("EventKey");
		switch (key) {
		case "1":
			return new TextMessage(requestMap, "�����˵�һ��һ���˵�");
		case "32":
			break;
		default:
			break;
		}
		return null;
	}

	private static String beanToXml(BaseMessage msg) {
		XStream stream = new XStream();
		// �� <entity.TextMessage> �滻Ϊ <xml>,�滻����
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
		// �û�����������
		String msg = requestMap.get("Content");

		if (msg.equals("ͼ��")) {
			List<Article> articles = new ArrayList<>();
			articles.add(new Article("����", "����",
					"http://mmbiz.qpic.cn/mmbiz_jpg/OYEhXYWLPN8FhVTmZlI3lIWctnZGDjbjGVUNXREC1ycjz39zgQqZC3S76ibrQvsJuoC6Wv81MxWHiaAU2B9Cyv7w/0",
					"http://kgh.vipgz1.idcfengye.com/Test/"));
			NewsMessage nm = new NewsMessage(requestMap, articles);
			return nm;
		}

		// ���÷����������������
		String resp = chat(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		return tm;
	}

	private static String chat(String msg) {

		int ran1 = (int) (Math.random() * (9) + 1);

		String result = null;

		// String url = "http://japi.juhe.cn/joke/content/list.from";// ����ӿڵ�ַ

		String url = "http://v.juhe.cn/joke/content/list.php";// ����ӿڵ�ַ

		Map params = new HashMap();// �������
		params.put("sort", "asc");// ���ͣ�desc:ָ��ʱ��֮ǰ�����ģ�asc:ָ��ʱ��֮�󷢲���
		params.put("page", ran1);// ��ǰҳ��,Ĭ��1
		params.put("pagesize", 1);// ÿ�η�������,Ĭ��1,���20
		params.put("time", 1418816979);// ʱ�����10λ�����磺1418816972
		params.put("key", APPKEY);// �������key

		try {
			result = Util.net(url, params, "GET");
			System.out.println("GET");
			// ��ӡapi���ص�����
			// System.out.println(result);

			// ����Json
			JSONObject jsonObject = JSONObject.fromObject(result);
			// ȡ��error_code
			int code = jsonObject.getInt("error_code");
			if (code != 0) {
				return null;
			}
			// ȡ�����ص���Ϣ����
			JSONObject jsonObj = jsonObject.getJSONObject("result");

			JSONArray jsonArr = jsonObj.getJSONArray("data");
			String resp = jsonArr.getJSONObject(0).getString("content");

			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

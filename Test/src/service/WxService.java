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
			// ����Ϣ������Ϊxml���ݰ�
			return beanToXml(msg);
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
		// ���÷����������������
		String resp = chat(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		return tm;
	}

	private static String chat(String msg) {

		int ran1 = (int) (Math.random()*(9)+1); 
		int ran2 = (int) (Math.random()*(19)+1);
		
		String result = null;
		
		//String url = "http://japi.juhe.cn/joke/content/list.from";// ����ӿڵ�ַ
		
		String url = "http://v.juhe.cn/joke/content/list.php";// ����ӿڵ�ַ
			
		Map params = new HashMap();// �������
		params.put("sort", "asc");// ���ͣ�desc:ָ��ʱ��֮ǰ�����ģ�asc:ָ��ʱ��֮�󷢲���
		params.put("page", ran1);// ��ǰҳ��,Ĭ��1
		params.put("pagesize", ran1);// ÿ�η�������,Ĭ��1,���20
		params.put("time", "1418816972");// ʱ�����10λ�����磺1418816972
		params.put("key", APPKEY);// �������key

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

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

	 //����APPID/AK/SK
    public static final String APP_ID = "22487802";
    public static final String API_KEY = "7wMFnw4nDUmMPAibuUgQ02Kr";
    public static final String SECRET_KEY = "ITBgzkxW2oszWS6LVSpoyilPPZb6NEDi";
	
	@Test
	public void testPic() {
		// ��ʼ��һ��AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// ��ѡ�������������Ӳ���
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
		//client.setHttpProxy("proxy_host", proxy_port); // ����http����
		//client.setSocketProxy("proxy_host", proxy_port); // ����socket����

		// ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
		// Ҳ����ֱ��ͨ��jvm�����������ô˻�������
		// System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		// ���ýӿ�
		String path = "C:\\Users\\Admin\\Desktop\\2.png";
		org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
		System.out.println(res.toString(2));

	}

	@Test
	public void testButton() {
		// �����˵�����
		Button btn = new Button();
		// ��һ��һ���˵�
		btn.getButton().add(new ClickButton("һ�����", "1"));
		// �ڶ���һ���˵�
		btn.getButton().add(new ViewButton("һ����ת", "http://www.baidu.com"));
		// ����������һ���˵�
		SubButton sb = new SubButton("���Ӳ˵�");
		// Ϊ������һ���˵������Ӳ˵�
		sb.getSub_button().add(new PhotoOrAlbumButton("��ͼ", "31"));
		sb.getSub_button().add(new ClickButton("���", "32"));
		sb.getSub_button().add(new ViewButton("��������", "http://news.163.com"));
		// ���������һ���˵�
		btn.getButton().add(sb);

		// ת��ΪJson
		JSONObject jsonObject = JSONObject.fromObject(btn);
		System.out.println(jsonObject.toString());

//{"button":[
//{"key":"1","name":"һ�����","type":"1"},
//{"name":"һ����ת","type":"view","url":"http://www.baidu.com"},
//{"name":"���Ӳ˵�","sub_button":[
//{"key":"31","name":"��ͼ","sub_button":[],"type":"pic_photo_or_album"},
//{"key":"","name":"���","type":"32"},
//{"name":"��������","type":"view","url":"http://news.163.com"}]}]}

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
		TextMessage tm = new TextMessage(map, "���");

		// System.out.println(tm);

		XStream stream = new XStream();
		// �� <entity.TextMessage> �滻Ϊ <xml>,�滻����
		stream.processAnnotations(TextMessage.class);
		String xml = stream.toXML(tm);
		System.out.println(xml);
	}
}

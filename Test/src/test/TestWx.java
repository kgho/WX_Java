package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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

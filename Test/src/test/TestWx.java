package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import entity.TextMessage;

public class TestWx {

	@Test
	public void testMsg() {
		Map<String, String> map = new HashMap<>();
		map.put("ToUserName", "to");
		map.put("FromUserName", "from");
		map.put("MsgType", "type");
		TextMessage tm = new TextMessage(map, "ÄãºÃ");

		// System.out.println(tm);

		XStream stream = new XStream();
		// ½« <entity.TextMessage> Ìæ»»Îª <xml>
		stream.processAnnotations(TextMessage.class);
		String xml = stream.toXML(tm);
		System.out.println(xml);
	}
}

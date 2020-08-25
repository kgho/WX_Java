package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import entity.TextMessage;

public class TestWx {

	@Test
	public void testMsg() {
		Map<String, String> map = new HashMap<>();
		map.put("ToUserName", "to");
		map.put("FromUserName", "from");
		map.put("MsgType", "type");
		TextMessage tm = new TextMessage(map, "ÄãºÃ");
		
		System.out.println(tm);
	}
}

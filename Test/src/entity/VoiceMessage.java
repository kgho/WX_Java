package entity;

import java.util.Map;

public class VoiceMessage extends BaseMessage {

	private String mediaId;

	public VoiceMessage(Map<String, String> requestMap, String mediaId) {
		super(requestMap);
		this.setMsgType("voice");
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}

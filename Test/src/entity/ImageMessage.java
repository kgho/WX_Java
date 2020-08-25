package entity;

import java.util.Map;

public class ImageMessage extends BaseMessage {

	private String mediaId;

	public ImageMessage(Map<String, String> requestMap, String mediaId) {
		super(requestMap);
		this.setMsgType("image");
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}

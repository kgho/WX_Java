package entity;

public class AccessToken {

	private String accessToken;
	private long expireTime;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public AccessToken(String accessToken, String expireIn) {
		super();
		this.accessToken = accessToken;
		// 计算过期时间 = 当前时间+还有多少秒过期
		expireTime = System.currentTimeMillis() + Integer.parseInt(expireIn) * 1000;
	}

	// 判断 token 是否过期
	public boolean isExpired() {
		return System.currentTimeMillis() > expireTime;
	}
}

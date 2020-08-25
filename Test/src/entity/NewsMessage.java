package entity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class NewsMessage extends BaseMessage{

	private String articleCount;
	private List<Article> articles=new ArrayList<>();
	
	public NewsMessage(Map<String, String> requestMap, String articleCount, List<Article> articles) {
		super(requestMap);
		this.setMsgType("news");
		this.articleCount = articleCount;
		this.articles = articles;
	}
	
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	
}

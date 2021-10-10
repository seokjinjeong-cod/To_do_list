package co.yedam.todolist;

public class Article {

	String no;
	String content;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Article [id=" + no + ", content=" + content + "]";
	}
	
	
}

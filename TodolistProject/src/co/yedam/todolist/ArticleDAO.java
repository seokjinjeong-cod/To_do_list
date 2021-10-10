package co.yedam.todolist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DAO;

public class ArticleDAO extends DAO {

	private static ArticleDAO singleton = new ArticleDAO();

	private ArticleDAO() {

	}

	public static ArticleDAO getInstance() {
		return singleton;
	}

	// 글목록
	public List<Article> getArticleList() {
		connect();
		List<Article> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from article order by 1 asc");
			while (rs.next()) {
				Article cmt = new Article();
				cmt.setNo(rs.getString("no"));
				cmt.setContent(rs.getString("content"));
				list.add(cmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 글입력
	public Article insertArticle(Article article) {
		connect();
		int currId = 0;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select value from id_article where name = 'ARTICLE'");
			if (rs.next()) {
				currId = rs.getInt("value");
			}
			currId++;

			psmt = conn.prepareStatement("insert into article values(?, ?)");
			psmt.setInt(1, currId);
			psmt.setString(2, article.getContent());
//			System.out.println(article.getContent());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력");

			psmt = conn.prepareStatement("update id_article set value = ? where name = 'ARTICLE'");
			psmt.setInt(1, currId);
			psmt.executeUpdate();
			if (rs.next()) {
				currId = rs.getInt("no");
//				System.out.println(currId);
			}

			article.setNo(String.valueOf(currId));
			return article;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			disconnect();
		}
	}

	// 글삭제
	public String deleteArticle(String no) {
		connect();
		int currId = 0;
//		try {
//			conn.setAutoCommit(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select value from id_article where name = 'ARTICLE'");
//			if (rs.next()) {
//				currId = rs.getInt("value");
//			}
//			currId--;
			
			psmt = conn.prepareStatement("delete from article where no = ?");
			psmt.setString(1, no);
			int r = psmt.executeUpdate();
			System.out.println(r + "건 삭제.");
			
//			psmt = conn.prepareStatement("update id_article set value = ? where name = 'ARTICLE'");
//			psmt.setInt(1, currId);
//			psmt.executeUpdate();
//			if (rs.next()) {
//				currId = rs.getInt("no");
//				System.out.println(currId);
//			}

			return no;

		} catch (SQLException e) {
			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			return null;
		} finally {
			disconnect();
		}

	}
}

package co.yedam.todolist;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArticleServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create();
		
		String cmd = request.getParameter("cmd");
		ArticleDAO dao = ArticleDAO.getInstance();
		
		if(cmd.equals("list")) {
			List<Article> list = dao.getArticleList();
			out.println(gson.toJson(list));
			
		} else if(cmd.equals("add")) {
			String content = request.getParameter("content");
			System.out.println(content);
			Article atc = new Article();
			
			atc.setContent(content);
			dao.insertArticle(atc);
			
			out.println(gson.toJson(atc));
		} else if(cmd.equals("del")) {
			String no = request.getParameter("deleteNo");
			System.out.println(no);
			if(dao.deleteArticle(no) == null) {
				//{"retCode":"fail"}{"retCode":"success"}
				out.println("{\"retCode\":\"fail\"}");
				return;
			} else {
				out.println("{\"retCode\":\"success\"}");
			}
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

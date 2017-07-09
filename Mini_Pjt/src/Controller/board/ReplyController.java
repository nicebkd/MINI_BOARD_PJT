package Controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.board.DAO.ReplyDAO;
import Model.board.DAO.ReplyDAOImpl;
import Model.board.Vo.ReplyVo;
import Model.member.Vo.MemberVo;

/**
 * Servlet implementation class ReplyController
 */
@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("utf-8");
		ReplyDAO dao = ReplyDAOImpl.getInstance();
		ReplyVo vo = null;
		String command = request.getRequestURI();
	
		if(command.indexOf(request.getContextPath())==0){
			command = command.substring(request.getContextPath().length());
		}
		System.out.println("URI 커맨더 : "+command);
		String viewPage = null;
		String prefix ="/views/board/";
		try{	
			//게시판 리스트
			if(command.equals("/reply/insert.do")){
				int bno = Integer.parseInt(request.getParameter("bno"));
				String srno = request.getParameter("rno");
				int ref =0;
				int re_step =0; 
				int re_level=0;
				
				int rno;
				if(srno ==null){
					rno=0;
				}else{
					rno = Integer.parseInt(srno);
					ref = Integer.parseInt(request.getParameter("ref"));
					re_step = Integer.parseInt(request.getParameter("re_step"));
					re_level = Integer.parseInt(request.getParameter("re_level"));
				}
				System.out.println("bno~~~~~~~" + bno);
				System.out.println("rno~~~~~~~~~~ : "+rno);
				System.out.println("ref~~~~~~~~" + ref);
				System.out.println("re_step~~~~~~~~" + re_step);
				System.out.println("re_level~~~~~~~~" + re_level);
				
				String replytext = request.getParameter("replytext");
				/*int rno = Integer.parseInt(request.getParameter("rno"));
				System.out.println("rno~" + rno);
				*/HttpSession session = request.getSession();
				MemberVo dto = new MemberVo();
				dto = (MemberVo) session.getAttribute("user");
				String replyer = dto.getId();
				
				int result = -1;
				vo = new ReplyVo();
				
				vo.setRno(rno);
				vo.setRef(ref);
				vo.setRe_step(re_step);
				vo.setRe_level(re_level);
				vo.setReplyer(replyer);
				vo.setBno(bno);
				vo.setReplytext(replytext);
				result = dao.insertRply(vo);
			}else if(command.equals("/reply/list.do")){
				int bno = Integer.parseInt(request.getParameter("bno"));
				ArrayList<ReplyVo> list = new ArrayList<>();
				list = dao.list(bno);
				request.setAttribute("list",list);
				viewPage=prefix+"replylist.jsp";
			}else if(command.equals("/reply/delete.do")){
				int rno = Integer.parseInt(request.getParameter("rno"));
				int result = -1;
				result = dao.delete(rno);
				PrintWriter out = response.getWriter();
				out.println(result);
				out.close();
			}
		}catch (Throwable e) {
			throw new ServletException(e);
		}
		if(viewPage !=null){
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
			
		}
		
	}

}

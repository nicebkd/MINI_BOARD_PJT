package Controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.member.DAO.MemberDAO;
import Model.member.DAO.MemberDAOImpl;
import Model.member.Vo.MemberVo;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
		
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//한글처리(서블릿 제외 나머지는 web.xml filter에서 한글처리)
		response.setCharacterEncoding("utf-8");
		//dao
		MemberDAO dao = MemberDAOImpl.getInstance();
		//서블릿에서 out 객체 생성
		PrintWriter out = response.getWriter();
		//dto 
		MemberVo dto= null;
		//command(uri로 명령어 받아와서 밑에 if문에서 처리)
		String command = request.getRequestURI();
		
		if(command.indexOf(request.getContextPath())==0){
			command = command.substring(request.getContextPath().length());
		}
		System.out.println("URI 커맨더 : "+command);
		//이동할 페이지 (RequestDispacher forward)
		String viewPage = null;
		//이동하는 페이지 경로 앞자리(멤버 기준)
		String prefix ="/views/member/";
		try{	
			//회원가입 페이지로 이동
			if(command.equals("/member/joinForm.do")){
				viewPage = prefix+"joinForm.jsp";
			//회원가입 로직
			}else if(command.equals("/member/join.do")){
				int result = -1;
				dto = new MemberVo();
				
				String id=request.getParameter("id");
				String password=request.getParameter("pw");
				String name =request.getParameter("name");
				String email=request.getParameter("email");
				
				dto.setId(id);
				dto.setPassword(password);
				dto.setName(name);
				dto.setEmail(email);
				result = dao.insertMember(dto);
				
				//foward 형태로 viewPage로 넘어가기때문에 데이터를 가지고 넘어간다.
				request.setAttribute("result",result);
				//넘어가는 페이지(result값을 가져감)
				viewPage=prefix+"joinResult.jsp";
			
			//아이디 체크 부분(ajax)
			}else if(command.equals("/member/idcheck.do")){
				String id = request.getParameter("id");
				
				int reuslt = -1;
				reuslt =  dao.id_check(id);
				System.out.println("아이디 체크" + reuslt);
				if(reuslt==0){
					out.print("success");
				}else{
					out.print("fail");
				}
				
				//로그인 페이지로 이동
				}else if(command.equals("/member/loginForm.do")){	
					viewPage = prefix+"/loginForm.jsp";
					
				}else if(command.equals("/member/loginPro.do")){
					
					String id=request.getParameter("id");
					String password=request.getParameter("pw");		
					
					dto = new MemberVo();				
					
					dto	=dao.login(id, password);//login에서 MemberVo형태로 값을 받아 왔기 때문에 사용하려면 dto에 담아줘야함

					request.setAttribute("user", dto);

					viewPage = prefix+"/loginResult.jsp";
				//로그아웃	
				}else if(command.equals("/member/logout.do")){
					HttpSession session = request.getSession();
					session.invalidate();
					viewPage="/home.jsp";
				//회원정보 
				}else if(command.equals("/member/info.do")){
					String id =request.getParameter("id");
					dto = new MemberVo();
					dto = dao.info(id);
					request.setAttribute("info", dto);
					viewPage=prefix+"info.jsp";
				//회원정보 수정
				}else if(command.equals("/member/update.do")){
					String id =request.getParameter("id");
					dto = new MemberVo();
					dto = dao.info(id);
					request.setAttribute("info", dto);				
					viewPage=prefix+"update.jsp";
				//회원정보수정완료
				}else if(command.equals("/member/updateForm.do")){
					int result = -1;
					dto = new MemberVo();
		
					String id=request.getParameter("id");
					String password=request.getParameter("pw");
					String name =request.getParameter("name");
					String email=request.getParameter("email");
					//
					dto.setId(id);
					dto.setName(name);
					dto.setPassword(password);
					dto.setEmail(email);
					result = dao.update(dto);
					
					
					//foward 형태로 viewPage로 넘어가기때문에 데이터를 가지고 넘어간다.
					request.setAttribute("result",result);
					//넘어가는 페이지(result값을 가져감)
					viewPage=prefix+"updateResult.jsp";
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

package Controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Model.board.DAO.BoardDAO;
import Model.board.DAO.BoardDAOImpl;
import Model.board.Vo.BoardVo;
import util.Pager;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setCharacterEncoding("utf-8");
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		String command = request.getRequestURI();
		
		if(command.indexOf(request.getContextPath())==0){
			command = command.substring(request.getContextPath().length());
		}
		System.out.println("URI 커맨더 : "+command);
		//이동할 페이지 (RequestDispacher forward)
		String viewPage = null;
		//이동하는 페이지 경로 앞자리(게시판 기준)
		String prefix ="/views/board/";
		try{	
			//게시판 리스트
			if(command.equals("/board/list.do")){
				ArrayList<BoardVo> list = new ArrayList<>();
				
				String search_option = request.getParameter("search_option");
				String keyword = request.getParameter("keyword");
				if(search_option==null || search_option.equals("")){
					search_option="all";
				}
				if(keyword==null || keyword.equals("")){
					keyword="";
				}
								
				String page = request.getParameter("curPage");
				int curPage = 1;
				if(page ==null){
				page = "1";
				}
				curPage = Integer.parseInt(page);
				int count = dao.getCount(search_option, keyword);
				Pager pager = new Pager(count, curPage);
				int startPage = pager.getPageBegin();
				int endPage = pager.getPageEnd();
				list = dao.list(search_option,keyword,startPage,endPage);
				Map<String,Object> map = new HashMap<>();
				map.put("search_option", search_option);
				map.put("keyword", keyword);
				map.put("list",list);
				map.put("count",count);
				map.put("pager",pager);
				
				request.setAttribute("map",map);
				
				viewPage = prefix+"list.jsp";
			//글쓰기Form 이동
			}else if(command.equals("/board/writeForm.do")){
				
				viewPage = prefix+"write.jsp";
			//글쓰기 로직
			}else if(command.equals("/board/write.do")){
								
				String path="";
				String saveFolder="upload";
				String encType="utf-8";
				int file_size=0;
				int maxSize = 10*1024*1024;
				
				ServletContext context = this.getServletContext();
				
				path = context.getRealPath(saveFolder);
				File file = new File(path);
				
				if(!file.exists()){
					file.mkdirs();
				}
				
				System.out.println("realpath : " + path);
				
				MultipartRequest multi= null;
				
				try{
					multi = new MultipartRequest(request, path,
							maxSize,encType,new DefaultFileRenamePolicy());
				
					String writer =multi.getParameter("writer");
					String title =multi.getParameter("title");
					String content =multi.getParameter("content");
					
					String original_fil_name="";
					String stored_file_name="";
					Enumeration en = multi.getFileNames();
					
					BoardVo dto = new BoardVo();
					dto.setWriter(writer);
					dto.setTitle(title);
					dto.setContent(content);
					
					while(en.hasMoreElements()){
						String name = (String) en.nextElement();
						original_fil_name = multi.getOriginalFileName(name);
						stored_file_name = multi.getFilesystemName(name);
						
						file = multi.getFile(name);
						
						if(file!=null){
							file_size = (int) file.length();
							System.out.println("file 크기 : " + file.length());
						}
						
					}
					
/*				String writer = request.getParameter("writer");
				String title = request.getParameter("title");
				String content = request.getParameter("content");*/
				
				
				
				dto.setOriginal_file_name(original_fil_name);
				dto.setStored_file_name(stored_file_name);
				dao.insert(dto);
				/*dao.insertFileName(dto);*/
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				response.sendRedirect("/Mini_Pjt/board/list.do");
				
			}else if(command.equals("/board/view.do")){
				int num = Integer.parseInt(request.getParameter("num"));
				BoardVo dto = new BoardVo();
				dto=dao.view(num);
				request.setAttribute("dto",dto);
				viewPage = prefix+"view.jsp";
				
			}else if(command.equals("/board/download.do")){
				String path="";
				String saveFolder="upload";
				
				ServletContext context = this.getServletContext();
				
				int num=Integer.parseInt(request.getParameter("num"));
				String stored_file_name=dao.getFileName(num);
				path = context.getRealPath(saveFolder)+"\\"+stored_file_name;
				byte b[]=new byte[4096];
				FileInputStream fis = new FileInputStream(path);
				System.out.println("fis 까지 실행");
				String mimeType
				=getServletContext().getMimeType(path);
			if(mimeType==null){
				mimeType
					="application/octet-stream;charset=utf-8";
			}
			//첨부파일에 특수문자,한글이 포함될 경우의 처리
//스트링.getBytes("문자셋") 스트링을 바이트 배열로 변환			
//new String(바이트배열, "문자셋") 바이트배열을 스트링으로
// 8859_1 (iso-8859-1 서유럽언어 문자셋)			
			stored_file_name = 
new String(stored_file_name.getBytes("ms949"),"8859_1");
			//헤더 구성(첨부파일의 정보)
			response.setHeader("Content-Disposition", 
"attachment;filename="+stored_file_name);
			ServletOutputStream os
				=response.getOutputStream();
			int numRead;
			System.out.println("byte 값 : " + b.length);
			while(true){
				//서버에서 읽음
				numRead = fis.read(b,0,b.length);
				//더 이상 읽을 내용이 없으면
				if(numRead == -1) break;
				//클라이언트로 복사
				os.write(b, 0, numRead); 
			}
			//리소스 정리
			os.flush();
			os.close();
			os.close();
			}else if(command.equals("/board/boardUp.do")){//boardUpdate #1
				int num = Integer.parseInt(request.getParameter("num"));
				System.out.println("boardUp.do실행되나 num= "+num);
				
				BoardVo dto = new BoardVo();
				dto = dao.view(num);
				request.setAttribute("dto", dto);
				
				viewPage=prefix+"boardUpdate.jsp";

			}else if(command.equals("/board/pwChkUp.do")){//boardUpdate #2
				int num = Integer.parseInt(request.getParameter("num"));
				String curPage = request.getParameter("curPage");
				String id = request.getParameter("writer");
				String password = request.getParameter("inputPw");
				String title = request.getParameter("title");///////
				String content = request.getParameter("content");////////

				int pwC = dao.pwCheck(id, password);
				if(pwC==1){
					//command = "${path}/board/updateResult.do";
					System.out.println("title= "+title+", content="+content);
					int updateResult = dao.boardUpdate(num, title, content);
					if(updateResult>=1){
						System.out.println("게시글 수정 성공!");
						response.sendRedirect("/Mini_Pjt/board/view.do?num="+num+"&curPage="+curPage);
					}else{
						System.out.println("게시글 수정 실패");
						
						BoardVo dto = new BoardVo();
						dto = dao.view(num);
						request.setAttribute("dto", dto);
						
						viewPage=prefix+"boardUpdate.jsp";
					}
					
				}else{
					System.out.println("수정 비밀번호가 옳지 않음");
					
					BoardVo dto = new BoardVo();
					dto = dao.view(num);
					request.setAttribute("dto", dto);
					
					viewPage=prefix+"boardUpdate.jsp";
				}
				
			}else if(command.equals("/board/pwChkDel.do")){
				String curPage = request.getParameter("curPage");
				int num = Integer.parseInt(request.getParameter("num"));
				String id = request.getParameter("writer");
				String password = request.getParameter("password");
				System.out.println("pwChkDel의 num="+num+", writer="+id+", pass="+password);
				
				int result = dao.pwCheck(id, password);//리턴값 1: 비밀번호 맞음 / 2: 비밀번호 다름
				if(result==1){//비밀번호가 맞다면...
					int delResult = -1;
					delResult = dao.boardDel(num);
					if(delResult>=1){
						System.out.println("게시글 삭제 완료");
						//viewPage=prefix+"list.jsp";
						response.sendRedirect("/Mini_Pjt/board/list.do?curPage="+curPage);
					}else{
						BoardVo dto = new BoardVo();
						dto = dao.view(num);
						request.setAttribute("dto", dto);
						System.out.println("비밀번호는 맞으나.. 게시글 삭제 실패");
						viewPage=prefix+"view.jsp";
					}
				}else{//비밀번호pwCheck가 맞지 않다면...
					BoardVo dto = new BoardVo();
					dto = dao.view(num);
					request.setAttribute("dto", dto);
					
					System.out.println("비밀번호가 맞지않아! 넌 게시글을 삭제할 수 없지 result: "+result);
					System.out.println("result: "+result);
					viewPage=prefix+"view.jsp";
				}
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

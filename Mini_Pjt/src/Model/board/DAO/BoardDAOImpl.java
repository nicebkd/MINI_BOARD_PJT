package Model.board.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.board.Vo.BoardVo;
import jdbc.DBOracleConn;

public class BoardDAOImpl implements BoardDAO{
	
	private static BoardDAOImpl instance;

	public static BoardDAOImpl getInstance() {
		if (instance == null) {
			instance = new BoardDAOImpl();
		}	
		return instance;
	}
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs= null;
	
	private void closeAll(){
		try{if(rs!=null)rs.close();}catch (Exception e) {e.getMessage();}
		try{if(pstmt!=null)pstmt.close();}catch (Exception e) {e.getMessage();}
		try{if(conn!=null)conn.close();}catch (Exception e) {e.getMessage();}
	}
	

	@Override
	public ArrayList<BoardVo> list(String search_option, String keyword,int startPage,int endPage) {
		keyword="%"+keyword+"%";
		
		String sql1="";
		if(search_option.equals("writer")){
			sql1="where writer like ?";
		}else if(search_option.equals("title")){
			sql1="where title like ?";
		}else if(search_option.equals("content")){
			sql1="where content like ?";
		}else if(search_option.equals("all")){
			sql1="where writer like ? or title like ? or content like ?";
		}
		
		String sql = "select * from"
				+ "(select rownum rn,a.* from("
				+ "select * from tbl_board "+sql1+" order by num desc,reg_date desc)a)"
				+ "where rn between ? and ?";
		BoardVo dto = null;
		ArrayList<BoardVo> list = new ArrayList<>();
		try{
			conn = DBOracleConn.getConnection();
			if(search_option.equals("writer")||search_option.equals("title")||search_option.equals("content")){
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setInt(2,startPage);
				pstmt.setInt(3, endPage);
			}else if(search_option.equals("all")){
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);	
				pstmt.setInt(4,startPage);
				pstmt.setInt(5, endPage);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dto = new BoardVo();
				
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setUpdate_date(rs.getDate("update_date"));
				dto.setOriginal_file_name(rs.getString("original_file_name"));
				dto.setStored_file_name("stored_file_name");
				list.add(dto);
			}
			
			
		}catch (Exception e) {
			System.out.println("BoardList Error~" + e.getMessage());
		}finally {
			closeAll();
		}
		
		return list;
	}

	@Override
	public int insert(BoardVo vo) {
		int result = -1;
		String sql = "insert into tbl_board(num,writer,title,content,readCount,original_file_name,stored_file_name) values(seq_board.nextval,?,?,?,0,?,?)";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,vo.getWriter());
			pstmt.setString(2,vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4,vo.getOriginal_file_name());
			pstmt.setString(5, vo.getStored_file_name());
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("Board insert Error~" + e.getMessage());
		}finally {
			closeAll();
		}
		return result;
	}


	@Override
	public BoardVo view(int num) {
		BoardVo dto = null;
		String sql = "select num,title,writer,content,readCount,b.reg_date,b.update_date,original_file_name from"
				+ " tbl_board b,tbl_member m where m.ID = b.WRITER and num=?";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				dto = new BoardVo();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getInt("readCount"));	
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setUpdate_date(rs.getDate("update_date"));
				dto.setOriginal_file_name(rs.getString("original_file_name"));
			}
			readCount(num);
		
		}catch (Exception e) {
			System.out.println("Board insert Error~" + e.getMessage());
		}finally {
			closeAll();
		}
		return dto;
	}


	@Override
	public void readCount(int num) {
		String sql ="update tbl_board set readCount = readCount+1 where num=?";
		
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
					
		}catch (Exception e) {
			System.out.println("readCount Error~" + e.getMessage());
		}finally {
			closeAll();
		}
	}


	@Override
	public int getCount(String search_option,String keyword) {
		keyword="%"+keyword+"%";
		
		System.out.println("search_option : "+search_option);
		System.out.println("keyword : "+keyword);
		
		String sql1="";
		if(search_option.equals("writer")){
			sql1="where writer like ?";
		}else if(search_option.equals("title")){
			sql1="where title like ?";
		}else if(search_option.equals("content")){
			sql1="where content like ?";
		}else if(search_option.equals("all")){
			sql1="where writer like ? or title like ? or content like ?";
		}
		String sql ="select count(*) from tbl_board "+sql1;
		System.out.println("sql : " + sql);
		int result = -1;
		try{
			conn = DBOracleConn.getConnection();
			
			if(search_option.equals("writer")||search_option.equals("title")||search_option.equals("content")){
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
			}else if(search_option.equals("all")){
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("getCount Error~" + e.getMessage());
		}finally {
			closeAll();
		}
		
		return result;
	}


	/*@Override
	public int insertFileName(BoardVo vo) {
		int result = -1;
		String sql="insert into tbl_file values(seq_board.currval,?,?,?)";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getOriginal_fil_name());
			pstmt.setString(2,vo.getStored_file_name());
			pstmt.setInt(3, vo.getFile_size());
			result = pstmt.executeUpdate();			
			
		}catch (Exception e) {
			System.out.println("insertFileName"+e.getMessage());
		}finally {
			closeAll();
		}
		
		return 0;
	}

*/
	@Override
	public String getFileName(int num) {
		String sql="select stored_file_name from tbl_board where num=?";
		String stored_file_name="";
		try{
			conn=DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				stored_file_name = rs.getString(1);
			}
			
		}catch (Exception e) {
			System.out.println("getFile Error~"+e.getMessage());
		}
		return stored_file_name;
	}


	@Override
	public int pwCheck(String id, String password) {
		System.out.println("pwCheck id= "+id+", password= "+password);
		int result = -1;//패스워드가 맞으면 1, 틀리면 -1
    	String sql = "select password from tbl_member where id=?";
		try{
    		conn = DBOracleConn.getConnection();
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		if(rs.next()){
    			if(password.equals(rs.getString(1))){//비밀번호가 맞으면 1
    				result = 1;
    				System.out.println("비밀번호 맞다!");
    			}
    			else{//비밀번호가 맞지 않다면 -1
    				System.out.println("비밀번호 확인...안됨...뺴앵ㅇ이애이이앵");
    				result = -1;
    			}
    		}
    		System.out.println("pwCheck() 완료!"); 
    	}catch(Exception e){ System.out.println("pwCheck()메소드 오류"+e.getMessage());
        }finally{
        	closeAll();
        }
		return result;
	}


	@Override
	public int boardUpdate(int num, String title, String content) {
		int result = -1;//성공하면 1이상
		String sql = "update tbl_board set title=?, content=? where num=?";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			result = pstmt.executeUpdate();
			System.out.println("boardUpdate의 result값: "+result);
		}catch (Exception e) {
			System.out.println("boardUpdate()오류:" + e.getMessage());
		}finally {
			closeAll();
		}
		return result;
	}


	@Override
	public int boardDel(int num) {
		int result = -1;//성공: 1이상
		String sql = "delete tbl_board where num=?";
		try{
    		conn = DBOracleConn.getConnection();
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		result = pstmt.executeUpdate();
    	}catch(Exception e){ System.out.println("viewDel()메소드 오류"+e.getMessage());
        }finally{
        	closeAll();
        }
    	return result;
	}

	
	
	
	
	
	
	
}

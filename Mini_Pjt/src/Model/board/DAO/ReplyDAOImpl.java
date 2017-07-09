package Model.board.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.board.Vo.ReplyVo;
import jdbc.DBOracleConn;

public class ReplyDAOImpl implements ReplyDAO{
	
	private static ReplyDAOImpl instance;

	public static ReplyDAOImpl getInstance() {
		if (instance == null) {
			instance = new ReplyDAOImpl();
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
	public int insertRply(ReplyVo vo) throws SQLException {
		int result = 0;
		int rno = vo.getRno();// 글번호
		int ref = vo.getRef();// 원 글번호(원글인 경우 num=ref, 답변글인경우 num!=ref)
		int re_step = vo.getRe_step();// 답변글의 순서
		int re_level = vo.getRe_level();// 같은 답변글 내에서의 순서
		int number = 0;
		
		System.out.println("RRRRRRRRRRRRRNO : " + rno);
		
		String sql = "";
		try {
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement("select max(rno) from tbl_reply");
			rs = pstmt.executeQuery();
			if (rs.next()){
				number = rs.getInt(1) + 1;// 새글번호=(현재 글번호 + 1)
			}
			else
				number = 1;// 최초로 글을 쓰는 경우
			pstmt.close();// select쿼리 닫기
			if (rno != 0) {// 답변글인 경우
				System.out.println("답변글인 경우 REF :" + ref +" RE_STEP : " + re_step);
				sql = "update tbl_reply set re_step=re_step+1 where ref=? and re_step>?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				pstmt.executeUpdate();
				re_step = re_step + 1;
				re_level = re_level + 1;
				pstmt.close();
			} else {// 원글 인 경우
				ref = number;
				re_step = 0;
				re_level = 0;
			}
			
			sql="insert into tbl_reply(rno,bno,replyer,replytext,ref,re_step,re_level,regdate,updatedate) values(?,?,?,?,?,?,?,sysdate,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setInt(2, vo.getBno());
			pstmt.setString(3, vo.getReplyer());
			pstmt.setString(4, vo.getReplytext());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("inserRply error~"+e.getMessage());
		}finally{
			closeAll();
		}
		
		return result;
	}


	@Override
	public ArrayList<ReplyVo> list(int bno) {
		
		String sql="select rno,bno,replytext,replyer,ref,re_step,re_level,regdate,updatedate from tbl_reply r,tbl_board b where r.bno=b.num and bno=? order by ref desc,re_step";
		ArrayList<ReplyVo> list = new ArrayList<>();
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				ReplyVo vo = new ReplyVo();
				vo.setBno(rs.getInt("bno"));
				vo.setRno(rs.getInt("rno"));
				vo.setReplyer(rs.getString("replyer"));
				vo.setReplytext(rs.getString("replytext"));
				vo.setRef(rs.getInt("ref"));
				vo.setRe_step(rs.getInt("re_step"));
				vo.setRe_level(rs.getInt("re_level"));
				vo.setRegdate(rs.getDate("regdate"));
				vo.setUpdatedate(rs.getDate("updatedate"));
				list.add(vo);
			}
			
		}catch (Exception e) {
			
		}finally {
			closeAll();
		}
		return list;
	}


	@Override
	public int delete(int rno) {
		String sql="delete from tbl_reply where rno=?";
		int result = -1;
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rno);
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println("Reply delete error"+e.getMessage());
		}finally{
			closeAll();
		}
		return result;
	}

}

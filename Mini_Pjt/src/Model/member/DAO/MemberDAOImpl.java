package Model.member.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.member.Vo.MemberVo;
import jdbc.DBOracleConn;

public class MemberDAOImpl implements MemberDAO{
	
	private static MemberDAOImpl instance;

	public static MemberDAOImpl getInstance() {
		if (instance == null) {
			instance = new MemberDAOImpl();
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
	public int insertMember(MemberVo vo) {
		int result = -1;//회원가입 성공:1 / 실패: 0
		String sql="insert into tbl_member(id,password,name,email, use_flag, reg_date, update_date) "
				+ "	values(?,?,?,?,1,sysdate, sysdate)";
		try{
			conn=DBOracleConn.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			result = pstmt.executeUpdate();			
			
		}catch (Exception e) {
			System.out.println("insertMember Error" + e.getMessage());
		}finally {
			closeAll();
		}
		return result;
	}

	@Override
	public int id_check(String id) {
		int result = -1;
		String sql ="select * from tbl_member where id=?";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("id_check error~" + e.getMessage());
		}finally {
			closeAll();
		}
		return result;
	}

	@Override
	public MemberVo login(String id, String pw) {
		MemberVo member = null;
		String sql = "select * from tbl_member where id=? and password=?";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while(rs.next()){
				member = new MemberVo();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setUse_flag(rs.getInt("use_flag"));
				member.setReg_date(rs.getDate("reg_date"));
				member.setUpdate_date(rs.getDate("update_date"));
				System.out.println("실행완료");
			}
		}catch(Exception e){
			System.out.println("login오류: "+e.getMessage());
		}finally{
			closeAll();
		}
		return member;
	}

	@Override
	public MemberVo info(String id) {
		MemberVo dto = null;
		String sql = "select * from tbl_member where id=? ";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				dto = new MemberVo();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setReg_date(rs.getDate("reg_date"));
				
			}
		}catch(Exception e){
			System.out.println("info오류: "+e.getMessage());
		}finally{
			closeAll();
		}
		return dto;
	}

	@Override
	public int update(MemberVo vo) {
		MemberVo dto = null;
		int result=0;
		String sql = "update tbl_member set password=?,email=?,name=? where id=?";
		try{
			conn = DBOracleConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getId());
			result = pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("update오류: "+e.getMessage());
		}finally{
			closeAll();
		}
		return result;


	}
	
	
		
}

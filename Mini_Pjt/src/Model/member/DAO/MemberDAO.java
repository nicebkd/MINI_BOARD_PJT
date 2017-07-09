package Model.member.DAO;

import Model.member.Vo.MemberVo;

public interface MemberDAO {
	
	public int insertMember(MemberVo vo);//회원가입
	public int id_check(String id);//회원가입 시 id중복확인
	public MemberVo login(String id,String pw);	//로그인
	public MemberVo info(String id);//회원정보
	public int update(MemberVo vo);//회원정보수정
}

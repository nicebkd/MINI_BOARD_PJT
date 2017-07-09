package Model.board.DAO;

import java.util.ArrayList;

import Model.board.Vo.BoardVo;

public interface BoardDAO {
	
	public ArrayList<BoardVo> list(String search_option,String keyword,int startPage,int endPage); 
	public int insert(BoardVo vo);
	public BoardVo view(int num);
	public int getCount(String search_option,String keyword);
	public void readCount(int num);
/*	public int insertFileName(BoardVo vo);*/
	public String getFileName(int num);
	//게시글의 작성자 비밀번호와 로그인 비밀번호 확인
	public int pwCheck(String id, String password);//맞으면 1 / 틀리면 -1
	public int boardUpdate(int num, String title, String content);//글 수정
	public int boardDel(int num);//글 삭제
}

package Model.board.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.board.Vo.ReplyVo;

public interface ReplyDAO {
	public int insertRply(ReplyVo vo) throws SQLException;
	public ArrayList<ReplyVo> list(int bno);
	public int delete(int rno);
	

}

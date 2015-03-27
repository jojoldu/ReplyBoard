package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyDao {

	public List<Reply> getList(PagingInfo pagingInfo);
	
	public int getPagingInfo(PagingInfo pagingInfo);
	
	public void add(Reply reply);
	
	public Reply getReply(int no);
	
	public void update(Reply reply);
	
	public int getMaxNo();
	
	public void delete(Reply reply);
}

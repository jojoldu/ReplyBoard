package zum.potal.dwlee.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyService {

	//댓글 목록
	public List<Reply> getList(PagingInfo pagingInfo);
	
	//댓글 페이징
	public PagingInfo getPagingInfo(PagingInfo pagingInfo);
	
	//마지막 게시글 번호
	public int getMaxNo();
	
	//이미지 업로드
	public boolean uploadImage(Reply reply, String path, MultipartFile mpf);
	
	//댓글 등록
	public boolean add(Reply reply) ;
	
	//댓글 수정
	public boolean update(Reply reply) ;

	//댓글 조회
	public Reply getReply(int no);
	
	//댓글 삭제
	public void delete(Reply reply);

}

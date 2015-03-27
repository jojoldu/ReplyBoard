package zum.potal.dwlee.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import zum.potal.dwlee.controller.ReplyController;
import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.utils.BaseTime;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	private final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	private final Tika Tika = new Tika();
	
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BaseTime time;

	@Override
	@Transactional
	public List<Reply> getList(PagingInfo pagingInfo) {
		return replyDao.getList(pagingInfo);
	}


	@Override
	@Transactional	
	public PagingInfo getPagingInfo(PagingInfo pagingInfo) {
		
		int totalRow = replyDao.getPagingInfo(pagingInfo);
		int totalPageCount=totalRow / pagingInfo.getPageSize();		
		
		if(totalRow%pagingInfo.getPageSize() != 0){
			totalPageCount += 1; 
		}
		
		pagingInfo.setTotalPageCount(totalPageCount);
		return pagingInfo;
	}


	//db에 등록할 reply 만들기
	private void makeInsertReply(Reply reply){
		reply.setPath(String.format("%06d", reply.getNo()));

		if(reply.getParent() == 0){//부모코드가 없는경우, 답글이 아닌 일반글

			reply.setFamily(reply.getNo());

		}else{//부모코드가 있는경우, 해당 부모코드의 familyCode와 depth를 사용한다

			Reply parentRepley = replyDao.getReply(reply.getParent());
			reply.setFamily(parentRepley.getFamily());
			reply.setDepth(parentRepley.getDepth()+1);

		}
	}
	
	@Override
	@Transactional
	public int getMaxNo() {
		return replyDao.getMaxNo();
	};
	
	private boolean checkFileType(String imgExt){
		if("JPG".equalsIgnoreCase(imgExt) || "PNG".equalsIgnoreCase(imgExt) || "GIF".equalsIgnoreCase(imgExt)){//jpg, png, gif만 허용
			return true;
		}
		return false;
	}
	
	@Override
	public boolean uploadImage(Reply reply, String path, MultipartFile mpf){
		MultipartFile image = mpf;
		try{
			if(image != null){
				String imgExt = Tika.detect(image.getInputStream()).split("/")[1];
				
				if(!checkFileType(imgExt)){
					return false;
				}

				String fileName = String.valueOf(reply.getNo());
				File dir = new File(path);

				if(!dir.exists()){
					dir.mkdirs();
				}

				String imageName=fileName+"."+imgExt;
				File file = new File(path + File.separatorChar + imageName);
				image.transferTo(file);
				reply.setImageName(imageName);
			}
			return true;
		}catch(IOException ioe){
			logger.error("imageUpload 에러: ", ioe);
			return false;
		}

	}

	@Override
	@Transactional
	public boolean add(Reply reply){
		
		reply.setWriteDate(time.getNowTime());
		reply.setModifyDate(time.getNowTime());
		reply.setWriterNo(userDao.getUser(reply.getWriter()).getNo());
		
		makeInsertReply(reply);

		replyDao.add(reply);
		return true;
	}

	@Override
	@Transactional
	public boolean update(Reply reply) {
		
		Reply update = replyDao.getReply(reply.getNo());
		
		if(update == null){
			return false;
		}
		
		update.setModifyDate(time.getNowTime());
		update.setContent(reply.getContent());
		update.setImageName(reply.getImageName());
		
		return true;
	}

	@Override
	@Transactional	
	public Reply getReply(int no) {
		return replyDao.getReply(no);
	}


	@Override
	@Transactional			
	public void delete(Reply reply) {
		Reply update = replyDao.getReply(reply.getNo());
		update.setStatus('N');
	}

}

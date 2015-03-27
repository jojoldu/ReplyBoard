package zum.potal.dwlee.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.utils.CommonConstants;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.ResponseObject;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService replyService;
	
	private boolean checkAuthReply(Reply reply, HttpSession session){
		User loginUser = (User) session.getAttribute(CommonConstants.LOGIN_SESSION);
		Reply compare = replyService.getReply(reply.getNo());
		
		if(loginUser==null){
			return false;
		}
		
		return loginUser.getNo() == compare.getWriterNo();
	}
	
	//작성자 설정
	private void setWriter(Reply reply, HttpSession session){
		User login = (User)session.getAttribute(CommonConstants.LOGIN_SESSION);
		reply.setWriter(login.getId());	
	}
 
	//request 요청을 MultipartFile로 전환
	private MultipartFile getMultipartFile(MultipartHttpServletRequest request){

		Iterator<String> itr =  request.getFileNames();
		MultipartFile mpf=null;
		
		if(itr.hasNext()) {
			mpf = request.getFile(itr.next());
		}
		
		return mpf;
	}

	private String getPath(MultipartHttpServletRequest request){
		String fileSeparator=File.separator;
		
		if(fileSeparator.equals("\\")){
			fileSeparator="/";
		}
				
		return request.getSession().getServletContext().getRealPath(fileSeparator+"resources"+fileSeparator+"images");
	}
	
	private List<Integer> getAuthBtnList(int loginNo, List<Reply> replyList){
		
		List<Integer> result = new ArrayList<Integer>();
		
		for(Reply reply : replyList){
			
			if(reply.getWriterNo() == loginNo){
				result.add(reply.getNo());
			}
			
		}
		
		return result;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
		return "reply/list";
	}

	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(PagingInfo pagingInfo, HttpSession session){
		
		Map<String, Object> map = new HashMap<String, Object>();
		User login = (User)session.getAttribute(CommonConstants.LOGIN_SESSION);
		List<Reply> list = replyService.getList(pagingInfo);
		
		if(login == null){
			map.put("result", false);
			return map;
		}
		
		map.put("list", list);
		map.put("loginId", login.getId());
		map.put("loginEmail", login.getEmail());
		map.put("authBtnList", getAuthBtnList(login.getNo(), list));
		map.put("result", true);
		
		return map;
	}

	@RequestMapping(value="/paginginfo", method=RequestMethod.POST)
	@ResponseBody 
	public PagingInfo getPagingInfo(PagingInfo pagingInfo){
		
		return replyService.getPagingInfo(pagingInfo);
	}	

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		setWriter(reply, session);
		
		reply.setNo(replyService.getMaxNo()+1);
	
		boolean resultUpload = replyService.uploadImage(reply, getPath(request), getMultipartFile(request));
		boolean resultAdd = replyService.add(reply);
		
		if(resultUpload && resultAdd){
			return new ResponseObject(true);
		}
		
		return new ResponseObject(false);
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		setWriter(reply, session);
		
		if(!checkAuthReply(reply, session)){
			return new ResponseObject(false);
		}
		
		boolean resultUpload = replyService.uploadImage(reply, getPath(request), getMultipartFile(request));
		boolean resultUpdate = replyService.update(reply);	
		
		if(resultUpload && resultUpdate){
			return new ResponseObject(true);
		}
		
		return new ResponseObject(false);
	}	

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody	
	public ResponseObject delete(Reply reply, HttpSession session) {
		
		if(!checkAuthReply(reply, session)){
			return new ResponseObject(false);
		}
		
		replyService.delete(reply);
		return new ResponseObject(true);
	}
}
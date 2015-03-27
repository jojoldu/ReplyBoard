/**
 * 댓글 관련 자바스크립트
 */

$(function() {

	$("#pageSize").val('30');
	
	//댓글 목록조회
	makeListAndPaging(activePage.get_index(), Reply);
	
	//댓글입력 
	$('#writeBtn').click(function(){
		addReply(this);
	});
	
	//페이징
	$("#pagination").on('click','.pageNo',function(){
		Reply.get_list(this.id);
	});
	
	//페이징 사이즈 조절
	$("#pageSize").change(function(){
		makeListAndPaging(activePage.get_index(), Reply);
	});
	
	//비밀번호 확인
	$("#checkPasswordBtn").click(checkPassword);
	
	//회원정보수정
	$("#updateUserBtn").click(updateUserInfo);
	
	//로그아웃
	$("#logoutBtn").click(logout);
	
	//회원탈퇴 확인
	$("#deleteUserBtn").click(confirmDeleteUser);
	
	var $replyList=$('#replyList');
	
	//replyList 클릭이벤트 동적 binding
	$replyList.on('click', '.openAddFormBtn', function(){	//답글 열기 버튼
		
    	openAddForm($(this).parents("tr").prop("id"));
    	
    }).on('click', '.openUpdateFormBtn', function(){ 		//수정폼 열기버튼
    	
    	openUpdateForm($(this).parents("tr").prop("id"));
    	
    }).on('click', '.deleteReplyBtn', function(){  			//글 삭제 버튼
    	
    	confirmDelete($(this).parents("tr").prop("id"));
    	
    }).on('click', '.addReplyBtn', function(){    			//답글 등록 버튼
    	
    	addReply(this);
    	
    }).on('click', '.closeAddFormBtn', function(){    		//답글폼 취소버튼
    	
    	$(this).closest(".addForm").addClass("hide");
    	$(".openAddFormBtn").prop("disabled", false);
    	
    }).on('click', '.updateReplyBtn', function(){   	 	//글 수정완료 버튼
    	
    	updateReply(this);
    	
    }).on('click', '.closeUpdateFormBtn', function(){    	//수정폼 취소버튼
    	
    	$(this).closest(".updateForm").addClass("hide");
    	$(this).closest(".inputForm").find("input[type=text]").prop("readonly", true);
    	$(".openUpdateFormBtn").prop("disabled", false);
    	
    });
});



//활성화된 페이지
var activePage = (function(){

	var index=1;
	
	return {
		get_index: function(){
			return index;
		},
		set_index: function(i){
			index = i;
		}
	}
}());


var Reply = (function (){
	return {
		get_list:function(index){
			var pageIndex=index;
			var pagingInfo = setPagingInfo(pageIndex);
			
			activePage.set_index(pageIndex);
			
			$('#'+pageIndex).addClass('active');//현재 선택한 페이지번호 활성화
			
			$.ajax({
				type:"POST",
				url:"list",
				data:pagingInfo,
				dataType:"json",
				success:function(data){
					if(data.result){
						$("#loginId").text(data.loginId);
						$("#email").val(data.loginEmail);
						drawTable(data.list, data.authBtnList);				
					}else{
						alert("목록을 조회하지 못하였습니다.");
					}
				}
			} );
		}
	};
})();



//table 생성
function drawTable(list, authBtnList) {
	
	var $tbody = $("#replyList tbody");
	var row='';
	
	$.each(list, function(index, value){
		row+=drawRow(value, authBtnList);
	});
	
	$tbody.html('');
	$tbody.append(row); 
}

//table row 생성
function drawRow(rowData, authBtnList) {
	var id=rowData.no;
	var replyId = 'reply'+id;
	var replyForm = $(".replyForm").html(); //댓글 입력폼
	var updateForm = $(".updateForm").html(); //수정폼
	var hideFlag = false
	var hide="hide";
	
	var depth = (100-(rowData.depth*3))+"%;";	//들여쓰기

    var row='<tr class="inputForm" id="'+replyId+'">';
	
	$.each(authBtnList, function(index, value){
		if(id === value){
			hideFlag=true;
		}
	});
	if(hideFlag){
		hide="";
	}
    
    if(rowData.imageName!=null){
		row+='<td align="center"><img src="../resources/images/' + rowData.imageName + '"></td>'; 
	}else{
		row+='<td align="center"></td>';
	}
	
	row+='<td align="center"><div class="row" style="float:right; width:'+depth+'"><input type="text" class="form-control content" readonly value="'+ rowData.content +'"></div>';
	row+='<br><div class="row hide updateForm" style="float:right; width:'+depth+'">'+updateForm+'</div>';
	row+='<div class="row hide addForm" style="float:right; width:'+depth+'">'+replyForm+'</div></td>';
	row+='<td align="center">' + new Date(rowData.modifyDate).format("yyyy-MM-dd HH:mm") + '</td>';
	row+='<td align="center" class="writer">' + rowData.writer + '</td>';
	row+='<td align="center"><button type="button" class="btn btn-info btn-sm openAddFormBtn" title="댓글"><span class="glyphicon glyphicon-comment"></span></button></td>';
	row+='<td align="center"><button type="button" class="btn btn-primary btn-sm openUpdateFormBtn '+hide+'" title="수정"><span class="glyphicon glyphicon-edit "></span></button></td>';
	row+='<td align="center"><button type="button" class="btn btn-danger btn-sm deleteReplyBtn '+hide+'" title="삭제"><span class="glyphicon glyphicon-trash"></span></button></td></tr>';
	
	return row;
}


//작성폼 초기화
function resetForm(){
	$("#content").val("");
	$("#image").val("");
}

//답글 작성
function addReply(e){
	var $e = $(e);
	var no;
	if($e.prop("id") === "writeBtn"){
		no = 0;
	}else{
		no = $e.closest(".inputForm").prop("id").substr(5);
	}
	var $parent = $e.closest(".addForm");
	var content =$parent.find(".content").val();
	var file =  $parent.find("input[type=file]")[0].files[0];
	
	var formData = new FormData();
	formData.append("parent",no);
	formData.append("content", content);
	formData.append("image", file);

	addReplyToAjax(formData);
}

//글&답글 등록
function addReplyToAjax(formData){
	var formData = formData;
	
	$.ajax({
		type:"POST",
		url:"add",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data.result){
				alert("댓글이 등록되었습니다!");
				resetForm();
				makeListAndPaging(activePage.get_index(), Reply);
			}else{
				alert("댓글 등록이 실패하였습니다.");
			}
		}
	} );
}

//답글작성폼 열기
function openAddForm(trId){
	
	$("#"+trId).find(".addForm").removeClass("hide");
	$(".openAddFormBtn").prop("disabled", true);//하나의 답글폼 open시 다른 답글폼 open 못하도록 방지
}

//글 수정폼 열기
function openUpdateForm(trId){
	$tr = $('#'+trId);
	$tr.find('.updateForm').removeClass('hide');
	$tr.find('.content').prop('readonly',false);
	$(".openUpdateFormBtn").prop("disabled", true);
}

//글 수정
function updateReply(e){
	var $parent = $(e).closest(".inputForm");
	var no = $parent.prop("id").substr(5);
	var content =$parent.find(".content").val();
	var file =  $parent.find("input[type=file]")[0].files[0];
	
	var formData = new FormData();
	formData.append("no",no);
	formData.append("content", content);
	formData.append("image", file);
	
	$.ajax({
		type:"POST",
		url:"update",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data.result){
				alert("댓글이 수정되었습니다!");
				resetForm();
				Reply.get_list(activePage.get_index());
			}else{
				alert("댓글 수정이 실패하였습니다.");
			}
		}
	} );
}

//댓글 삭제 확인
function confirmDelete(trId){
	var no=trId.substr(5);
	bootbox.confirm("삭제하시겠습니까?", function(confirmed) {
		if(confirmed) {
			deleteReply(no);
		}
	});	
}

//글 삭제
function deleteReply(no){
	var no=no;
	
	var user = {
			no:no
	}
	
	$.ajax({
		type:"POST",
		url:"delete",
		data:user,
		dataType:"json",
		success:function(data){
			if(data.result){
				alert("삭제되었습니다.");
				Reply.get_list(activePage.get_index());//active 된 pageIndex				
			}else{
				alert("삭제가 실패되었습니다.");
			}
		}
	} )
}

////기존 비밀번호 확인
//function checkPassword(){
//	var id=$("#loginId").text();
//	var password = $("#password").val();
//	var user={
//			id:id,
//			password:password
//	}
//	
//	$.ajax({
//		type:"POST",
//		url:"/user/check/password",
//		data:user,
//		dataType:"json",
//		success:function(data){
//			if(data.result){
//				alert("비밀번호가 확인되었습니다.");
//			}else{
//				alert("비밀번호가 잘못되었습니다.");
//			}
//		}
//	} );
//	
//}

//사용자정보 수정
function updateUserInfo(){
	
	var id = $("#loginId").text();
	var originPassword = $("#password").val();
	var password = $("#newPassword").val();
	var confirmPassword = $("#confirmPassword").val();
	var email = $("#email").val();
	
	if(password===""){
		alert("비밀번호는 공란이 허용되지 않습니다.");
		return;
	}
	if(password !== confirmPassword){
		alert("새로운 비밀번호를 다시 확인해주세요");
		return;
	}
	
	
	$.ajax({
		type:"POST",
		url:"/user/update",
		data: {
			id			   :id,
			password	   :password,
			email		   :email,
			originPassword : originPassword
		},
		dataType:"json",
		success:function(data){
			if(data.result){
				alert("회원정보가 수정되었습니다.");
				$(".close").trigger("click");
			}else{
				alert(data.message);
			}
		}
	} );
}

//로그아웃
function logout(){
	
	$.ajax({
		type:"POST",
		url:"/user/logout",
		success:function(data){
			if(data.result){
				alert("로그아웃 되었습니다.");
				location.href="/";
			}else{
				alert("로그아웃이 실패하였습니다.");
			}
		}
	} );
}

//탈퇴확인
function confirmDeleteUser(){
	bootbox.confirm("탈퇴하시겠습니까?", function(confirmed) {
		if(confirmed) {
			deleteUser();
		}
	});	
}

//탈퇴
function deleteUser(){
	$.ajax({
		type:"POST",
		url:"/user/delete",
		dataType:"json",
		success:function(data){
			if(data.result){
				alert("탈퇴되었습니다.");
				location.href="/";				
			}else{
				alert("탈퇴가 실패하였습니다.");				
			}

		}
	} );
}


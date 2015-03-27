/**
 * 공통유틸 자바스크립트
 */

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var d = this;
     
    return f.replace(/(yyyy|MM|dd|HH|hh|mm|ss|)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "MM": return (d.getMonth() + 1).zerofill(2);
            case "dd": return d.getDate().zerofill(2);
            case "HH": return d.getHours().zerofill(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zerofill(2);
            case "mm": return d.getMinutes().zerofill(2);
            case "ss": return d.getSeconds().zerofill(2);
            default: return $1;
        }
    });
};

Number.prototype.zerofill = function(length){
	return this.toString().zerofill(length);
};

String.prototype.zerofill = function(length){
	return "0".numfill(length - this.length) + this;
};

String.prototype.numfill = function(length){
	var str = '';
	var i = 0; 
	
	while (i < length) { 
		str += this;
		i++;
	} 
	return str;
};

$(function(){
	
	//ajax error handler
	$.ajaxSetup({
		error:function(e, xhr, settings, exception){
		    var message = '';

		    if (xhr.status === 0) {
		        message = '인터넷 연결을 확인해주세요.';
		    }
		    else if (xhr.status === 401) {
		    	message = '권한이 없습니다.';
		    }
		    else if (xhr.status === 403) {
		    	message = '서버가 요청을 금지하였습니다.';
		    }
		    else if (xhr.status === 404) {
		        message = '페이지를 찾을 수 없습니다.';
		    }
		    else if (xhr.status === 500) {
		        message = '내부서버오류 ';
		    }
		    else {
		        message = ('Ajax 요청에 알 수 없는 에러가 발생하였습니다.');
		    }
		    
		    alert(message);			
		}
	});
	
});


//페이징정보 설정
function setPagingInfo(pageIndex){
	var pageIndex = pageIndex;
	var pageSize = $("#pageSize").val();
	var obj = {
			pageSize  : pageSize,
			pageIndex : pageIndex,
			firstRow  : (pageIndex-1)*pageSize,
	}
	return obj;
}

//페이징 정보 호출
function getPagingInfo(pageIndex, Obj){
	var pagingInfo = setPagingInfo(pageIndex);
	$.ajax({
		type:"POST",
		url:"paginginfo.json",
		data:pagingInfo,
		dataType:"json",
		success:function(pagingInfo){
			makeAjaxPaging(pagingInfo);
		}
	} );	
}

//ajax 페이징
function makeAjaxPaging(pagingInfo, Obj){

	/*
	1) 현재 index와 pageSize를 서버에 전송한다.
	2) 서버에서는 totalCount를 가져오고 이를 totalCount/pageSize하여 페이지 개수를 계산해낸다.
	3) list 조회
	4) 2),3) 결과 클라이언트에 전송
	5) 4)을 토대로 페이징 html코드 생성 & 페이지개수가 pageScope를 넘어가면 < > 생성
	6) 각 페이지버튼마다 click="getReplyList(pageIndex)" 추가

	 */

	var totalPageCount =parseInt(pagingInfo.totalPageCount);//전체 페이지개수
	var pageScope = parseInt(pagingInfo.pageScope);//화면에 보여줄 페이지 범위
	var pageSize = parseInt(pagingInfo.pageSize);//한 페이지당 보여줄 글개수
	var pageIndex = parseInt(pagingInfo.pageIndex);//현재 페이지
	var lastPageIndex=parseInt(pageIndex+pageScope-1);//현재화면에서 나와야할 마지막 페이지번호
	
	var $pagination = $('#pagination');
	
	var $next=$("#next");
	var $prev=$("#prev");
	
	if(lastPageIndex>totalPageCount){//전체페이지수 보다 lastPageIndex가 크면
		lastPageIndex=totalPageCount;
	}

	var paginationHtml='<ul class="pagination pagination" style="float:right; margin:2px;">';
	
	paginationHtml+='<li><a href="javascript:void(0);" class="disabled" id="prev"><</a></li>';
	
	for(var i=1;i<=lastPageIndex;i++){
		paginationHtml+='<li><a href="javascript:void(0);" class="pageNo" id="'+i+'">'+i+'</a></li>';	
	}
	paginationHtml+='<li><a href="javascript:void(0);" class="disabled" id="next">></a></li></ul>';
	
	
	$pagination.html('');
	$pagination.append(paginationHtml);
	
	
	$("#"+pageIndex).addClass("active");//현재페이지 활성화
	
	if(totalPageCount > (pageIndex + pageScope - 1)){//현재 페이지범위보다 전체페이지개수가 더 클경우
		$next.removeClass("disabled");
		$next.click(function(){
			makeListAndPaging(lastPageIndex+1, Obj);
		});
	}
	
	if(pageIndex > pageScope){//현재페이지가 pageScope 보다 클경우
		$prev.removeClass("disabled");
		$prev.click(function(){
			makeListAndPaging(pageIndex-pageScope, Obj);
		});
	}
}

//페이징 생성 및 목록출력
function makeListAndPaging(pageIndex, Obj){
	getPagingInfo(pageIndex, Obj);
	Obj.get_list(pageIndex);
}

//영문, 숫자만 입력되었는지 체크
function checkEng(str) {
	var flag=true;  
	var regResult = (/^[0-9a-z]*$/i).test(str); //영문, 숫자 이외엔 없는지
	if(!regResult || str===""){
		flag = false;       
	}
	return flag;
}


//글자수 제한
function limitText(limitField, limitCount){
	if (limitField.value.length > limitCount) {
		limitField.value = limitField.value.substring(0, limitCount);
		alert(limitCount+"자 이하로만 입력이 가능합니다.");
	} 
}

//날짜변환
function getDateTime(dateTime){
	var dateTime = new Date(dateTime);
	
} 



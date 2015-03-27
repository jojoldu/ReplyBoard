<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>댓글 목록</title>
<jsp:include page="/WEB-INF/jsp/common/commonHeader.jsp" flush="true" />

<!-- ajax 파일 업로드를 위한 plugin -->
<script src="../plugins/jquery.form.js"></script>

<!-- 바탕화면 css -->
<link rel="stylesheet" href="../css/carousel.css">
<body>

	<div class="navbar-wrapper">
		<div class="container"></div>
	</div>
	<!-- Carousel
    ================================================== -->
	<div id="myCarousel" class="carousel slide">
		<div class="carousel-inner">
			<div class="item active">
				<div class="container">
					<div class="carousel-caption">
						<h1>이동욱</h1>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.carousel -->

	<div class="row">
		<div class="col-md-3">
			<div class="row">
				<div class="col-md-3 col-md-offset-4" style="padding: 0px;">
					<a data-toggle="modal" href="#userInfoForm">
						<button type="button" class="btn btn-info">회원정보수정</button>
					</a>
				</div>
				<div class="col-md-3" style="padding: 0px;">
					<button type="button" class="btn btn-default" id="logoutBtn">로그아웃</button>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-3 col-md-offset-3">
					<h3 style="margin: 0px;">
						<span class="label label-default">페이징사이즈</span>
					</h3>
				</div>
				<div class="col-md-3 col-md-offset-1" style="padding-left: 0px;">
					<select id="pageSize" class="form-control" style="float: right;">
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="30">30</option>
					</select>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-3 col-md-offset-7" style="padding: 0px;">
					<button type="button" class="btn btn-danger" id="deleteUserBtn">회원탈퇴</button>
				</div>
			</div>
		</div>
		<div class="modal fade" id="userInfoForm">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">회원정보수정</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label><span class="required">*</span>아이디</label>
								</div>
								<div class="col-md-6 col-slim-padding">
									<label><span class="required" id="loginId"></span></label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<label><span class="required">*</span>기존 비밀번호</label>
								</div>
								<div class="col-md-6 col-slim-padding">
									<input type="password" id="password" class="form-control"
										placeholder="">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<label><span class="required">*</span>새 비밀번호</label>
								</div>
								<div class="col-md-6 col-slim-padding">
									<input type="password" id="newPassword" class="form-control"
										placeholder="">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<label><span class="required">*</span>새 비밀번호확인</label>
								</div>
								<div class="col-md-6 col-slim-padding">
									<input type="password" id="confirmPassword"
										class="form-control" placeholder="">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<label><span class="required">*</span>Email</label>
								</div>
								<div class="col-md-6 col-slim-padding">
									<input type="email" id="email" class="form-control required"
										placeholder="">
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row">
							<div class="col-md-12">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">닫기</button>
								<button type="button" class="btn btn-primary" id="updateUserBtn">수정완료</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<div class="col-md-6 addForm" id="reply0">
			<textarea class="form-control content" rows="3" autofocus
				placeholder="댓글을 입력해주세요." onKeyDown="limitText(this,10000)"></textarea>
			<br>
			<div class="row">
				<div class="col-md-2 file">
					<input type="file" id="image" name="image">
				</div>
			</div>
			<div class="row">
				<div class="col-md-2 col-md-offset-10">
					<button type="button" class="btn btn-success" id="writeBtn">글
						등록하기</button>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<div class="row hide">
		<!-- 글 수정 form -->
		<form class="form updateForm" enctype="multipart/form-data">
			<br>
			<div class="row">
				<div class="col-md-2 file">
					<input type="file">
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-8">
					<button type="button" class="btn btn-primary btn-sm updateReplyBtn">수정</button>
					<button type="button"
						class="btn btn-danger btn-sm closeUpdateFormBtn">취소</button>
				</div>
			</div>
		</form>
	</div>

	<div class="row hide">
		<!-- 답글 form -->
		<form class="form replyForm" enctype="multipart/form-data">
			<textarea class="form-control content" rows="3" autofocus
				placeholder="답글을 입력해주세요." onKeyDown="limitText(this,10000)"></textarea>
			<br>
			<div class="row">
				<div class="col-md-2 file">
					<input type="file">
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-8">
					<button type="button" class="btn btn-primary btn-sm addReplyBtn">등록</button>
					<button type="button" class="btn btn-danger btn-sm closeAddFormBtn">취소</button>
				</div>
			</div>
		</form>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<table class="table table-striped table-hover" id="replyList"
				style='margin: 0px;'>
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="10%">
				<col width="3%">
				<col width="3%">
				<col width="3%">
				<tbody>
				</tbody>
			</table>
			<div id="pagination"></div>
		</div>
	</div>
</body>
</html>
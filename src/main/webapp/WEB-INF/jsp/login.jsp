<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />


<!-- 각 페이지에서 사용될 javascript 파일 -->
<script type="text/javascript" src="plugins/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/login.js"></script>

<link rel="stylesheet" href="css/signin.css">
</head>
<body>
	<div class="container">
		<form class="form-signin" id="loginForm">
			<h2 class="form-signin-heading">댓글 게시판</h2>
			<input type="text" class="form-control" placeholder="ID" autofocus id="loginId"> 
			<input type="password" class="form-control" placeholder="Password" id="loginPassword"> 
			<button class="btn btn-lg btn-success btn-block" type="button" id="loginBtn">로그인</button>
			<br>
			<a data-toggle="modal" href="#signUpForm">
				<button class="btn btn-lg btn-primary btn-block" type="button">회원가입</button>
			</a>
		</form>
	</div>	<!-- /container -->

	<div class="modal fade" id="signUpForm">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">회원가입</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="row">
							<div class="col-md-3">
								<label><span class="required">*</span>아이디</label>
							</div>
							<div class="col-md-6 col-slim-padding">
								<input type="text" id="id" class="form-control required"
									placeholder="" onKeyDown="limitText(this,100)">
							</div>
							<div class="col-md-1 col-slim-padding">
								<button class="btn btn-danger" id="checkIdBtn">중복검사</button>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<label><span class="required">*</span>비밀번호</label>
							</div>
							<div class="col-md-6 col-slim-padding">
								<input type="password" id="password" class="form-control"
									placeholder="">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<label><span class="required">*</span>비밀번호확인</label>
							</div>
							<div class="col-md-6 col-slim-padding">
								<input type="password" id="checkPassword" class="form-control"
									placeholder="">
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
							<button type="button" class="btn btn-default" data-dismiss="modal" id="closeForm">닫기</button>
							<button type="button" class="btn btn-primary" id="signUpBtn">회원가입</button>						
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</body>
</html>
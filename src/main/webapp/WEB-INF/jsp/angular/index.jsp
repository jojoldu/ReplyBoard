<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/common/commonHeader.jsp" flush="true" />

<script type="text/javascript" src="../js/angular/controller.js"></script>
<script type="text/javascript" src="../js/angular/service.js"></script>
</head>

<body ng-app='app' ng-controller="MainCtrl">
	<div class="col-md-8 col-md-2-offset">
		<input type="checkbox" ng-model="youCheckedId" ng-click="checkModel()">
		<br>
		<br>
		창업예산:<input ng-change="computeNeeded()" ng-model="funding.startingEstimate">
		<br>
		예산금액: {{funding.startingEstimate}}
		<br>
		권장 자본금: {{needed}}
		<button>저의 창업에 투자해주세요</button>
		
		<ul>
			<li ng-repeat = 'student in students'>
				<a href='/student/view/{{student.id}}'>{{student.name}}</a>
			</li>
		</ul>
		<input type="text" ng-model='student'/>
		<button ng-click="insertStudent()">등록하기</button>
	</div>
</body>
</html>
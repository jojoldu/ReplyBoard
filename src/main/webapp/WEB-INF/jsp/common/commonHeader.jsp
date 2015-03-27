<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<!-- Bootstrap css -->
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<!-- common css -->
<link href="../css/common.css" rel="stylesheet" type="text/css" />

<!-- 각 페이지에서 사용될 javascript 파일 -->
<script type="text/javascript" src="../plugins/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../plugins/angular.js">
<script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../bootstrap/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="../js/common/common.js"></script>
<script type="text/javascript" src="../js/${fn:replace(fn:substringAfter(pageContext.request.requestURI, '/WEB-INF/jsp/'), '.jsp', '.js')}"></script>
/**
 * 
 */

var app = angular.module('app',[]);

app.controller('MainCtrl', function($scope){
	$scope.message = 'World';
	
	$scope.greet = function(){
		$scope.message = "hello, "+$scope.user.message;
	}
	
	$scope.checkModel = function(){
		if($scope.youCheckedId){
			alert($scope.youCheckedId);
		}
	}
	
	$scope.funding = {
			startingEstimate : 0
	};
	
	$scope.computeNeeded = function(){
		$scope.needed = $scope.funding.startingEstimate * 10;
	}
	
	$scope.requestFunding = function(){
		alert("고객을 모으세요");
	}
	
	
	var students = [{name:'Mary', id:'1'},
	                {name:'Jack', id:'2'},
	                {name:'Jill', id:'3'}];
	
	$scope.students = students;
	
	$scope.insertStudent = function(){
		var student = {
				id:$scope.students.length+1,
				name:$scope.student
		}
		$scope.students.push(student);
	}
});
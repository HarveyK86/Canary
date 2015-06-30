module = angular.module("org.canary.controller")

controller = ($scope, $location, $http, $window) ->
	self = this

	self.loginFailureAlert =
		name: "LoginFailure"
		template: "tpl/login-failure.tpl.html"
		enabled: false

	self.logoutSuccessAlert =
		name: "LogoutSuccess"
		template: "tpl/logout-success.tpl.html"
		enabled: false

	$scope.availableAlerts = [
		self.loginFailureAlert,
		self.logoutSuccessAlert
	]

	$scope.form =
		username: ""
		password: ""

	self.init = () ->
		parameters = $location.search()
		for alert in $scope.availableAlerts	
			alert.enabled = parameters.alert == alert.name

	self.isUsernameFieldValid = () ->
		$scope.form.username != null && $scope.form.username != ""

	self.isPasswordFieldValid = () ->
		$scope.form.password != null && $scope.form.password != ""

	self.isFormValid = () ->
		self.isUsernameFieldValid() && self.isPasswordFieldValid()

	self.submitForm = () ->
		post =
			method: "POST"
			url: "login_check"
			data: "username=" + $scope.form.username + "&password=" + $scope.form.password
			headers: "Content-Type": "application/x-www-form-urlencoded"

		$http(post).success(self.onLoginSuccess).error(self.onLoginFailure)

	self.onLoginSuccess = () ->
		$window.location.href = "index"

	self.onLoginFailure = () ->
		$window.location.href = "login#?alert=LoginFailure"
		$window.location.reload()

	init: self.init
	isUsernameFieldValid: self.isUsernameFieldValid
	isPasswordFieldValid: self.isPasswordFieldValid
	isFormValid: self.isFormValid
	submitForm: self.submitForm

module.controller("LoginController", ["$scope", "$location", "$http", "$window", controller])

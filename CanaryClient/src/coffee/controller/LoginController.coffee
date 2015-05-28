module = angular.module("org.canary.controller")

controller = ($scope, $location, $http, $window) ->
	self = this

	self.loginError =
		name: "LoginError"
		template: "tpl/login-error.tpl.html"
		visible: false

	self.logoutSuccess =
		name: "LogoutSuccess"
		template: "tpl/logout-success.tpl.html"
		visible: false

	$scope.alerts = [
		self.loginError,
		self.logoutSuccess
	]

	$scope.login =
		username: ""
		password: ""

	self.init = () ->
		parameters = $location.search()
		for alert in $scope.alerts	
			alert.visible = parameters.alert == alert.name

	self.isUsernameValid = () ->
		$scope.login.username != null && $scope.login.username != ""

	self.isPasswordValid = () ->
		$scope.login.password != null && $scope.login.password != ""

	self.isLoginValid = () ->
		self.isUsernameValid() && self.isPasswordValid()

	self.login = (username, password) ->
		post =
			method: "POST"
			url: "login_check"
			data: "username=" + username + "&password=" + password
			headers: "Content-Type": "application/x-www-form-urlencoded"

		$http(post).success(self.onLogin).error(self.onLoginError)

	self.logout = () ->
		post =
			method: "POST"
			url: "logout"

		$http(post).success(self.onLogout).error(self.onLogoutError)

	self.onLogin = () ->
		$window.location.href = "index"

	self.onLoginError = () ->
		$window.location.href = "login#?alert=LoginError"
		$window.location.reload()

	self.onLogout = () ->
		$window.location.href = "login#?alert=LogoutSuccess"

	self.onLogoutError = () ->
		path = $location.path()
		$window.location.href = "index#" + path + "?alert=LogoutError"
		$window.location.reload()

	init: self.init
	isLoginErrorVisible: self.isLoginErrorVisible
	isLogoutSuccessVisible: self.isLogoutSuccessVisible
	isUsernameValid: self.isUsernameValid
	isPasswordValid: self.isPasswordValid
	isLoginValid: self.isLoginValid
	login: self.login
	logout: self.logout

module.controller("LoginController", ["$scope", "$location", "$http", "$window", controller])

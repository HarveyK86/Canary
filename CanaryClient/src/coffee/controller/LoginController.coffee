module = angular.module("org.canary.controller")

controller = ($scope, $http, $window) ->
	self = this

	self.token;

	$scope.login =
		username: ""
		password: ""

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

		$http(post).success(self.handleLoginSuccess).error(self.handleLoginError)

	self.logout = () ->
		post =
			method: "POST"
			url: "logout"

		$http(post).success(self.handleLogoutSuccess).error(self.handleLogoutError)

	self.handleLoginSuccess = () ->
		$window.location.href = "index"

	self.handleLoginError = () ->
		$window.location.href = "login#/loginError"

	self.handleLogoutSuccess = () ->
		$window.location.href = "login#/logoutSuccess"

	self.handleLogoutError = () ->
		$window.location.href = "index#/logoutError"

	isUsernameValid: self.isUsernameValid
	isPasswordValid: self.isPasswordValid
	isLoginValid: self.isLoginValid
	login: self.login
	logout: self.logout

module.controller("LoginController", ["$scope", "$http", "$window", controller])

module = angular.module("org.canary.controller")

controller = ($scope) ->
	self = this

	$scope.editUser =
		username: ""
		password: ""
		confirmPassword: ""
		visible: false

	self.showEditUser = (username) ->
		$scope.editUser =
			username: username
			password: ""
			confirmPassword: ""
			visible: true

	self.hideEditUser = () ->
		$scope.editUser.visible = false

	self.isEditUserValid = () ->
		self.isUsernameValid() && self.isPasswordValid() && self.isConfirmPasswordValid()

	self.isUsernameValid = () ->
		$scope.editUser.username != null && $scope.editUser.username != ""

	self.isPasswordValid = () ->
		true

	self.isConfirmPasswordValid = () ->
		$scope.editUser.confirmPassword == $scope.editUser.password

	showEditUser: self.showEditUser
	hideEditUser: self.hideEditUser
	isUsernameValid: self.isUsernameValid
	isPasswordValid: self.isPasswordValid
	isConfirmPasswordValid: self.isConfirmPasswordValid
	isEditUserValid: self.isEditUserValid

module.controller("UserController", ["$scope", controller])

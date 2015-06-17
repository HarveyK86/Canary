module = angular.module("org.canary.controller")

controller = ($scope) ->
	self = this

	$scope.editUser =
		username: ""
		password: ""
		confirmPassword: ""
		permissions: []
		visible: false

	self.showEditUser = (username, permissions) ->
		$scope.editUser =
			username: username
			password: ""
			confirmPassword: ""
			permissions: permissions
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

	self.hasPermission = (permission) ->
		$scope.editUser.permissions.indexOf(permission) != -1

	self.addPermission = (permission) ->
		$scope.editUser.permissions.push(permission)

	self.removePermission = (permission) ->
		index = $scope.editUser.permissions.indexOf(permission)
		if index != -1
			$scope.editUser.permissions.splice(index, 1)

	showEditUser: self.showEditUser
	hideEditUser: self.hideEditUser
	isUsernameValid: self.isUsernameValid
	isPasswordValid: self.isPasswordValid
	isConfirmPasswordValid: self.isConfirmPasswordValid
	isEditUserValid: self.isEditUserValid
	hasPermission: self.hasPermission
	addPermission: self.addPermission
	removePermission: self.removePermission

module.controller("UserController", ["$scope", controller])

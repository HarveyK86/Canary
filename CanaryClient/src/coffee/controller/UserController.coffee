module = angular.module("org.canary.controller")

controller = ($routeParams, $scope, $window, service) ->
	self = this

	$scope.userForm =
		username: ""
		password: ""
		confirmPassword: ""
		permissions: []
		enabled: false

	self.init = () ->
		service.read($routeParams.userId, self.onRead)

	self.isFormEnabled = () ->
		$scope.userForm.enabled

	self.enableForm = () ->
		$scope.userForm.username = $scope.user.getUsername()
		$scope.userForm.password = ""
		$scope.userForm.confirmPassword = ""
		self.copyPermissions($scope.user.getPermissions())
		$scope.userForm.enabled = true

	self.disableForm = () ->
		$scope.userForm.username = ""
		$scope.userForm.password = ""
		$scope.userForm.confirmPassword = ""
		self.copyPermissions($scope.user.getPermissions())
		$scope.userForm.enabled = false

	self.isUsernameFieldValid = () ->
		$scope.userForm.username != null && $scope.userForm.username != ""

	self.isPasswordFieldValid = () ->
		true

	self.isConfirmPasswordFieldValid = () ->
		$scope.userForm.confirmPassword == $scope.userForm.password

	self.isPermissionBeingGranted = (permission) ->
		$scope.userForm.permissions.indexOf(permission) != -1

	self.grantPermission = (permission) ->
		$scope.userForm.permissions.push(permission)

	self.denyPermission = (permission) ->
		index = $scope.userForm.permissions.indexOf(permission)
		if index != -1
			$scope.userForm.permissions.splice(index, 1)

	self.isFormValid = () ->
		self.isUsernameFieldValid() && self.isPasswordFieldValid() && self.isConfirmPasswordFieldValid()

	self.submitForm = () ->
		service.update($scope.user.getId(), $scope.userForm.username, $scope.userForm.password, $scope.userForm.permissions, self.onUpdate)

	self.getPermissionDisplayString = (permission) ->
		words = permission.split("_")
		displayString = ""
		for word in words
			displayString += word.charAt(0) + word.substr(1, word.length - 1).toLowerCase() + " "
		displayString.trim()

	self.copyPermissions = (permissions) ->
		$scope.userForm.permissions.splice(0, $scope.userForm.permissions.length)
		for permission in permissions
			$scope.userForm.permissions.push(permission)

	self.onRead = (user) ->
		$scope.user = user
		self.copyPermissions(user.getPermissions())

	self.onUpdate = () ->
		self.init()
		self.disableForm()

	init: self.init
	isFormEnabled: self.isFormEnabled
	enableForm: self.enableForm
	disableForm: self.disableForm
	isUsernameFieldValid: self.isUsernameFieldValid
	isPasswordFieldValid: self.isPasswordFieldValid
	isConfirmPasswordFieldValid: self.isConfirmPasswordFieldValid
	isPermissionBeingGranted: self.isPermissionBeingGranted
	grantPermission: self.grantPermission
	denyPermission: self.denyPermission
	isFormValid: self.isFormValid
	submitForm: self.submitForm
	getPermissionDisplayString: self.getPermissionDisplayString

module.controller("UserController", ["$routeParams", "$scope", "$window", "UserService", controller])

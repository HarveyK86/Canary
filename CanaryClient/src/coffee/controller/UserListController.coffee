module = angular.module("org.canary.controller")

controller = ($scope, $window, service) ->
	self = this

	$scope.form =
		username: ""
		password: ""
		confirmPassword: ""
		enabled: false

	self.init = () ->
		service.readAll(self.onReadAll)

	self.isFormEnabled = () ->
		$scope.form.enabled

	self.enableForm = () ->
		$scope.form.username = ""
		$scope.form.password = ""
		$scope.form.confirmPassword = ""
		$scope.form.enabled = true

	self.disableForm = () ->
		$scope.form.username = ""
		$scope.form.password = ""
		$scope.form.confirmPassword = ""
		$scope.form.enabled = false

	self.isUsernameFieldValid = () ->
		$scope.form.username != null && $scope.form.username != ""

	self.isPasswordFieldValid = () ->
		$scope.form.password != null && $scope.form.password != ""

	self.isConfirmPasswordFieldValid = () ->
		$scope.form.confirmPassword == $scope.form.password

	self.isFormValid = () ->
		self.isUsernameFieldValid() && self.isPasswordFieldValid() && self.isConfirmPasswordFieldValid()

	self.submitForm = () ->
		service.create($scope.form.username, $scope.form.password, [], self.onCreate)

	self.goToUser = (user) ->
		$window.location.href = "index#/User/" + user.getId()
		$window.location.reload()

	self.delete = (user) ->
		service.delete(user, self.onDelete)

	self.onCreate = () ->
		self.init()
		self.disableForm()

	self.onReadAll = (userList) ->
		$scope.userList = userList

	self.onDelete = () ->
		self.init()

	init: self.init
	isFormEnabled: self.isFormEnabled
	enableForm: self.enableForm
	disableForm: self.disableForm
	isUsernameFieldValid: self.isUsernameFieldValid
	isPasswordFieldValid: self.isPasswordFieldValid
	isConfirmPasswordFieldValid: self.isConfirmPasswordFieldValid
	isFormValid: self.isFormValid
	submitForm: self.submitForm
	goToUser: self.goToUser
	delete: self.delete

module.controller("UserListController", ["$scope", "$window", "UserService", controller])

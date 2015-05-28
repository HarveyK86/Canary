module = angular.module("org.canary.controller")

controller = ($scope, repository) ->
	self = this

	$scope.addUser =
		username: ""
		password: ""
		confirmPassword: ""
		visible: false

	self.init = () ->
		self.readAll()

	self.showAddUser = () ->
		$scope.addUser =
			username: ""
			password: ""
			confirmPassword: ""
			visible: true

	self.hideAddUser = () ->
		$scope.addUser.visible = false

	self.isAddUserValid = () ->
		self.isUsernameValid() && self.isPasswordValid() && self.isConfirmPasswordValid()

	self.isUsernameValid = () ->
		$scope.addUser.username != null && $scope.addUser.username != ""

	self.isPasswordValid = () ->
		$scope.addUser.password != null && $scope.addUser.password != ""

	self.isConfirmPasswordValid = () ->
		$scope.addUser.confirmPassword == $scope.addUser.password

	self.create = (username, password) ->
		repository.create(username, password, self.onCreate)

	self.readAll = () ->
		repository.readAll(self.onReadAll)

	self.update = (userId, username, password) ->
		repository.update(userId, username, password, self.onUpdate)

	self.delete = (user) ->
		userId = user.getId()
		repository.delete(userId, self.onDelete)

	self.onReadAll = (users) ->
		$scope.users = users

	self.onCreate = () ->
		self.readAll()

	self.onUpdate = () ->
		self.readAll()

	self.onDelete = () ->
		self.readAll()

	self.init()
	showAddUser: self.showAddUser
	hideAddUser: self.hideAddUser
	isAddUserValid: self.isAddUserValid
	create: self.create
	update: self.update
	delete: self.delete

module.controller("UsersController", ["$scope", "UserRepository", controller])

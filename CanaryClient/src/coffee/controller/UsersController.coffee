module = angular.module("org.canary.controller")

controller = ($scope, service) ->
	self = this

	$scope.addUser =
		username: ""
		password: ""
		confirmPassword: ""
		permissions: []
		visible: false

	self.init = () ->
		self.readAll()

	self.showAddUser = () ->
		$scope.addUser =
			username: ""
			password: ""
			confirmPassword: ""
			permissions: []
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

	self.hasPermission = (permission) ->
		$scope.addUser.permissions.indexOf(permission) != -1

	self.addPermission = (permission) ->
		$scope.addUser.permissions.push(permission)

	self.removePermission = (permission) ->
		index = $scope.addUser.permissions.indexOf(permission)
		if index != -1
			$scope.addUser.permissions.splice(index, 1)

	self.getDisplay = (permission) ->
		words = permission.split("_")
		display = ""
		for word in words
			display += word.charAt(0) + word.substr(1, word.length - 1).toLowerCase() + " "
		display.trim()

	self.create = (username, password, permissions) ->
		service.create(username, password, permissions, self.onCreate)

	self.read = (userId) ->
		service.read(userId, self.onRead)

	self.readAll = () ->
		service.readAll(self.onReadAll)

	self.readCurrent = () ->
		service.readCurrent(self.onReadCurrent)

	self.update = (userId, username, password, permissions) ->
		service.update(userId, username, password, permissions, self.onUpdate)

	self.delete = (user) ->
		service.delete(user, self.onDelete)

	self.onCreate = () ->
		self.readAll()

	self.onRead = (user) ->
		$scope.user = user

	self.onReadAll = (users) ->
		$scope.users = users

	self.onReadCurrent = (currentUser) ->
		$scope.currentUser = currentUser

	self.onUpdate = () ->
		self.readAll()

	self.onDelete = () ->
		self.readAll()

	self.init()
	showAddUser: self.showAddUser
	hideAddUser: self.hideAddUser
	isAddUserValid: self.isAddUserValid
	isUsernameValid: self.isUsernameValid
	isPasswordValid: self.isPasswordValid
	isConfirmPasswordValid: self.isConfirmPasswordValid
	hasPermission: self.hasPermission
	addPermission: self.addPermission
	removedPermission: self.removePermission
	getDisplay: self.getDisplay
	create: self.create
	read: self.read
	readAll: self.readAll
	readCurrent: self.readCurrent
	update: self.update
	delete: self.delete

module.controller("UsersController", ["$scope", "UserService", controller])

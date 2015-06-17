module = angular.module("org.canary.repository")

repository = ($resource, $http) ->
	self = this

	self.resource = $resource("/canary-server/user/:id", id: "@id")
	self.currentUrl = "/canary-server/user/current"

	self.create = (username, password, permissions, callback) ->
		request = new self.resource()
		request.username = username
		request.password = password
		request.permissions = permissions
		request.$save(callback)

	self.read = (userId, callback) ->
		self.resource.get(id: userId, (response) ->
			user = new User()
			user.setId(response.id)
			user.setUsername(response.username)
			user.setPermissions(response.permissions)
			callback(user))

	self.readAll = (callback) ->
		self.resource.query((response) ->
			users = []
			for obj in response
				user = new User()
				user.setId(obj.id)
				user.setUsername(obj.username)
				user.setPermissions(obj.permissions)
				users.push(user)
			callback(users))

	self.readCurrent = (callback) ->
		$http.get(self.currentUrl).then((response) ->
			user = new User()
			user.setId(response.data.id)
			user.setUsername(response.data.username)
			user.setPermissions(response.data.permissions)
			callback(user))

	self.update = (userId, username, password, permissions, callback) ->
		request = new self.resource()
		request.id = userId
		request.username = username
		request.password = password
		request.permissions = permissions
		request.$save(id: userId, callback)

	self.delete = (userId, callback) ->
		request = new self.resource()
		request.$delete(id: userId, callback)

	create: self.create
	read: self.read
	readAll: self.readAll
	readCurrent: self.readCurrent
	update: self.update
	delete: self.delete

module.factory("UserRepository", ["$resource", "$http", repository])

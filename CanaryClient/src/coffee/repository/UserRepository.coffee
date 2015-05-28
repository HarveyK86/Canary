module = angular.module("org.canary.repository")

repository = ($resource) ->
	self = this

	self.resource = $resource("/canary-server/user/:id", id: "@id")

	self.create = (username, password, callback) ->
		request = new self.resource()
		request.username = username
		request.password = password
		request.$save(callback)

	self.read = (userId, callback) ->
		self.resource.get(id: userId, (response) ->
			user = new User()
			user.setId(response.id)
			user.setUsername(response.username)
			callback(username))

	self.readAll = (callback) ->
		self.resource.query((response) ->
			users = []
			for obj in response
				user = new User()
				user.setId(obj.id)
				user.setUsername(obj.username)
				users.push(user)
			callback(users))

	self.update = (userId, username, password, callback) ->
		request = new self.resource()
		request.id = userId
		request.username = username
		request.password = password
		request.$save(id: userId, callback)

	self.delete = (userId, callback) ->
		request = new self.resource()
		request.$delete(id: userId, callback)

	create: self.create
	read: self.read
	readAll: self.readAll
	update: self.update
	delete: self.delete

module.factory("UserRepository", ["$resource", repository])

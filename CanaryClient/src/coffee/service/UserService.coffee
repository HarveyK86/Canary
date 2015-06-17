module = angular.module("org.canary.service")

service = (repository) ->
	self = this

	self.create = (username, password, permissions, callback) ->
		repository.create(username, password, permissions, callback)

	self.read = (userId, callback) ->
		repository.read(userId, callback)

	self.readAll = (callback) ->
		repository.readAll(callback)

	self.readCurrent = (callback) ->
		repository.readCurrent(callback)

	self.update = (userId, username, password, permissions, callback) ->
		repository.update(userId, username, password, permissions, callback)

	self.delete = (user, callback) ->
		userId = user.getId();
		repository.delete(userId, callback)

	create: self.create
	read: self.read
	readAll: self.readAll
	readCurrent: self.readCurrent
	update: self.update
	delete: self.delete

module.factory("UserService", ["UserRepository", service])

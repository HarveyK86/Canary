module = angular.module("org.canary.service")

service = (repository) ->
	self = this

	self.create = (value, callback) ->
		repository.create(value, callback)

	self.read = (messageId, callback) ->
		repository.read(messageId, callback)

	self.readAll = (callback) ->
		repository.readAll(callback)

	self.update = (messageId, value, callback) ->
		repository.update(messageId, value, callback)

	self.delete = (message, callback) ->
		messageId = message.getId();
		repository.delete(messageId, callback)

	create: self.create
	read: self.read
	readAll: self.readAll
	update: self.update
	delete: self.delete

module.factory("MessageService", ["MessageRepository", service])

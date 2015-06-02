module = angular.module("org.canary.repository")

repository = ($resource) ->
    self = this

    self.resource = $resource("/canary-server/message/:id", id: "@id")

    self.create = (value, callback) ->
        request = new self.resource()
        request.value = value
        request.$save(callback)

    self.read = (messageId, callback) ->
        self.resource.get(id: messageId, (response) ->
            message = new Message()
            message.setId(response.id)
            message.setValue(response.value)
            callback(message))

    self.readAll = (callback) ->
        self.resource.query((response) ->
            messages = []
            for obj in response
                message = new Message()
                message.setId(obj.id)
                message.setValue(obj.value)
                messages.push(message)
            callback(messages))

    self.update = (messageId, value, callback) ->
        request = new self.resource()
        request.id = messageId
        request.value = value
        request.$save(id: messageId, callback)

    self.delete = (messageId, callback) ->
        request = new self.resource()
        request.$delete(id: messageId, callback)

    create: self.create
    read: self.read
    readAll: self.readAll
    update: self.update
    delete: self.delete

module.factory("MessageRepository", ["$resource", repository])

module = angular.module("org.canary.repository")

repository = ($resource) ->
    self = this

    self.resource = $resource("http://localhost:8080/canary-server/canary/:id", id: "@id")

    self.create = (message, callback) ->
        canary = new self.resource()
        canary.message = message
        canary.$save(callback)

    self.read = (canaryId, callback) ->
        self.resource.get(id: canaryId, (result) ->
            canary = new Canary
            canary.setId(result.id)
            canary.setMessage(result.message)
            callback(canary) )

    self.readAll = (callback) ->
        self.resource.query((results) ->
            toReturn = []
            for result in results
                canary = new Canary()
                canary.setId(result.id)
                canary.setMessage(result.message)
                toReturn.push(canary)
            callback(toReturn) )

    self.update = (canaryId, message, callback) ->
        canary = new self.resource()
        canary.id = canaryId
        canary.message = message
        canary.$save(id: canaryId, callback)

    self.delete = (canaryId, callback) ->
        canary = new self.resource()
        canary.$delete(id: canaryId, callback)

    create : self.create
    read: self.read
    readAll: self.readAll
    update: self.update
    delete: self.delete

module.factory("CanaryRepository", ["$resource", repository])

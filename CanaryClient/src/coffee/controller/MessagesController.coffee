module = angular.module("org.canary.controller")

controller = ($scope, service) ->
    self = this

    $scope.addMessage =
        value: ""
        visible: false

    self.init = () ->
        self.readAll()

    self.showAddMessage = () ->
        $scope.addMessage =
            value: ""
            visible: true

    self.hideAddMessage = () ->
        $scope.addMessage.visible = false

    self.isAddMessageValid = () ->
        $scope.addMessage.value != null && $scope.addMessage.value != ""

    self.create = (value) ->
        service.create(value, self.onCreate)

    self.read = (messageId) ->
        service.read(messageId, self.onRead)

    self.readAll = () ->
        service.readAll(self.onReadAll)

    self.update = (messageId, value) ->
        service.update(messageId, value, self.onUpdate)

    self.delete = (message) ->
        service.delete(message, self.onDelete)

    self.onCreate = () ->
        self.readAll() 

    self.onRead = (message) ->
        $scope.message = message

    self.onReadAll = (messages) ->
        $scope.messages = messages

    self.onUpdate = () ->
        self.readAll() 

    self.onDelete = () ->
        self.readAll()

    self.init()
    showAddMessage: self.showAddMessage
    hideAddMessage: self.hideAddMessage
    isAddMessageValid: self.isAddMessageValid
    create: self.create
    read: self.read
    readAll: self.readAll
    update: self.update
    delete: self.delete

module.controller("MessagesController", ["$scope", "MessageService", controller])

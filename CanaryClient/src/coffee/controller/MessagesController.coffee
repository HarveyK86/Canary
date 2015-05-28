module = angular.module("org.canary.controller")

controller = ($scope, repository) ->
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
        repository.create(value, self.onCreate)

    self.readAll = () ->
        repository.readAll(self.onReadAll)

    self.update = (messageId, value) ->
        repository.update(messageId, value, self.onUpdate)

    self.delete = (message) ->
        messageId = message.getId()
        repository.delete(messageId, self.onDelete)

    self.onReadAll = (messages) ->
        $scope.messages = messages

    self.onCreate = () ->
        self.readAll() 

    self.onUpdate = () ->
        self.readAll() 

    self.onDelete = () ->
        self.readAll()

    self.init()
    showAddMessage: self.showAddMessage
    hideAddMessage: self.hideAddMessage
    isAddMessageValid: self.isAddMessageValid
    create: self.create
    update: self.update
    delete: self.delete

module.controller("MessagesController", ["$scope", "MessageRepository", controller])

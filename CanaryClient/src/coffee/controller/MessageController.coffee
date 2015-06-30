module = angular.module("org.canary.controller")

controller = ($scope, service) ->
    self = this

    $scope.messageForm =
        value: ""
        enabled: false

    self.isFormEnabled = () ->
        $scope.messageForm.enabled

    self.enableForm = (value) ->
        $scope.messageForm.value = value
        $scope.messageForm.enabled = true

    self.disableForm = () ->
        $scope.messageForm.value = ""
        $scope.messageForm.enabled = false

    self.isValueFieldValid = () ->
        $scope.messageForm.value != null && $scope.messageForm.value != ""

    self.isFormValid = () ->
        self.isValueFieldValid()

    self.submitForm = (messageId) ->
        service.update(messageId, $scope.messageForm.value, self.onUpdate)

    self.delete = (message) ->
        service.delete(message, self.onDelete)

    self.onUpdate = () ->
        $scope.ctrl.init()
        self.disableForm()

    self.onDelete = () ->
        $scope.ctrl.init()
        self.disableForm()

    isFormEnabled: self.isFormEnabled
    enableForm: self.enableForm
    disableForm: self.disableForm
    isValueFieldValid: self.isValueFieldValid
    isFormValid: self.isFormValid
    submitForm: self.submitForm
    delete: self.delete

module.controller("MessageController", ["$scope", "MessageService", controller])

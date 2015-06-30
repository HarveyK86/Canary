module = angular.module("org.canary.controller")

controller = ($scope, service) ->
    self = this

    $scope.form =
        value: ""
        enabled: false

    self.init = () ->
        service.readAll(self.onReadAll)

    self.isFormEnabled = () ->
        $scope.form.enabled

    self.enableForm = () ->
        $scope.form.value = ""
        $scope.form.enabled = true

    self.disableForm = () ->
        $scope.form.value = ""
        $scope.form.enabled = false

    self.isValueFieldValid = () ->
        $scope.form.value != null && $scope.form.value != ""

    self.isFormValid = () ->
        self.isValueFieldValid()

    self.submitForm = () ->
        service.create($scope.form.value, self.onCreate)

    self.onCreate = () ->
        self.init()
        self.disableForm()

    self.onReadAll = (messageList) ->
        $scope.messageList = messageList

    init: self.init()
    isFormEnabled: self.isFormEnabled
    enableForm: self.enableForm
    disableForm: self.disableForm
    isValueFieldValid: self.isValueFieldValid
    isFormValid: self.isFormValid
    submitForm: self.submitForm

module.controller("MessageListController", ["$scope", "MessageService", controller])

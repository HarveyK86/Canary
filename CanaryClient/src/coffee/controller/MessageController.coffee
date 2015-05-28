module = angular.module("org.canary.controller")

controller = ($scope) ->
    self = this

    $scope.editMessage =
        value: ""
        visible: false

    self.showEditMessage = (value) ->
        $scope.editMessage =
            value: value
            visible: true

    self.hideEditMessage = () ->
        $scope.editMessage.visible = false

    self.isEditMessageValid = () ->
        $scope.editMessage.value != null && $scope.editMessage.value != ""

    showEditMessage: self.showEditMessage
    hideEditMessage: self.hideEditMessage
    isEditMessageValid: self.isEditMessageValid

module.controller("MessageController", ["$scope", controller])

module = angular.module("org.canary.controller")

controller = ($scope) ->
  self = this

  self.editFieldVisible = false

  $scope.edit = message : ""

  self.showEditField = (message) ->
    $scope.edit = message: message
    self.editFieldVisible = true

  self.hideEditField = () ->
    self.editFieldVisible = false

  self.isEditFieldVisible = () ->
    self.editFieldVisible

  self.isUpdatedMessageValid = () ->
    $scope.edit.message != null && $scope.edit.message != ""

  showEditField: self.showEditField
  hideEditField: self.hideEditField
  isEditFieldVisible: self.isEditFieldVisible
  isUpdatedMessageValid: self.isUpdatedMessageValid

module.controller("CanaryController", ["$scope", controller])

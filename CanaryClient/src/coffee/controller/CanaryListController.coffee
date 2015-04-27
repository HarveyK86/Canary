module = angular.module("org.canary.controller")

controller = ($scope, canaryRepository) ->
    self = this

    self.addPanelVisible = false;

    $scope.add = message: ""
    $scope.canaries

    self.showAddPanel = () ->
        $scope.add = message: ""
        self.addPanelVisible = true

    self.hideAddPanel = () ->
        self.addPanelVisible = false

    self.isAddPanelVisible = () ->
        self.addPanelVisible

    self.isNewMessageValid = () ->
        $scope.add.message != null && $scope.add.message != ""

    self.create = (message) ->
        canaryRepository.create(message, () ->
            self.readAll() )

    self.readAll = () ->
        canaryRepository.readAll((results) ->
            $scope.canaries = results.reverse() )

    self.update = (canaryId, message) ->
        canaryRepository.update(canaryId, message, () ->
            self.readAll() )

    self.delete = (canary) ->
        canaryRepository.delete(canary.getId(), () ->
            self.readAll() )

    self.readAll()

    showAddPanel: self.showAddPanel
    hideAddPanel: self.hideAddPanel
    isAddPanelVisible: self.isAddPanelVisible
    isNewMessageValid: self.isNewMessageValid
    create: self.create
    readAll: self.readAll
    update: self.update
    delete: self.delete

module.controller("CanaryListController", ["$scope", "CanaryRepository", controller])

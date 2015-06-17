module = angular.module("org.canary.controller")

controller = ($scope, $location, $window, userService) ->
    self = this

    self.messages =
        name: "Messages"
        permission: "READ_MESSAGE"

    self.users =
        name: "Users"
        permission: "READ_USER"

    self.logoutError =
        name: "LogoutError"
        template: "tpl/logout-error.tpl.html"

    $scope.pages = [
        self.messages,
        self.users
    ]

    $scope.alerts = [
        self.logoutError
    ]
        
    self.init = () ->
        path = $location.path()
        found = false
        for page in $scope.pages
            if "/" + page.name == path
                self.setActive(page)
                found = true
        if found
            parameters = $location.search()
            for alert in $scope.alerts
                if alert.name == parameters.alert
                    self.setAlert(alert)
        else
            self.goToPage(self.messages)
        userService.readCurrent(self.onReadCurrent)

    self.goToHome = () ->
        $window.location.href = "index#/Home"
        $window.location.reload()

    self.goToPage = (page) ->
        $window.location.href = "index#/" + page.name
        $window.location.reload()

    self.setActive = (page) ->
        for current in $scope.pages
            current.active = current == page

    self.setAlert = (alert) ->
        for current in $scope.alerts
            current.visible = current == alert

    self.onReadCurrent = (currentUser) ->
        $scope.currentUser = currentUser

    init = self.init
    goToHome = self.goToHome
    goToPage = self.goToPage

module.controller("IndexController", ["$scope", "$location", "$window", "UserService", controller])

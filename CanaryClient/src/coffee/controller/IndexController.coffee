module = angular.module("org.canary.controller")

controller = ($scope, $location, $http, $window, userService) ->
    self = this

    self.messagesPage =
        name: "Messages"
        readPermission: "READ_MESSAGE"
        enabled: false

    self.usersPage =
        name: "Users"
        readPermission: "READ_USER"
        enabled: false

    self.logoutFailureAlert =
        name: "LogoutFailure"
        template: "tpl/logout-failure.tpl.html"

    $scope.availablePages = [
        self.messagesPage,
        self.usersPage
    ]

    $scope.availableAlerts = [
        self.logoutFailureAlert
    ]
        
    self.init = () ->
        path = $location.path()
        if path == ""
            self.goToHome()
        else
            for page in $scope.availablePages
                page.enabled = path == "/" + page.name
            parameters = $location.search()
            for alert in $scope.availableAlerts
                alert.enabled = parameters.alert == alert.name
            userService.readCurrent(self.onReadCurrent)

    self.goToHome = () ->
        self.goToPage(self.messagesPage)

    self.goToPage = (page) ->
        $window.location.href = "index#/" + page.name
        $window.location.reload()

    self.goToCurrentUser = () ->
        $window.location.href = "index#/User/" + $scope.currentUser.getId()
        $window.location.reload()

    self.logout = () ->
        post =
            method: "POST"
            url: "logout"

        $http(post).success(self.onLogoutSuccess).error(self.onLogoutFailure)

    self.onLogoutSuccess = () ->
        $window.location.href = "login#?alert=LogoutSuccess"

    self.onLogoutFailure = () ->
        path = $location.path()
        $window.location.href = "index#" + path + "?alert=LogoutError"
        $window.location.reload()

    self.onReadCurrent = (currentUser) ->
        $scope.currentUser = currentUser

    init: self.init
    goToHome: self.goToHome
    goToPage: self.goToPage
    goToCurrentUser: self.goToCurrentUser
    logout: self.logout

module.controller("IndexController", ["$scope", "$location", "$http", "$window", "UserService", controller])

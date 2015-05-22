config = ($routeProvider) ->
	$routeProvider.when("/loginError", templateUrl: "tpl/login-error.tpl.html")
	$routeProvider.when("/logoutSuccess", templateUrl: "tpl/logout-success.tpl.html")
	$routeProvider.when("/logoutError", templateUrl: "tpl/logout-error.tpl.html")

angular.module("org.canary", ["org.canary.controller", "org.canary.repository", "ngRoute", "ngResource"]).config(["$routeProvider", config])
angular.module("org.canary.controller", ["org.canary.repository"])
angular.module("org.canary.repository", ["ngResource"])

config = ($routeProvider) ->
	$routeProvider.when("/Messages", templateUrl: "tpl/messages.tpl.html")
	$routeProvider.when("/Users", templateUrl: "tpl/users.tpl.html")

angular.module("org.canary", ["org.canary.controller", "org.canary.service", "org.canary.repository", "ang-drag-drop", "ngRoute", "ngResource"]).config(["$routeProvider", config])
angular.module("org.canary.controller", ["org.canary.service"])
angular.module("org.canary.service", ["org.canary.repository"])
angular.module("org.canary.repository", ["ngResource"])

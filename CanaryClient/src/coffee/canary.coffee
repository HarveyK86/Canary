angular.module("org.canary", ["org.canary.controller", "org.canary.repository", "ngResource"])
angular.module("org.canary.controller", ["org.canary.repository"])
angular.module("org.canary.repository", ["ngResource"])

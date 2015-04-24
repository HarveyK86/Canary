angular.module("org.canary", ["org.canary.controller", "org.canary.service", "org.canary.repository", "ngResource"]);
angular.module("org.canary.controller", ["org.canary.service"]);
angular.module("org.canary.service", ["org.canary.repository"]);
angular.module("org.canary.repository", ["ngResource"]);

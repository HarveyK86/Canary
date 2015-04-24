var module = angular.module("org.canary.controller");

var controller = function($log, $scope, canaryService) {

	$log.log("CanaryListController : constructor[$log=" + $log + ", $scope=" + $scope + ", canaryService=" + canaryService + "]");

	var self = this;

	self.addPanelVisible = false;

	$scope.add = { message: "" };
	$scope.canaries;

	self.showAddPanel = function() {

		$log.log("CanaryListController : showAddPanel");

		$scope.add = { message: "" };
		self.addPanelVisible = true;
	};

	self.hideAddPanel = function() {

		$log.log("CanaryListController : hideAddPanel");

		self.addPanelVisible = false;
	};

	self.isAddPanelVisible = function() {

		$log.log("CanaryListController : isAddPanelVisible");

		return self.addPanelVisible;
	};

	self.isNewMessageValid = function() {

		$log.log("CanaryListController : isNewMessageValid");

		return $scope.add.message != null && $scope.add.message != ""; 
	};

	self.create = function(message) {

		$log.log("CanaryListController : create[message=" + message + "]");

		canaryService.create(message, function() {
			self.readAll();
		});
	};

	self.readAll = function() {

		$log.log("CanaryListController : readAll");

		canaryService.readAll(function(results) {
			$scope.canaries = results.reverse();
		});
	};

	self.update = function(canaryId, message) {

		$log.log("CanaryListController : update[canaryId=" + canaryId + ", message=" + message + "]");

		canaryService.update(canaryId, message, function() {
			self.readAll();
		});
	};

	self.delete = function(canary) {

		$log.log("CanaryListController : delete[canary=" + canary + "]");

		canaryService.delete(canary, function() {
			self.readAll();
		});
	};

	self.readAll();

	return {

		showAddPanel: self.showAddPanel,
		hideAddPanel: self.hideAddPanel,
		isAddPanelVisible : self.isAddPanelVisible,
		isNewMessageValid : self.isNewMessageValid,

		create: self.create,
		readAll: self.readAll,
		update: self.update,
		delete: self.delete
	};

};

module.controller("CanaryListController", ["$log", "$scope", "CanaryService", controller]);

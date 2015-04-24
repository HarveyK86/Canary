var module = angular.module("org.canary.controller");

var controller = function($log, $scope) {

	$log.log("CanaryController : constructor[$log=" + $log + "]");

	var self = this;

	self.editFieldVisible = false;

	$scope.edit = { message: "" };

	self.showEditField = function(message) {

		$log.log("CanaryController : showEditField[message=" + message + "]");

		$scope.edit = { message: message };
		self.editFieldVisible = true;
	};

	self.hideEditField = function() {

		$log.log("CanaryController : hideEditField");

		self.editFieldVisible = false;
	};

	self.isEditFieldVisible = function() {

		$log.log("CanaryController : isEditFieldVisible");

		return self.editFieldVisible;
	};

	self.isUpdatedMessageValid = function() {

		$log.log("CanaryController : isUpdatedMessageValid");

		return $scope.edit.message != null && $scope.edit.message != "";
	};

	return {

		showEditField: self.showEditField,
		hideEditField: self.hideEditField,
		isEditFieldVisible: self.isEditFieldVisible,
		isUpdatedMessageValid: self.isUpdatedMessageValid
	};

};

module.controller("CanaryController", ["$log", "$scope", controller]);

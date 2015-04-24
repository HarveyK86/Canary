var module = angular.module("org.canary.service");

var service = function($log, canaryRepository) {

	$log.log("CanaryService : constructor[$log=" + $log + ", canaryRepository=" + canaryRepository + "]");

	var self = this;

	self.create = function(message, callback) {

		$log.log("CanaryService : create[message=" + message + ", callback=" + (callback != null) + "]");

		return canaryRepository.create(message, callback);
	};

	self.read = function(canaryId, callback) {

		$log.log("CanaryService : read[canaryId=" + canaryId + ", callback=" + (callback != null) + "]");

		canaryRepository.read(canaryId, callback);
	};

	self.readAll = function(callback) {

		$log.log("CanaryService : readAll[callback=" + (callback != null) + "]");

		canaryRepository.readAll(callback);
	};

	self.update = function(canaryId, message, callback) {

		$log.log("CanaryService : update[canaryId=" + canaryId + ", message=" + message + ", callback=" + callback + "]");
		
		return canaryRepository.update(canaryId, message, callback);	
	};

	self.delete = function(canary, callback) {

		$log.log("CanaryService : delete[canary=" + canary + ", callback=" + callback + "]");

		return canaryRepository.delete(canary.id, callback);
	};

	return {

		create: self.create,
		read: self.read,
		readAll: self.readAll,
		update: self.update,
		delete: self.delete
	};

};

module.factory("CanaryService", ["$log", "CanaryRepository", service]);

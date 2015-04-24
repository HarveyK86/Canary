var module = angular.module("org.canary.repository");

var repository = function($log, $resource) {

	$log.log("CanaryRepository : constructor[$log=" + $log + ", $resource=" + ($resource != null) + "]");

	var self = this;

	self.resource = $resource("http://localhost:8080/canary-server/canary/:id", { id: "@id" }, { update: { method: "POST" } });

	self.create = function(message, callback) {

		$log.log("CanaryRepository : create[message=" + message + ", callback=" + (callback != null) + "]");

		var canary = new self.resource();

		canary.message = message;

		canary.$save(callback);
	};

	self.read = function(canaryId, callback) {

		$log.log("CanaryRepository : read[canaryId=" + canaryId + ", callback=" + (callback != null) + "]");

		self.resource.get({ id: canaryId }, function(result) {

			var canary = new Canary();

			canary.id = result.id;
			canary.message = result.message;

			callback(canary);
		});
	};

	self.readAll = function(callback) {

		$log.log("CanaryRepository : readAll[callback=" + (callback != null) + "]");

		self.resource.query(function(results) {

			var toReturn = [];

			results.forEach(function(result) {

				var canary = new Canary();

				canary.id = result.id;
				canary.message = result.message;

				toReturn.push(canary);
			});

			callback(toReturn);
		});
	};

	self.update = function(canaryId, message, callback) {

		$log.log("CanaryRepository : update[canaryId=" + canaryId + ", message=" + message + ", callback=" + (callback != null) + "]");

		var canary = new self.resource();

		canary.id = canaryId;
		canary.message = message;

		canary.$update({ id: canaryId }, callback);
	};

	self.delete = function(canaryId, callback) {

		$log.log("CanaryRepository : delete[canaryId=" + canaryId + ", canary=" + canary + ", callback=" + callback + "]");

		var canary = new self.resource();

		canary.$delete({ id: canaryId }, callback);
	};

	return {

		create: self.create,
		read: self.read,
		readAll: self.readAll,
		update: self.update,
		delete: self.delete
	};

};

module.factory("CanaryRepository", ["$log", "$resource", repository]);

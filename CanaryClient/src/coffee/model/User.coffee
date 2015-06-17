User = () ->
	self = this

	self.id
	self.username
	self.permissions

	self.getId = () ->
		self.id

	self.setId = (userId) ->
		self.id = userId

	self.getUsername = () ->
		self.username

	self.setUsername = (username) ->
		self.username = username

	self.getPermissions = () ->
		self.permissions

	self.setPermissions = (permissions) ->
		self.permissions = permissions

	self.hasPermission = (permission) ->
		self.permissions.indexOf(permission) != -1

	getId: self.getId
	setId: self.setId
	getUsername: self.getUsername
	setUsername: self.setUsername
	getPermissions: self.getPermissions
	setPermissions: self.setPermissions
	hasPermission: self.hasPermission

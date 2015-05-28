User = () ->
	self = this

	self.id
	self.username

	self.getId = () ->
		self.id

	self.setId = (userId) ->
		self.id = userId

	self.getUsername = () ->
		self.username

	self.setUsername = (username) ->
		self.username = username

	getId: self.getId
	setId: self.setId
	getUsername: self.getUsername
	setUsername: self.setUsername

window.Canary = () ->
  self = this

  self.id
  self.message

  self.getId = () ->
    self.id

  self.setId = (canaryId) ->
    self.id = canaryId

  self.getMessage = () ->
    self.message

  self.setMessage = (message) ->
    self.message = message

  getId: self.getId
  setId: self.setId
  getMessage: self.getMessage
  setMessage: self.setMessage

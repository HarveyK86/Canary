Message = () ->
    self = this

    self.id
    self.value

    self.getId = () ->
        self.id

    self.setId = (messageId) ->
        self.id = messageId

    self.getValue = () ->
        self.value

    self.setValue = (value) ->
        self.value = value

    getId: self.getId
    setId: self.setId
    getValue: self.getValue
    setValue: self.setValue

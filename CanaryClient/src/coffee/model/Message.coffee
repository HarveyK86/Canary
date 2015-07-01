Message = () ->
    self = this

    self.id
    self.author
    self.value

    self.getId = () ->
        self.id

    self.setId = (messageId) ->
        self.id = messageId

    self.getAuthor = () ->
        self.author

    self.setAuthor = (author) ->
        self.author = author

    self.getValue = () ->
        self.value

    self.setValue = (value) ->
        self.value = value

    getId: self.getId
    setId: self.setId
    getAuthor: self.getAuthor
    setAuthor: self.setAuthor
    getValue: self.getValue
    setValue: self.setValue

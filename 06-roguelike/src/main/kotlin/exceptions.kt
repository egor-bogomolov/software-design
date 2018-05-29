import model.characters.items.Item

class InvalidArgumentException(message: String) : Exception(message)

class ItemNotExist(item: Item) : Exception("Item '${item.title}' doesn't exist.")
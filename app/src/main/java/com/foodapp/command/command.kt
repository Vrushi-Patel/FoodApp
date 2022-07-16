import com.foodapp.models.Food

enum class OperationType {
    AddToCart, RemoveFromCart,
}

interface Command {
    fun execute()
    fun unExecute()
}

// command
class Operation(private val cartItems: MutableList<Food>) {
    fun operation(
        operation: OperationType,
        foodItem: Food,
        product: Food?,
    ) {
        when (operation) {
            OperationType.AddToCart -> {
                cartItems.add(foodItem)
            }
            OperationType.RemoveFromCart -> {
                cartItems.remove(foodItem)
            }
        }
    }
}

// integrate command and composite
class UserOperations() {

    val cartItems: MutableList<Food> = mutableListOf()
    private val commands: MutableList<Command> = mutableListOf()

    fun performOperation(operation: OperationType, foodItem: Food, product: Food?) {
        val command = PerformCommands(Operation(cartItems), operation, foodItem, product)
        commands.add(command)
        command.execute()
    }

    fun reverseOperation(operation: OperationType, foodItem: Food, product: Food?) {
        val command = PerformCommands(Operation(cartItems), operation, foodItem, product)
        commands.remove(command)
        command.unExecute()
    }
}

class PerformCommands(
    private val operations: Operation,
    private val operation: OperationType,
    private val foodItem: Food,
    private val product: Food?,
) : Command {
    override fun execute() {
        operations.operation(operation, foodItem, product)
    }

    override fun unExecute() {
        operations.operation(undo(operation), foodItem, product)
    }

    private fun undo(operation: OperationType): OperationType {
        return when (operation) {
            OperationType.AddToCart -> OperationType.RemoveFromCart
            OperationType.RemoveFromCart -> OperationType.AddToCart
        }
    }
}

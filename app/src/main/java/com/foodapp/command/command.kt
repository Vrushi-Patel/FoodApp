package food_app_assignment.command

import food_app_assignment.models.*

enum class OperationType {
    AddIngredient, RemoveIngredient, AddProduct, RemoveProduct, AddToCart, RemoveFromCart,
}

interface Command {
    fun execute()
    fun unExecute()
}

// command
class Operation(private val cartItems: MutableList<Food>) {
    fun operation(operation: OperationType, foodItem: Food, product: Food?) {
        when (operation) {
            OperationType.AddIngredient -> {
                (foodItem as Product).ingredients.add(product as Ingredient)
            }
            OperationType.RemoveIngredient -> {
                (foodItem as Product).ingredients.remove(product as Ingredient)
            }
            OperationType.RemoveProduct -> {
                (foodItem as Meal).products.remove(product as Product)
            }
            OperationType.AddProduct -> {
                (foodItem as Meal).products.add(product as Product)
            }
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
class UserOperations {
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

/*
    builder :
        director -> assembles product ( burger )
        media builder -> has all the abstractions which narrates how to build the product
                      -> partcipents says we can retrive the build product from here
        question?
        where command should sneak in? -
        where composition should sneak in?






*/
class PerformCommands(
    private val operations: Operation,
    private val operation: OperationType,
    private val foodItem: Food,
    private val product: Food?
) : Command {
    override fun execute() {
        operations.operation(operation, foodItem, product)
    }

    override fun unExecute() {
        operations.operation(undo(operation), foodItem, product)
    }

    private fun undo(operation: OperationType): OperationType {
        return when (operation) {
            OperationType.AddIngredient -> OperationType.RemoveIngredient
            OperationType.RemoveIngredient -> OperationType.AddIngredient
            OperationType.AddProduct -> OperationType.RemoveProduct
            OperationType.RemoveProduct -> OperationType.AddProduct
            OperationType.AddToCart -> OperationType.RemoveFromCart
            OperationType.RemoveFromCart -> OperationType.AddToCart
        }
    }
}

package food_app_assignment.models

interface Food {
    var calories: Double
    var price: Double
    var name: String
    var url: String
//    var component : Food
}

class Ingredient : Food {
    override var url: String = ""
    override var calories: Double = 0.0
    override var price: Double = 0.0
    override var name: String = ""
    override fun toString(): String {
        return "{ calories = $calories, price = $price, name = $name, url = $url }"
    }
}

// leaf component
class Product : Food {
    override var url: String = ""
    override var calories: Double = 0.0
    override var price: Double = 0.0
    override var name: String = ""
    var ingredients: MutableList<Ingredient> = mutableListOf()
    override fun toString(): String {
        return "{ calories = $calories, price = $price, name = $name, url = $url, ingredients = $ingredients }"
    }
}

class Meal : Food {
    override var url: String = ""
    override var calories = 0.0
    override var price = 0.0
    override var name = ""
    var isCombo: Boolean = false

    //    var products: Food
    var products: MutableList<Product> = mutableListOf()
    override fun toString(): String {
        return "{ calories = $calories, price = $price, name = $name, isCombo = $isCombo, url = $url, products = $products }"
    }
}

class Foods : ArrayList<Food>() {

    fun product(block: Product.() -> Unit) {
        val product = Product().apply(block)
        add(product)  // changed
    }

    fun ingredient(block: Ingredient.() -> Unit) {
        val product = Ingredient().apply(block)
        add(product)  // changed
    }

}
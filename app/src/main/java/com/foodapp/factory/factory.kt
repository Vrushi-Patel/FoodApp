import com.foodapp.models.Food
import com.foodapp.models.Foods
import com.foodapp.models.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import com.foodapp.room.entities.Ingredient as RoomIngredient

interface FoodFactory {
    fun makeIngredient(
        function: Ingredient.() -> Unit
    ): Ingredient

    fun convertIngredient(ingredient: RoomIngredient): Ingredient

    fun convertFood(food: FoodIngredientRelation): Ingredient
}

class FoodFactoryImpl : FoodFactory {
    override infix fun makeIngredient(function: Ingredient.() -> Unit): Ingredient {
        return Ingredient().apply(function)
    }

    override fun convertIngredient(ingredient: RoomIngredient): Ingredient {
        return makeIngredient {
            name = ingredient.product.name
            url = ingredient.product.url
            calories = ingredient.product.calories
            price = ingredient.product.price
        }
    }

    override fun convertFood(food: FoodIngredientRelation): Ingredient {
        return makeIngredient {
            name = food.food.product.name
            url = food.food.product.url
            calories = food.food.product.calories
            price = food.food.product.price
            food.ingredients?.let {
                food.ingredients.forEach {
                    ingredients.add(convertIngredient(it))
                }
            }
        }
    }
}

fun ingredientsList(function: Foods.() -> Unit): MutableList<Food> {
    val ingredient = mutableListOf<Food>()  // changed
    val foods = Foods().apply(function)
    ingredient.addAll(foods)
    return ingredient
}

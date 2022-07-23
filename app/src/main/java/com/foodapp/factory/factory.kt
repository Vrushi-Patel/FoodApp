import com.foodapp.models.Food
import com.foodapp.models.Ingredient
import com.foodapp.room.entities.Food as entity

interface FoodFactory {
    fun makeIngredient(
        function: Ingredient.() -> Unit
    ): Ingredient

//    fun convertEntity(food: entity): Food
}

class FoodFactoryImpl : FoodFactory {
    override infix fun makeIngredient(function: Ingredient.() -> Unit): Ingredient {
        return Ingredient().apply(function)
    }

//    override fun convertEntity(food: com.foodapp.room.entities.Food): Food {
//        return makeIngredient {
//            url = food.url
//            calories = food.calories
//            price = food.price
//            name = food.name
//            food.ingredients.forEach() {
////                ingredients.add(convertEntity(it))
//            }
//        }
//    }
}

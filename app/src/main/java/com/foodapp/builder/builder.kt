package com.foodapp.builder

import FoodFactory
import FoodFactoryImpl
import com.foodapp.models.Ingredient

interface IngredientComboMealBuilder {
    val factory: FoodFactory
    fun makeCheeseSlice(): Ingredient
    fun makeLettuce(): Ingredient
    fun makeAluPatty(): Ingredient
    fun makeChickenPatty(): Ingredient
    fun makeCoke(): Ingredient
    fun makeFries(): Ingredient
    fun makeVegBurger(): Ingredient
    fun makeNonVegBurger(): Ingredient
    fun makeIceCream(): Ingredient
    fun makeVegBurgerMeal(): Ingredient
    fun makeNonVegBurgerMeal(): Ingredient
    fun makeBurgerCombo(): Ingredient
}

class FoodBuilderImpl(override val factory: FoodFactoryImpl) :
    IngredientComboMealBuilder {

    override fun makeCheeseSlice(): Ingredient {
        return factory makeIngredient {
            url =
                "https://pngimg.com/uploads/cheese/cheese_PNG7.png"
            calories = 110.0
            price = 16.0
            name = "Cheese Slice"
        }
    }

    override fun makeLettuce(): Ingredient {
        return factory makeIngredient {
            url =
                "https://purepng.com/public/uploads/large/purepng.com-romaine-lettucevegetablessalad-leaf-romaine-lettuce-leaves-cos-lettuce-941524703216ytfqu.png"
            calories = 54.0
            price = 8.0
            name = "Lettuce"
        }
    }

    override fun makeAluPatty(): Ingredient {
        return factory makeIngredient {
            url =
                "https://biggiesburger.com/wp-content/uploads/2020/06/4098325-download-burger-bun-top-transparent-png-on-yellow-images-360-png-bun-466_580_preview.png"
            calories = 240.0
            price = 8.0
            name = "Alu Patty"
        }
    }

    override fun makeChickenPatty(): Ingredient {
        return factory makeIngredient {
            url =
                "https://clipground.com/images/hamburger-patty-png-9.png"
            calories = 290.0
            price = 8.0
            name = "Chicken Patty"
        }
    }

    override fun makeCoke(): Ingredient {
        return factory makeIngredient {
            url =
                "https://www.freeiconspng.com/uploads/bottle-coca-cola-png-transparent-2.png"
            calories = 140.0
            price = 40.0
            name = "Coke"
        }
    }

    override fun makeFries(): Ingredient {
        return factory makeIngredient {
            url =
                "https://th.bing.com/th/id/R.cf5d3ff8380a3a6124f7aa74cc767b58?rik=ViXSzVW%2bYuFytw&riu=http%3a%2f%2fpngimg.com%2fuploads%2ffries%2ffries_PNG77.png&ehk=f%2fVVz9hXHJxj4J4mNBDFYY%2b7iL7iUZCs6mvynYFs5ss%3d&risl=&pid=ImgRaw&r=0"
            calories = 400.0
            price = 120.0
            name = "Fries"
        }
    }

    override fun makeVegBurger(): Ingredient {
        return factory makeIngredient {
            url =
                "https://th.bing.com/th/id/R.4fbde595384ea1c9833df3c9468588be?rik=LU6gYxH7raAxIQ&riu=http%3a%2f%2fclipart-library.com%2fimg%2f1472879.png&ehk=5KNiNQZJeP9P6ldx9r2lleyi5Cixe72gJSafH%2fphAyU%3d&risl=&pid=ImgRaw&r=0"
            name = "Veg Burger"
            ingredients =
                mutableListOf(
                    makeCheeseSlice(),
                    makeLettuce(),
                    makeAluPatty(),
                )
            returnCalories()
            returnPrice()
        }
    }

    override fun makeNonVegBurger(): Ingredient {
        return factory makeIngredient {
            url =
                "https://toppng.com/public/uploads/thumbnail/chicken-burger-non-veg-burger-11563048667o9az9u5jng.png"
            name = "NonVeg Burger"
            ingredients = mutableListOf(
                makeCheeseSlice(),
                makeLettuce(),
                makeChickenPatty()
            )
            returnCalories()
            returnPrice()
        }

    }

    override fun makeIceCream(): Ingredient {
        return factory makeIngredient {
            url =
                "https://th.bing.com/th/id/R.455187b169890c0f4c11482b21e8e357?rik=KnDYPOpKB9LDdQ&riu=http%3a%2f%2fwww.pngpix.com%2fwp-content%2fuploads%2f2016%2f07%2fPNGPIX-COM-Ice-Cream-PNG-Transparent-Image.png&ehk=AUXFwYWSfytNHjVonU%2bVHhRUy5SA39quoAEZqnRiDTg%3d&risl=&pid=ImgRaw&r=0"
            calories = 400.0
            price = 120.0
            name = "IceCream"
        }
    }

    override fun makeVegBurgerMeal(): Ingredient {
        return factory makeIngredient {
            url =
                "https://toppng.com/uploads/thumbnail/in-n-out-burger-meal-out-burger-11563597692rtgh9by46v.png"
            name = "Veg Burger Meal"
            ingredients = mutableListOf(
                makeVegBurger(),
                makeCoke(),
                makeFries()
            )
            returnCalories()
            returnPrice()
        }
    }

    override fun makeNonVegBurgerMeal(): Ingredient {
        return factory makeIngredient {
            url =
                "https://www.erfolgreich-sparen.com/wp-content/uploads/2013/05/mcdonalds_artikel_logo.png"
            price = 999.0
            calories = 300.0
            name = "NonVeg Burger Meal"
            ingredients = mutableListOf(makeVegBurger(), makeFries(), makeCoke())
            returnCalories()
            returnPrice()
        }
    }

    override fun makeBurgerCombo(): Ingredient {
        val food = factory makeIngredient {
            url =
                "https://www.pngarts.com/files/3/McDonalds-Burger-PNG-Photo.png"
            name = "NonVeg/Veg Burger Combo"
            add(makeVegBurger())
            add(makeNonVegBurger())
            returnCalories()
            returnPrice()
        }
        return food
    }
}

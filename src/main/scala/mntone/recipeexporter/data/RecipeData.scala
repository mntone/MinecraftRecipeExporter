package mntone.recipeexporter.data

import com.google.gson.annotations.SerializedName

sealed abstract class RecipeData extends Serializable {
    val recipeType: String
}

sealed trait CraftRecipeData {
    val input: Array[String]
    val output: String
}
sealed trait ShapeCraftRecipeData extends CraftRecipeData {
    val recipe: Array[String]
}

final case class ShapelessRecipeData(input: Array[String], output: String) extends {
    @SerializedName("type")
    val recipeType = "shapeless"
} with RecipeData with CraftRecipeData
final case class ShapeRecipeData(recipe: Array[String], input: Array[String], output: String) extends {
    @SerializedName("type")
    val recipeType = "shape"
} with RecipeData with ShapeCraftRecipeData

final case class UnsupportedRecipeData() extends {
    @SerializedName("type")
    val recipeType = "unsupported"
} with RecipeData
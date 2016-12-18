package mntone.recipeexporter.core

import java.{util => ju}

import mntone.recipeexporter.data.{RecipeData, ShapeRecipeData, ShapelessRecipeData, UnsupportedRecipeData}
import mntone.recipeexporter.util.ScalaUtil
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.{CraftingManager, ShapedRecipes, ShapelessRecipes}
import net.minecraftforge.oredict.{OreDictionary, ShapedOreRecipe, ShapelessOreRecipe}

import scala.collection.convert.wrapAsScala._;

object RecipeExtractor {
    def getCraftingRecipes(crafting: CraftingManager): Array[RecipeData] = {
        crafting.getRecipeList.map {
            case oreShape: ShapedOreRecipe => {
                val input = oreShape.getInput
                val types = input.filter(_ != null).map(getItemStackNameWithOre).distinct.zipWithIndex.map {
                    case (item, index) => (item, ('A' + index).asInstanceOf[Char])
                }.toMap

                val w = ScalaUtil.getPrivateFieldData[Int](oreShape, "width")
                val h = ScalaUtil.getPrivateFieldData[Int](oreShape, "height")
                val recipe = Array.fill(h)("")
                for (i <- 0 until w; j <- 0 until h) {
                    val target = input(w * j + i)
                    if (target == null) {
                        recipe(j) += ' '
                    } else {
                        recipe(j) += types(getItemStackNameWithOre(target))
                    }
                }
                ShapeRecipeData(
                    recipe,
                    types.keys.toArray,
                    getItemStackName(oreShape.getRecipeOutput))
            }

            case oreShapeless: ShapelessOreRecipe =>
                ShapelessRecipeData(
                    oreShapeless.getInput.map(getItemStackNameWithOre).toArray,
                    getItemStackName(oreShapeless.getRecipeOutput))

            case shape: ShapedRecipes => {
                val input = shape.recipeItems
                val types = input.filter(_ != null).map(getItemStackName).distinct.zipWithIndex.map {
                    case (item, index) => (item, ('A' + index).asInstanceOf[Char])
                }.toMap

                val w = shape.recipeWidth
                val h = shape.recipeHeight
                val recipe = Array.fill(h)("")
                for (i <- 0 until w; j <- 0 until h) {
                    val target = input(w * j + i)
                    if (target == null) {
                        recipe(j) += ' '
                    } else {
                        recipe(j) += types(getItemStackName(target))
                    }
                }
                ShapeRecipeData(
                    recipe,
                    types.keys.toArray,
                    getItemStackName(shape.getRecipeOutput))
            }

            case shapeless: ShapelessRecipes =>
                ShapelessRecipeData(
                    shapeless.recipeItems.asInstanceOf[ju.List[ItemStack]].map(getItemStackName).toArray,
                    getItemStackName(shapeless.getRecipeOutput))

            case _ => UnsupportedRecipeData()
        }.toArray
    }

    private def getItemStackNameWithOre(obj: Object): String =
        obj match {
            case oreItem: ju.ArrayList[ItemStack] =>
                val oreId = OreDictionary.getOreIDs(oreItem(0))(0)
                OreDictionary.getOreName(oreId)
            case item: ItemStack => getItemStackName(item)
        }

    private def getItemStackName(itemStack: ItemStack): String =
        itemStack.getItem.delegate.name
}
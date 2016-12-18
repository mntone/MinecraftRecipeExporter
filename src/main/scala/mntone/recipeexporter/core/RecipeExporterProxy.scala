package mntone.recipeexporter.core

import java.time.LocalDateTime

import mntone.recipeexporter.data.ExportDataRoot
import mntone.recipeexporter.util.{GsonUtil, IOUtil}
import net.minecraft.client.Minecraft
import net.minecraft.item.crafting.CraftingManager

class ClientRecipeExporterProxy {
    def init(): Unit = println("[RecipeExporter] function 'init' is called.")

    def loadComplete() {
        println("[RecipeExporter] function 'loadComplete' is called.")
        val minecraft = Minecraft.getMinecraft
        val export = ExportDataRoot(
            TextureExtractor.getTextures(minecraft),
            RecipeExtractor.getCraftingRecipes(CraftingManager.getInstance))
        val json = GsonUtil.gson.toJson(export)
        val timestamp = LocalDateTime.now()
        val fileName = s"export_${timestamp.toString.replace(':', '-')}.json"
        IOUtil.writeFile(fileName, json)
        println(s"[RecipeExporter] wrote recipe & texture data file, $fileName.")
    }
}

object RecipeExporterProxy extends ClientRecipeExporterProxy
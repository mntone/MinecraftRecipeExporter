package mntone.recipeexporter

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLLoadCompleteEvent}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import mntone.recipeexporter.core.RecipeExporterProxy

@SideOnly(Side.CLIENT)
@Mod(modid = "recipeexporter", name = "Minecraft Recipe Exporter", version = "0.8.0", useMetadata = true, modLanguage = "scala")
object RecipeExporter
{
    @Mod.EventHandler
    def init(event: FMLInitializationEvent): Unit =
        RecipeExporterProxy.init()

    @Mod.EventHandler
    def loadComplete(event: FMLLoadCompleteEvent): Unit =
        RecipeExporterProxy.loadComplete()
}

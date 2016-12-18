package mntone.recipeexporter.util

import mntone.recipeexporter.util.ScalaUtil.using
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

import scala.reflect.io.Streamable

object MinecraftUtil {
    def getImageData(resourceLocation: ResourceLocation): Array[Byte] = {
        val resource = Minecraft.getMinecraft.getResourceManager.getResource(resourceLocation)
        using(resource.getInputStream) {
            Streamable.bytes(_)
        }
    }
}
package mntone.recipeexporter.core

import java.{util => ju}

import mntone.recipeexporter.data.TextureData
import mntone.recipeexporter.util.MinecraftUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.{ITextureObject, TextureMap}
import net.minecraft.util.ResourceLocation

import scala.collection.convert.wrapAsScala._

object TextureExtractor {
    def getTextures(minecraft: Minecraft): Map[String, TextureData] = {
        val texManager = minecraft.getTextureManager
        val mapTextureObjects = texManager.mapTextureObjects.asInstanceOf[ju.Map[ResourceLocation, ITextureObject]]
        mapTextureObjects.flatMap {
            case (dloc, tex: TextureMap) =>
                val domain = dloc.getResourceDomain
                val basePath = tex.basePath
                tex.mapRegisteredSprites.asInstanceOf[ju.Map[String, Object]].map {
                    case (name, _) =>
                        convertToReal(domain, name) match {
                            case None => None
                            case Some((domain2, name2)) =>
                                val loc = getResourceLocation(domain2, basePath, name2)
                                val data = MinecraftUtil.getImageData(loc)
                                val texData = TextureData(domain2, basePath, name2, data)
                                name match {
                                    case n if n == "minecraft" => Some((s"minecraft:$name", texData))
                                    case _ => Some(name, texData)
                                }
                        }
                }
            case _ => None
        }.flatten.toMap
    }

    private def convertToReal(domain: String, name: String) =
        if (name != null && name.length != 0) {
            var domain2 = domain
            var name2 = name

            val pos = name2.indexOf(':')
            if (pos >= 0) {
                name2 = name.substring(pos + 1, name.length)
                if (pos != 0) {
                    domain2 = name.substring(0, pos)
                }
            }
            Some((domain2, name2))
        } else
            None

    private def getResourceLocation(domain: String, basePath: String, name: String) =
        new ResourceLocation(domain, s"$basePath/$name.png")
}

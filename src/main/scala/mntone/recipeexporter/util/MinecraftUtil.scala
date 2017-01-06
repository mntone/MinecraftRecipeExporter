package mntone.recipeexporter.util

import java.{util => ju}

import mntone.recipeexporter.util.ScalaUtil.using
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.OreDictionary

import scala.collection.convert.wrapAsScala._
import scala.reflect.io.Streamable

object MinecraftUtil {
    def getImageData(resourceLocation: ResourceLocation): Array[Byte] = {
        val resource = Minecraft.getMinecraft.getResourceManager.getResource(resourceLocation)
        using(resource.getInputStream) {
            Streamable.bytes(_)
        }
    }

    def getItemStackNameWithOre(obj: Object): String =
        obj match {
            case oreItem: ju.ArrayList[ItemStack] =>
                val oreId = OreDictionary.getOreIDs(oreItem(0))(0)
                OreDictionary.getOreName(oreId)
            case item: ItemStack => getItemStackName(item)
        }

    def getItemStackName(itemStack: ItemStack): String =
        getItemName(itemStack.getItem)

    def getItemName(item: Item): String =
        item.delegate.name

    def getBlockName(block: Block): String =
        block.delegate.name
}
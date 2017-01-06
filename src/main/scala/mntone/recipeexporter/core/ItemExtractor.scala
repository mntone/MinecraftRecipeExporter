package mntone.recipeexporter.core

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry
import mntone.recipeexporter.data.{BlockItemData, ItemData, SimpleItemData}
import mntone.recipeexporter.util.MinecraftUtil
import net.minecraft.item.{Item, ItemBlock}

import scala.collection.convert.wrapAsScala._

object ItemExtractor {
    def getItems(itemRegistry: FMLControlledNamespacedRegistry[Item]): Array[ItemData] = {
        itemRegistry.map(_.asInstanceOf[Item]).map {
            case item: ItemBlock =>
                val name = MinecraftUtil.getItemName(item)
                val renderType = item.field_150939_a.getRenderType
                BlockItemData(name, renderType)

            case item =>
                val name = MinecraftUtil.getItemName(item)
                SimpleItemData(name)
        }.toArray
    }
}

package mntone.recipeexporter.core

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry
import mntone.recipeexporter.data._
import mntone.recipeexporter.util.MinecraftUtil
import net.minecraft.block.Block

import scala.collection.convert.wrapAsScala._

object BlockExtractor {
    def getBlocks(blockRegistry: FMLControlledNamespacedRegistry[Block]): Array[BlockData] = {
        blockRegistry.map(_.asInstanceOf[Block]).map { block =>
            val name = MinecraftUtil.getBlockName(block)
            val renderType = block.getRenderType
            BlockData(name, renderType)
        }.toArray
    }
}
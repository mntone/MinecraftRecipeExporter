package mntone.recipeexporter.data

final case class ExportDataRoot(blocks: Array[BlockData],
                                items: Array[ItemData],
                                images: Map[String, TextureData],
                                recipes: Array[RecipeData])
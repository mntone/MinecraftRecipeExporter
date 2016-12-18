package mntone.recipeexporter.data

final case class ExportDataRoot(images: Map[String, TextureData], recipes: Array[RecipeData])
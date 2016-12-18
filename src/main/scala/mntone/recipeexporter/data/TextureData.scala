package mntone.recipeexporter.data

final case class TextureData(domain: String,
                             basePath: String,
                             name: String,
                             data: Array[Byte])
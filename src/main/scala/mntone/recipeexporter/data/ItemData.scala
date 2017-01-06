package mntone.recipeexporter.data

import com.google.gson.annotations.SerializedName

sealed abstract class ItemData extends Serializable {
    val itemType: String
}

final case class SimpleItemData(name: String) extends {
    @SerializedName("type")
    val itemType = "simple"
} with ItemData

final case class BlockItemData(name: String, renderType: Int) extends {
    @SerializedName("type")
    val itemType = "block"
} with ItemData
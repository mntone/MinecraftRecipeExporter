package mntone.recipeexporter.util

import java.lang.reflect.Type
import java.{util => ju}

import com.google.gson._

object GsonUtil {
    private lazy val gson_ = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeHierarchyAdapter(classOf[Array[Byte]], ByteArrayBase64TypeAdapter)
        //.registerTypeHierarchyAdapter(classOf[TraversableOnce[_]], ScalaCollectionTypeAdapter)
        .registerTypeHierarchyAdapter(classOf[Map[String, Any]], new ScalaMapTypeAdapter[Any])
        .create()

    def gson: Gson = gson_

    private object ByteArrayBase64TypeAdapter extends JsonSerializer[Array[Byte]] {
        override def serialize(src: Array[Byte], typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
            new JsonPrimitive("data:image/png;base64," + ju.Base64.getEncoder.encodeToString(src))
    }

    private object ScalaCollectionTypeAdapter extends JsonSerializer[TraversableOnce[_]] {
        override def serialize(src: TraversableOnce[_], typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
            context.serialize(src.toArray, classOf[Array[_]])
    }

    private class ScalaMapTypeAdapter[T] extends JsonSerializer[Map[String, T]] {
        override def serialize(src: Map[String, T], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
            import scala.collection.convert.wrapAsJava._
            context.serialize(mapAsJavaMap(src), classOf[ju.Map[String, T]])
        }
    }

}
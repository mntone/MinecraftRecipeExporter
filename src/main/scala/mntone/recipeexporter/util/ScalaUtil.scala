package mntone.recipeexporter.util

import scala.language.experimental.macros

object ScalaUtil {
    type Closable = {def close(): Unit}

    def using[A <: Closable, B](resource: A)(f: A => B): B =
        try {
            f(resource)
        } finally {
            resource.close
        }

    def getPrivateFieldData[T](instance: Object, fieldName: String): T = {
        val field = instance.getClass.getDeclaredField(fieldName)
        field.setAccessible(true)
        field.get(instance).asInstanceOf[T]
    }
}

package mntone.recipeexporter.util

import java.io.{BufferedOutputStream, FileOutputStream}
import java.nio.charset.Charset

import mntone.recipeexporter.util.ScalaUtil.using

object IOUtil {
    private val utf8 = Charset.forName("UTF-8")

    def writeFile(outputFileName: String, byteArray: Array[Byte]) =
        using(new BufferedOutputStream(new FileOutputStream(outputFileName))) {
            _.write(byteArray)
        }

    def writeFile(outputFIleName: String, text: String) =
        using(new BufferedOutputStream(new FileOutputStream(outputFIleName))) {
            _.write(text.getBytes(utf8))
        }
}

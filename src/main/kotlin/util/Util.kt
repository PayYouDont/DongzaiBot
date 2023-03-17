package com.payudon.util

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.message.data.Image
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL

@Throws(IOException::class, TemplateException::class)
fun Template.processTemplateIntoString(data: Any): String {
    val result = StringWriter(1024)
    process(data, result)
    return result.toString()
}

@Throws(IOException::class, TemplateException::class)
suspend fun htmlToImage(
    contact: Contact?,
    dataFolderPath: String, templateName: String, data: Any, serverHost: String, nginxHost: String
): Image? {
    val dirName = "$dataFolderPath/html/$templateName"
    val configuration = Configuration(Configuration.VERSION_2_3_25)
    configuration.setDirectoryForTemplateLoading(File(dirName))
    val template = configuration.getTemplate("$templateName.ftl")
    val htmlText = template.processTemplateIntoString(data)
    val cacheDir = File("$dataFolderPath/cache/html")
    if (cacheDir.exists()) {
        cacheDir.mkdirs()
    }
    val out = withContext(Dispatchers.IO) {
        val file = File("${cacheDir.path}/$templateName.html")
        val dir = File(file.parent)
        if (!dir.exists()){
            dir.mkdirs()
        }
        FileOutputStream(file)
    }
    withContext(Dispatchers.IO) {
        out.write(htmlText.toByteArray())
        out.close()
    }
    val url = "http://${
        serverHost.replace("http://", "")
    }/convert/html2image?u=doctron&p=lampnick&url=http://${
        nginxHost.replace("http://", "")
    }/cache/html/$templateName.html"
    println("imageUrl=$url")
    try {
        //return downloadImage(url, imagePath)
        val conn = withContext(Dispatchers.IO) {
            URL(url).openConnection()
        } as HttpURLConnection
        conn.requestMethod = "GET"
        if (conn.responseCode == 200) {
            val input = conn.inputStream
            val image =  contact?.uploadImage(input, "png")
            withContext(Dispatchers.IO) {
                input.close()
            }
            return image
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

@Throws(Exception::class)
fun downloadImage(url: String, imagePath: String): File? {
    val conn = URL(url).openConnection() as HttpURLConnection
    conn.requestMethod = "GET"
    println("response code:${conn.responseCode}")
    if (conn.responseCode == 200) {
        val file = File(imagePath)
        val dirFile = File(file.parent)
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        val out = FileOutputStream(file)
        out.write(conn.inputStream.readBytes())
        out.close()
        return file
    } else {
        println("convert fail!")
    }
    return null
}
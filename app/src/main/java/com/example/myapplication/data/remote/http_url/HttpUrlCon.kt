package com.example.myapplication.data.remote.http_url

import android.util.Log
import android.util.Xml
import com.example.myapplication.data.remote.dto.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.util.Date

class HttpUrlCon {
    suspend fun getPage(): Flow<List<Post>> = channelFlow{
//        emit(listOf(Post(1,"Hola", 1,null,null,null,null,"Fecha","Privado")))
        withContext(Dispatchers.IO) {
            val url = URL("https://prueba.agenciareforma.com/AppIphone/Android/xml/posts.xml")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            Log.println(Log.ERROR, "Se conecto", "Se conecto")
            val inputStream = urlConnection.inputStream
            send(parse(inputStream))
            urlConnection.disconnect()
        }
    }

    private fun skip(parser: XmlPullParser) {
        if(parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while(depth != 0){
            when(parser.next()){
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    private fun stringNullOrNullishToNullableString(string: String?): String? {
        return if(string == null || string == "null") null else string
    }

    private fun parse(inputStream: InputStream): List<Post>{
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            parser.nextTag()
            return readPosts(parser)

        }
    }


    private fun readPosts(parser: XmlPullParser): List<Post> {
        val posts = mutableListOf<Post>()

        parser.require(XmlPullParser.START_TAG, null, "Posts")
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.eventType != XmlPullParser.START_TAG){
                continue
            }

            if(parser.name == "Post"){
                posts.add(readPost(parser))
            } else {
                skip(parser)
            }
        }

        return posts
    }


    private fun readPost(parser: XmlPullParser): Post {
        var id = 0
        var userId = 0
        var audioId: String? = null
        var squadId: String? = null
        var linkedDiscussionId: Int? = null
        var linkedCommentId: Int? = null
        var date: String? = null
        var privacy = "No seteado"
        var textContent = "No seteado"
        var username = "Username"

        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            when(parser.name) {
                "id" -> id = readId(parser)
                "text-content" -> textContent = readTextContent(parser)
                "user-id" -> userId = readUserId(parser)
                "audio-id" -> audioId = readAudioId(parser)
                "squad-id" -> squadId = readSquadId(parser)
                "linked-discussion-id" -> linkedDiscussionId = readLinkedDiscussionId(parser)
                "linked-comment-id" -> linkedCommentId = readLinkedCommentId(parser)
                "date" -> date = readDate(parser)
                "privacy" -> privacy = readPrivacy(parser)
                "username" -> username = readUsername(parser)
            }
        }

        return Post(id, textContent, userId, audioId, squadId, linkedDiscussionId, linkedCommentId, date, privacy, username)
    }

    private fun readUsername(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "username")
        val username = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "username")

        return username
    }

    private fun readLinkedCommentId(parser: XmlPullParser): Int? {
        parser.require(XmlPullParser.START_TAG, null, "linked-comment-id")
        val linkedCommentId = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "linked-comment-id")

        return linkedCommentId.toIntOrNull()
    }

    private fun readPrivacy(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "privacy")
        val privacy = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "privacy")

        return when(privacy.toIntOrNull()){
            1 -> "Privado"
            2 -> "Usuarios"
            3 -> "Publico"
            else -> "Desconocido"
        }
    }

    private fun readDate(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, null, "date")
        val date = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "date")

        return date
    }

    private fun readLinkedDiscussionId(parser: XmlPullParser): Int? {
        parser.require(XmlPullParser.START_TAG, null, "linked-discussion-id")
        val linkedDiscussionId = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "linked-discussion-id")

        return linkedDiscussionId.toIntOrNull()
    }

    private fun readSquadId(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, null, "squad-id")
        val squadId = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "squad-id")

        return stringNullOrNullishToNullableString(squadId)
    }

    private fun readAudioId(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, null, "audio-id")
        val audioId = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "audio-id")

        return stringNullOrNullishToNullableString(audioId)
    }

    private fun readUserId(parser: XmlPullParser): Int {
        parser.require(XmlPullParser.START_TAG, null, "user-id")
        val userId = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "user-id")

        return userId.toInt()
    }

    private fun readTextContent(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "text-content")
        val textContent = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "text-content")

        return textContent
    }

    private fun readId(parser: XmlPullParser): Int {
        parser.require(XmlPullParser.START_TAG, null, "id")
        val id = parser.nextText()
        parser.require(XmlPullParser.END_TAG, null, "id")

        return id.toInt()
    }

}
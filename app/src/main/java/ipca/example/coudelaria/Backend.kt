package ipca.example.coudelaria

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ipca.example.coudelaria.models.Cavalo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


object Backend {

    const val BASE_API = "http://192.168.56.50:5024/"

    fun getAllCavalos( callback : (( List<Cavalo>)->Unit) ) {
        var  cavalos = arrayListOf<Cavalo>()
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "cavalo")
                .build()
            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultArray = JSONArray(result)

                for (index in 0 until resultArray.length()){
                    var cavaloJSON  = resultArray[index] as JSONObject
                    var cavalo = Cavalo.fromJSON(cavaloJSON)
                    cavalos.add(cavalo)
                }

                GlobalScope.launch (Dispatchers.Main){
                    callback.invoke(cavalos)
                }
            }
        }
    }

    fun getCavalo( cod_Cavalo: Int, callback : (( Cavalo)->Unit) ) {

        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "cavalo" + "/" + cod_Cavalo)
                .build()
            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)
                var cavalo = Cavalo.fromJSON(resultJSONObject)

                GlobalScope.launch (Dispatchers.Main){
                    callback.invoke(cavalo)
                }
            }
        }
    }
    fun addCavalo( cavalo: Cavalo, callback : (( Boolean)->Unit) ) {

        GlobalScope.launch (Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body: RequestBody = RequestBody.create(
                mediaType, cavalo.toJSON().toString())

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "cavalo" )
                .post(body)
                .build()
            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }

    fun updateCavalo(cod_Cavalo: Int, cavalo: Cavalo, callback : (( Boolean)->Unit) ) {

        GlobalScope.launch (Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body: RequestBody = RequestBody.create(
                mediaType, cavalo.toJSON().toString())

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "cavalo" + "/" + cod_Cavalo )
                .put(body)
                .build()
            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }

    fun deleteCavalo(cod_Cavalo: Int, callback : (( Boolean)->Unit) ) {

        GlobalScope.launch (Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(BASE_API + "cavalo" + "/" + cod_Cavalo )
                .delete()
                .build()
            client.newCall(request).execute().use { response ->
                var result = response.body!!.string()
                var resultJSONObject = JSONObject(result)

                GlobalScope.launch (Dispatchers.Main){
                    val status = resultJSONObject.getString("status")
                    callback.invoke(status == "ok")
                }
            }
        }
    }




    fun getImage(  urlImage: String,  callback : ((Bitmap)->Unit) ){
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(urlImage)
                .build()
            client.newCall(request).execute().use { response ->
                response.body?.let { body->
                    val inputStream: InputStream? = body.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    GlobalScope.launch (Dispatchers.Main) {
                        callback.invoke(bitmap)
                    }
                }
            }
        }
    }
}
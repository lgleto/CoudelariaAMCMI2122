package ipca.example.coudelaria

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ipca.example.coudelaria.models.Cavalo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


object Backend {

    fun getAllCavalos( callback : (( List<Cavalo>)->Unit) ) {
        var  cavalos = arrayListOf<Cavalo>()
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://192.168.56.50:5024/cavalo")
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
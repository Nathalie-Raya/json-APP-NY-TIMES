package com.example.json

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

   private lateinit var adapter: Adapter
   private var articulos2= mutableListOf<Resultado>()

    private fun initRecycler(){
        adapter = Adapter(articulos2){
            //Toast.makeText(this,it.url,Toast.LENGTH_SHORT).show()
            findViewById<WebView>(R.id.wvUrl).loadUrl(it.url)
        }

        findViewById<RecyclerView>(R.id.rv1).layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        findViewById<RecyclerView>(R.id.rv1).adapter = adapter


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        call()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    public fun call(){

        CoroutineScope(Dispatchers.IO).launch {
            val call2: Response<Respuesta> = getRetrofit().create(APIService::class.java).getResponse("emailed/7.json?api-key=tRDVJwRTVrQLGrKIkYworULgj5CXWAwB")


            val respuesta:Respuesta? = call2.body()
            val articulos:List<Resultado> = respuesta?.results ?: emptyList()

            runOnUiThread {
                if(call2.isSuccessful){
                    if (respuesta!=null)
                    {

                   //findViewById<TextView>(R.id.TitleTextView).text= respuesta.results.first().title
                    articulos2.clear()

                        articulos2.addAll(articulos)
                        adapter.notifyDataSetChanged()



                    }
                }else
                {
                    mensaje()


                }

            }


        }
    }
    fun mensaje(){
        Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show()

    }


}

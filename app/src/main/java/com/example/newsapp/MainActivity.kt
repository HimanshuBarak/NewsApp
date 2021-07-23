package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemclicked {

    private lateinit var mAdapter: newsAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //selecting the layout type of our recycler view
           recyclerView.layoutManager = LinearLayoutManager(this)
           fetchdata()
            mAdapter = newsAdapter(this)
          //setting the recyclerview adapter
          recyclerView.adapter = mAdapter

    }

   //to fetch data from the api
    fun fetchdata(){
         val url ="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=e5e0e97f52024c468ed4b06228ae8625"
       val jsonObjectRequest = object: JsonObjectRequest(
           Request.Method.GET,url,null,
           {
               val newsjsonarray = it.getJSONArray("articles")
               val newsarray = ArrayList<News>()

               for( i in 0 until newsjsonarray.length())
               {
                   val jsonobj = newsjsonarray.getJSONObject(i)
                   val news = News(
                       jsonobj.getString("title"),
                       jsonobj.getString("author"),
                       jsonobj.getString("url"),
                       jsonobj.getString("urlToImage"),


                       )
                   newsarray.add(news)
               }
             mAdapter.updateNews(newsarray)
           },
           { error ->

           }
       ) {
           override fun getHeaders(): MutableMap<String, String> {
               val headers = HashMap<String, String>()
               headers["User-Agent"] = "Mozilla/5.0"
               return headers
           }
       }
       //adding the request to the queue for processing
       Mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    //to handle click when a item is clicked
    override fun onItemClicked(item: News) {
        //build a custom chrome tab and open the link with that
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

}
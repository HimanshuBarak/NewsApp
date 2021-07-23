package com.example.newsapp

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


//this is the adapter its purpose is to take the xml and the data and kinda basically combine to form a view
    class newsAdapter( val listener:NewsItemclicked) : RecyclerView.Adapter<newsViewHolder>(){

     private  val items:ArrayList<News> = ArrayList()
       //we find the the xml that is to be converted into a view ,inflate and convert it to a view , and return into to the view holder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
                 val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
                 val viewHolder = newsViewHolder(view)
                 //passing the element that  was clicked on
                 view.setOnClickListener{
                     listener.onItemClicked(items[viewHolder.adapterPosition])
           }
                  return viewHolder
        }
         //binding the data with view
        override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
            val currentitem = items[position]
             //the textview created in the newitem file value will be set to currentitem
            holder.titleview.text = currentitem.title
             Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.image);
             holder.author.text = currentitem.author




         }
       fun updateNews(updatedNews:ArrayList<News>){
           //clearing the list so that the new results can be displayed this time
           items.clear()
           //adding the new fetched results
           items.addAll(updatedNews)
           //telling the adapter that the dataset has changed so it can all its function again for the updated data

           notifyDataSetChanged()

       }

        //this function will return the no. of the item in the list
        override fun getItemCount(): Int {
            return items.size
        }

    }



//this our viewholder this will take in the view locate the textview so that onbind can bind data to this view
class newsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
   //locating the textview of our view
    val titleview: TextView = itemView.findViewById(R.id.title)
    val image : ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)


}
interface NewsItemclicked{
    fun onItemClicked(item:News)
}
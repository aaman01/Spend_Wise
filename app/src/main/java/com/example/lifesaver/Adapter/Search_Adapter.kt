package com.example.lifesaver.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesaver.Database.data.transaction
import com.example.lifesaver.R

class Search_Adapter:RecyclerView.Adapter<Search_Adapter.Viewholder>(){
    private  lateinit var transaction:List<transaction>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_Adapter.Viewholder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.transactions_search,parent,false)
       return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Search_Adapter.Viewholder, position: Int) {
        val transact=transaction[position]
        val context=holder.amount.context

        if (transact.amount>=0){
            holder.amount.text="+ ₹%.2f".format(transact.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.green))
        }else{
            holder.amount.text="- ₹%.2f".format(Math.abs(transact.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        holder.label.text=transact.label
        holder.desc.text=transact.description
        holder.date.text=transact.date
    }

    override fun getItemCount(): Int {
       return transaction.size
    }

    class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        val label : TextView =itemView.findViewById(R.id.label)
        val amount: TextView =itemView.findViewById(R.id.amount)
        val desc:TextView =itemView.findViewById(R.id.description)
        val date:TextView =itemView.findViewById(R.id.date)
    }

    fun submitlist(transaction: List<transaction>){
    this.transaction=transaction
    }
}
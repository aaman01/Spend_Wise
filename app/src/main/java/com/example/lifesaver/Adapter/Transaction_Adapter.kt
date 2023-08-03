package com.example.lifesaver.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lifesaver.R
import com.example.lifesaver.data.transaction

class Transaction_Adapter(private val transactions:ArrayList<transaction>):
    RecyclerView.Adapter<Transaction_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.transactions,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return transactions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          val transact=transactions[position]
         val context=holder.amount.context

        if (transact.amount>=0){
            holder.amount.text="+ ₹%.2f".format(transact.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.green))
        }else{
            holder.amount.text="- ₹%.2f".format(Math.abs(transact.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        holder.label.text=transact.label

    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
          val label :TextView=itemView.findViewById(R.id.label)
          val amount: TextView=itemView.findViewById(R.id.amount)

    }

}
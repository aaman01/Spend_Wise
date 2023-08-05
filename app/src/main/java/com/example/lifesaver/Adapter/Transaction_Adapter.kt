package com.example.lifesaver.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesaver.R
import com.example.lifesaver.Database.data.transaction

import com.example.lifesaver.ViewModel.MainViewModel
import com.example.lifesaver.shared_viewmodel



class Transaction_Adapter(
    private var transactions: List<transaction>,
    mainViewModel: MainViewModel,
    private val shared_viewmodel:shared_viewmodel
):
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
         holder.layout.setOnClickListener {
             shared_viewmodel.id=transact.id
             shared_viewmodel.setBooleanValue(true)
             }


    }



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
          val label :TextView=itemView.findViewById(R.id.label)
          val amount: TextView=itemView.findViewById(R.id.amount)
         val layout:LinearLayout=itemView.findViewById(R.id.ll)


    }

}
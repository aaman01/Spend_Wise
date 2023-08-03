package com.example.lifesaver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesaver.Adapter.Transaction_Adapter
import com.example.lifesaver.data.transaction
import com.example.lifesaver.databinding.FragmentMainFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class Main_fragment : Fragment() {
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var  transactions :ArrayList<transaction>
    private lateinit var adapter: Transaction_Adapter
    private lateinit var dialog:BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     _binding=FragmentMainFragmentBinding.inflate(inflater,container,false)
      return _binding!!.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactions= arrayListOf(
            transaction("WEEKEND BUDGET",400.00),
            transaction("DFDF",-20.00),
            transaction("WEEKEND BUDGET",-60.00),
            transaction("WEEKEND BUDGET",-2.00),
            transaction("WEEKEND BUDGET",-9.00),
            )
        adapter= Transaction_Adapter(transactions)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager=LinearLayoutManager(context)
        updatedashboard()
        binding.addBtn.setOnClickListener {
            entertransaction()
        }
    }

    private fun entertransaction() {
        val dialogview=layoutInflater.inflate(R.layout.bottom_sheet,null)
        dialog=BottomSheetDialog(requireContext(),R.style.BottomDialogTheme)
        dialog.setContentView(dialogview)
        dialog.show()
       dialogview.findViewById<ImageView>(R.id.closeBtn).setOnClickListener {
           dialog.dismiss()
       }
        val l=dialogview.findViewById<TextInputLayout>(R.id.labelLayout)
        val a=dialogview.findViewById<TextInputLayout>(R.id.amountLayout)

        dialogview.findViewById<TextInputEditText>(R.id.labelInput).addTextChangedListener {
            if (it!!.count()>0){
              l.error=null
            }
        }
        dialogview.findViewById<TextInputEditText>(R.id.amountInput).addTextChangedListener {
            if (it!!.count()>0){
                a.error=null
            }
        }
        dialogview.findViewById<Button>(R.id.addTransactionBtn).setOnClickListener {
            val label=dialogview.findViewById<TextInputEditText>(R.id.labelInput).text.toString()
            val amount=dialogview.findViewById<TextInputEditText>(R.id.amountInput).text.toString().toDoubleOrNull()
            if (label.isEmpty()){
                l.error="Please enter a valid label"
            }
            if (amount==null){
                a.error="Please enter a valid amount"
            }

        }

    }

    private fun updatedashboard() {
        val totalamount=transactions.map {it.amount}.sum()
        val budget=transactions.filter { it.amount>0}.map{ it.amount }.sum()
        val expense=budget-totalamount
       binding.balance.text="₹ %.2f".format(totalamount)
       binding.budget.text="₹ %.2f".format(budget)
        binding.expense.text="₹ %.2f".format(expense)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}
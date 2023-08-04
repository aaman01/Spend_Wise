package com.example.lifesaver

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesaver.Adapter.Transaction_Adapter
import com.example.lifesaver.Database.AppDatabase
import com.example.lifesaver.Database.data.transaction
import com.example.lifesaver.Repository.Repository
import com.example.lifesaver.ViewModel.MainViewModel
import com.example.lifesaver.ViewModel.MainViewModelFactory
import com.example.lifesaver.databinding.FragmentMainFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class Main_fragment : Fragment() {
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    private lateinit var  transactions :List<transaction>
    private lateinit var adapter: Transaction_Adapter
    private lateinit var dialog:BottomSheetDialog
   private  lateinit var t:transaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     _binding=FragmentMainFragmentBinding.inflate(inflater,container,false)
      return _binding!!.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initalize()
        AppDatabase.getDatabase(requireActivity()).transactiondao().getall().observe(
            viewLifecycleOwner, Observer {
                transactions=it
                recyclerset(transactions)
            }
        )


        binding.addBtn.setOnClickListener {
            entertransaction()
        }
    }

    private fun initalize() {
        val spenddb=AppDatabase.getDatabase(requireContext())
        val repository=Repository(spenddb)
        mainViewModel = ViewModelProvider(
            this@Main_fragment,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]

    }
    private fun recyclerset(transactions: List<transaction>) {
        adapter= Transaction_Adapter(transactions)
        binding.recyclerview.adapter=adapter
        adapter.notifyDataSetChanged()
        binding.recyclerview.layoutManager=LinearLayoutManager(context)
        updatedashboard()
    }


    private fun entertransaction() {
        val dialogview=layoutInflater.inflate(R.layout.bottom_sheet,null)
        dialog=BottomSheetDialog(requireContext(),R.style.BottomDialogTheme)
        dialog.setContentView(dialogview)
        dialog.show()
       dialogview.findViewById<ImageView>(R.id.closeBtn).setOnClickListener {
           val delayMillis = 200
           Handler().postDelayed({
               dialog.dismiss()
           }, delayMillis.toLong())

       }
        val l=dialogview.findViewById<TextInputLayout>(R.id.labelLayout)
        val a=dialogview.findViewById<TextInputLayout>(R.id.amountLayout)
        val d=dialogview.findViewById<TextInputLayout>(R.id.descriptionLayout)

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
        dialogview.findViewById<TextInputEditText>(R.id.descriptionInput).addTextChangedListener {
            if (it!!.count()>0){
                d.error=null
            }
        }
        dialogview.findViewById<Button>(R.id.addTransactionBtn).setOnClickListener {
            val label=dialogview.findViewById<TextInputEditText>(R.id.labelInput).text.toString()
            val amount=dialogview.findViewById<TextInputEditText>(R.id.amountInput).text.toString().toDoubleOrNull()
            val description=dialogview.findViewById<TextInputEditText>(R.id.descriptionInput).text.toString()
            if (label.isEmpty()){
                l.error="Please enter a valid label"
            }
            if (amount==null){
                a.error="Please enter a valid amount"
            }
            if (description.isEmpty()){
                d.error="Please enter a valid description"
            }
            if (label.isNotEmpty() && amount!=null && description.isNotEmpty()){
               t=transaction(0,label,amount,description)
             mainViewModel.addtransaction(t)
                dialog.dismiss()
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
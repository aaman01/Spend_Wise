package com.example.lifesaver

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Main_fragment : Fragment() {
    private var _binding: FragmentMainFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    private lateinit var  transactions :List<transaction>
    private lateinit var adapter: Transaction_Adapter
    private lateinit var dialog:BottomSheetDialog
   private  lateinit var t:transaction
    private val sharedViewModel: shared_viewmodel by activityViewModels()

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
        sharedViewModel.booleanLiveData.observe(viewLifecycleOwner, Observer {
            if (it){
                update_delete_transaction()
            }
        })
        binding.searchIcon.setOnClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_SHORT).show()
        }
     binding.more.setOnClickListener {
        val window=PopupWindow(requireContext())
         val view=layoutInflater.inflate(R.layout.pop_up_layout,null)
         window.contentView=view
        val clearallbtn= view.findViewById<TextView>(R.id.clearall)
         val charts= view.findViewById<TextView>(R.id.charts)
         val settings= view.findViewById<TextView>(R.id.setting)
         clearallbtn.setOnClickListener {
             Deleteall()
             window.dismiss()
         }
         charts.setOnClickListener {
             Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_SHORT).show()
             window.dismiss()
         }
         settings.setOnClickListener {
             Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_SHORT).show()
             window.dismiss()
         }
         window.showAsDropDown(binding.more)

     }

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
        adapter= Transaction_Adapter(transactions,mainViewModel,sharedViewModel)
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

    private fun Deleteall() {
       val dialog=AlertDialog.Builder(requireContext())
           .setTitle("Are you sure ?")
           .setPositiveButton("Yes"){dialog,_->
               lifecycleScope.launch(Dispatchers.Main) {
                   AppDatabase.getDatabase(requireContext()).transactiondao().deleteAll()
               }
               dialog.dismiss()
               }
           .setNegativeButton("Cancel"){dialog,_->
               dialog.dismiss()
           }.create()
        dialog.show()
    }

    private fun update_delete_transaction() {
        val dialogview=layoutInflater.inflate(R.layout.bottom_sheet_edit,null)
        dialog=BottomSheetDialog(requireContext(),R.style.BottomDialogTheme)
        dialog.setContentView(dialogview)

        dialog.show()
        val t = transactions.filter{ it.id==sharedViewModel.id}
        Log.d("hello","1")
//        dialogview.findViewById<ImageView>(R.id.closeBtn).setOnClickListener {
//            val delayMillis = 200
//            Handler().postDelayed({
//                dialog.dismiss()
//            }, delayMillis.toLong())
//
//        }
//        val l=dialogview.findViewById<TextInputEditText>(R.id.labelInput)
//        val a=dialogview.findViewById<TextInputEditText>(R.id.amountInput)
//        val d=dialogview.findViewById<TextInputEditText>(R.id.descriptionInput)
//
//        val editableText: Editable = SpannableStringBuilder(t[0].label)
//        l.text=editableText
//        val editableText1: Editable = SpannableStringBuilder(t[0].amount.toString())
//        a.text=editableText1
//        val editableText2: Editable = SpannableStringBuilder(t[0].description)
//        d.text=editableText2




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}
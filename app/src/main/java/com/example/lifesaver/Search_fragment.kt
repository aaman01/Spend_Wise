package com.example.lifesaver

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesaver.Adapter.Search_Adapter
import com.example.lifesaver.Database.AppDatabase
import com.example.lifesaver.Database.data.transaction
import com.example.lifesaver.Repository.Repository
import com.example.lifesaver.ViewModel.MainViewModel
import com.example.lifesaver.ViewModel.MainViewModelFactory
import com.example.lifesaver.databinding.FragmentMainFragmentBinding
import com.example.lifesaver.databinding.FragmentSearchFragmentBinding

class Search_fragment : Fragment() {
    private  var _binding:FragmentSearchFragmentBinding?=null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    private lateinit var adapter:Search_Adapter
    val searchText = MutableLiveData<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSearchFragmentBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initalize()
     binding.back.setOnClickListener {
         backpress()
     }

    binding.searchText.addTextChangedListener(object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
           val text=s.toString()
         searchText.value=text

        }

        override fun afterTextChanged(s: Editable?) {}
    })

        searchText.observeForever {str->
            AppDatabase.getDatabase(requireContext()).transactiondao().filterdata(str).observe(viewLifecycleOwner,
                Observer {
                    if (str.isNullOrEmpty()){
                       binding.searchview.visibility=View.GONE
                        binding.text.visibility=View.VISIBLE
                    }
                    else {
                        binding.text.visibility=View.GONE
                        binding.searchview.visibility=View.VISIBLE
                     recyclelist(it)}
                })
        }

    }

    private fun recyclelist(it: List<transaction>?) {
        binding.searchview.layoutManager=LinearLayoutManager(requireContext())
        adapter=Search_Adapter()
        adapter.submitlist(it!!)
        adapter.notifyDataSetChanged()
        binding.searchview.adapter=adapter

    }

    private fun backpress() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }


    private fun initalize() {
            val spenddb= AppDatabase.getDatabase(requireContext())
            val repository= Repository(spenddb)
            mainViewModel = ViewModelProvider(
                this@Search_fragment,
                MainViewModelFactory(repository)
            )[MainViewModel::class.java]

        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}
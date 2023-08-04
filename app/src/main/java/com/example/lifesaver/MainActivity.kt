package com.example.lifesaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesaver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, Main_fragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
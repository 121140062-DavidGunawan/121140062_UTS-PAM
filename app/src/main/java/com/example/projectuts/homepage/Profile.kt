package com.example.projectuts.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.projectuts.databinding.ActivityProfileBinding
import com.example.projectuts.datastore.DataStoreManager
import com.example.projectuts.login.SignIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        dataStoreManager.readLoginFlow.asLiveData().observe(this) {
            if (!it) {
                intent = Intent(this, SignIn::class.java)
                startActivity(intent)
            }
        }
        buttonClick()
        insetData()
    }

    private fun insetData() {
        lifecycleScope.launch {
            dataStoreManager.getUsername().collect {
                val firstLetter: String = it.substring(0, 1).uppercase()
                binding.title.text = firstLetter
                binding.username.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getGitHub().collect {
                binding.github.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getNim().collect {
                binding.nim.text = it
            }
        }
        lifecycleScope.launch {
            dataStoreManager.getEmail().collect {
                binding.email.text = it
            }
        }

    }

    private fun buttonClick() {
        binding.bthome.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btprofile.setOnClickListener{

        }
        binding.btlogout.setOnClickListener{
            lifecycleScope.launch {
                dataStoreManager.updateLogin(false)
            }
        }
    }
}
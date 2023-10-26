package com.example.projectuts.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectuts.databinding.ActivitySignInBinding
import com.example.projectuts.datastore.DataStoreManager
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.projectuts.homepage.MainActivity
import kotlinx.coroutines.launch

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        dataStoreManager.readLoginFlow.asLiveData().observe(this) {
            if (it) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        clickListener()
    }

    private fun clickListener() {
        binding.signInBt.setOnClickListener{
            val user = binding.inputUsername.text.toString()
            val pw = binding.inputPassword.text.toString()

            if (user != "" && pw != "") {
                observeInput(user, pw)
            } else {
                Toast.makeText(this, "Fill all the Fields!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpOp.setOnClickListener {
            intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun observeInput(user: String, password: String) {
        var userbenar = false
        var passwordbenar = false
        dataStoreManager.readUserFlow.asLiveData().observe(this) {
            if (it == user) {
                userbenar = true
            }
        }
        dataStoreManager.readPwFlow.asLiveData().observe(this) {
            if (it == password) {
                passwordbenar = true
            }
        }
        if (userbenar && passwordbenar) {
            lifecycleScope.launch {
                dataStoreManager.updateLogin(true)
            }
        } else {
            Toast.makeText(this, "Incorrect username or password!", Toast.LENGTH_SHORT).show()
        }
    }
}
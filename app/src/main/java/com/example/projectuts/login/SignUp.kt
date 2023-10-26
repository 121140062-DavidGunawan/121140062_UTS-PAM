package com.example.projectuts.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.projectuts.R
import com.example.projectuts.databinding.ActivitySignUpBinding
import com.example.projectuts.datastore.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        binding.signUpBt.setOnClickListener{
            val user = binding.inputUsername.text.toString()
            val pw = binding.inputPassword.text.toString()
            val git = binding.inputGithub.text.toString()
            val nim =  binding.inputNim.text.toString()
            val email = binding.inputEmail.text.toString()
            if (user != "" && pw != "" && git != "" && nim != "" && email != "") {
                lifecycleScope.launch {
                    dataStoreManager.saveToDataStore(user,pw,git,nim,email)
                }
                intent = Intent(this, SignIn::class.java)
                Toast.makeText(this,"Successfully Sign Up!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else {
                Toast.makeText(this, "Fill all the Fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
package com.example.projectuts

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.projectuts.databinding.ActivitySplashScreenBinding
import com.example.projectuts.datastore.DataStoreManager
import com.example.projectuts.homepage.MainActivity
import com.example.projectuts.login.SignIn


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStoreManager(applicationContext)

        val islogin = dataStoreManager.readLoginFlow.asLiveData().observe(this){
            if(it) {
                intent = Intent(this, MainActivity::class.java)
            } else {
                intent = Intent(this, SignIn::class.java)
            }
        }

        binding.ivSplash.alpha = 0f
        binding.textView.alpha = 0f
        binding.textView.animate().setDuration(3000).alpha(1f)
        binding.ivSplash.animate().setDuration(3000).alpha(1f).withEndAction{
            startActivity(intent)
        }
    }
}
package com.example.projectuts.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuts.databinding.ActivityMainBinding
import com.example.projectuts.model.DataItem
import com.example.projectuts.model.ResponseUser
import com.example.projectuts.network.configAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        createRecyclerView()
        createSearchView()

        buttonClick()
    }

    private fun buttonClick() {
        binding.bthome.setOnClickListener {

        }
        binding.btprofile.setOnClickListener {
            intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }

    private fun createRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerView
        itemAdapter = RecyclerViewAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        getData()
    }

    private fun createSearchView () {
        val searchView: SearchView = binding.searchBar
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                itemAdapter.filter.filter(p0)
                return false
            }
        })
    }


    private fun getData() {
        val client = configAPI.getApiService().getListUsers("1")

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray) {
                        itemAdapter.addUser(data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}
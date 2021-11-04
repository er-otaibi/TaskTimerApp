package com.example.tasktimerapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.adapter.SummaryRVAdapter
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SummaryTaskActivity : AppCompatActivity() {

    lateinit var llNoSummaryTask : LinearLayout
    lateinit var rvSummary: RecyclerView
    lateinit var summaryRVAdapter: SummaryRVAdapter

    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_task)

        llNoSummaryTask = findViewById(R.id.llNoSummaryTask)
        rvSummary = findViewById(R.id.rvSummary)

        var fab = findViewById<FloatingActionButton>(R.id.fabSummary)
        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        var btSummaryNavigationView = findViewById<BottomNavigationView>(R.id.btSummaryNavigationView)

        btSummaryNavigationView.background = null
        btSummaryNavigationView.menu.getItem(2).isEnabled = false

        btSummaryNavigationView.setOnNavigationItemSelectedListener {
                item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.view -> {
                    startActivity(Intent(this, ViewTaskActivity::class.java))

                }
                R.id.add -> {
                    startActivity(Intent(this, AddTaskActivity::class.java))

                }
                R.id.summary -> {
                    startActivity(Intent(this, SummaryTaskActivity::class.java))
                }

            }
            true
        }


        myViewModel.getTasks().observe(this, {
                tasks -> summaryRVAdapter.update(tasks)
        })

        summaryRVAdapter = SummaryRVAdapter(this)
        rvSummary.adapter = summaryRVAdapter
        rvSummary.layoutManager = LinearLayoutManager(this)

    }

}
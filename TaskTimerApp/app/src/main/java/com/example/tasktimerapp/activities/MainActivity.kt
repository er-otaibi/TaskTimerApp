package com.example.tasktimerapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tasktimerapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addTask = findViewById<Button>(R.id.addTask)
        val viewTask = findViewById<Button>(R.id.viewTask)
        var btNavigationView = findViewById<BottomNavigationView>(R.id.btNavigationView)

        btNavigationView.background = null
        btNavigationView.menu.getItem(2).isEnabled = false
        var fab = findViewById<FloatingActionButton>(R.id.fabMain)

        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        btNavigationView.setOnNavigationItemSelectedListener {
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

        addTask.setOnClickListener {
            val intent = Intent(this , AddTaskActivity::class.java)
            startActivity(intent)
        }
        viewTask.setOnClickListener {
            val intent = Intent(this , ViewTaskActivity::class.java)
            startActivity(intent)
        }
    }

}
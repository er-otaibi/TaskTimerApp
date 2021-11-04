package com.example.tasktimerapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.R
import com.example.tasktimerapp.database.Tasks
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddTaskActivity : AppCompatActivity() {
     lateinit var etName: EditText
     lateinit var etDescription: EditText
     lateinit var btnAdd: Button
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        etName = findViewById(R.id.etName)
        etDescription = findViewById(R.id.etDescription)
        btnAdd = findViewById(R.id.btnAdd)

        var fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        var btAddNavigationView = findViewById<BottomNavigationView>(R.id.btAddNavigationView)

        btAddNavigationView.background = null
        btAddNavigationView.menu.getItem(2).isEnabled = false

        btAddNavigationView.setOnNavigationItemSelectedListener {
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

        btnAdd.setOnClickListener {
            if (etName.text.isNotEmpty() && etDescription.text.isNotEmpty()){
                var name = etName.text.toString()
                var description = etDescription.text.toString()

                // add to database
                myViewModel.addTask(Tasks(0,name, description,""))
                startActivity(Intent(this, ViewTaskActivity::class.java))

            }else{
                Toast.makeText(this , "Enter a full task" , Toast.LENGTH_SHORT).show()
            }
        }
    }

}
package com.example.tasktimerapp.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.TimerService
import com.example.tasktimerapp.adapter.StopwatchRVAdapter
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt


class ViewTaskActivity : AppCompatActivity() {

    lateinit var stopwatchRvAdapter: StopwatchRVAdapter
    lateinit var tvStopWatch: TextView
    lateinit var tvTaskName: TextView
    lateinit var ivIcanchor: ImageView
    lateinit var llNoViewTask : LinearLayout
    lateinit var llViewTask : LinearLayout
    var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    var myTaskId = 0

    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        val rvMain = findViewById<RecyclerView>(R.id.rvMain)
        tvStopWatch = findViewById(R.id.tvStopWatch)
        ivIcanchor = findViewById(R.id.icanchor)
        llNoViewTask = findViewById(R.id.llNoViewTask)
        llViewTask = findViewById(R.id.llViewTask)
        tvTaskName = findViewById(R.id.tvTaskName)
        var btViewNavigationView = findViewById<BottomNavigationView>(R.id.btViewNavigationView)

        var fab = findViewById<FloatingActionButton>(R.id.fabView)
        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        btViewNavigationView.background = null
        btViewNavigationView.menu.getItem(2).isEnabled = false

        btViewNavigationView.setOnNavigationItemSelectedListener {
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

        // import font
        // Typeface MLight = Typeface.createFromAsset (getAssets(), path: "fonts/MLight.ttf");
        // Typeface MMedium = Typeface.createFromAsset(getAssets(), path: "fonts/MMedium.ttf");
        // Typeface MRegular = Typeface.createFromAsset(getAssets (), path: "fonts/MRegular.ttf");
        // customize font
        // tvSplash.setTypeface(MRegular);
        // tvSubSplash.setTypeface(MLight);
        // btnget.setTypeface(MMedium);



        myViewModel.getTasks().observe(this, {
                tasks -> stopwatchRvAdapter.update(tasks)
        })


        stopwatchRvAdapter = StopwatchRVAdapter(this)
        rvMain.adapter = stopwatchRvAdapter
        rvMain.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false)


        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))


    }

    fun resetTimer()
    {
        stopTimer()
        time = 0.0
        tvStopWatch.text = getTimeStringFromDouble(time)
    }

     fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

     fun startTimer()
    {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true
    }

     fun stopTimer()
    {
        stopService(serviceIntent)
        timerStarted = false
    }

     val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            tvStopWatch.text = getTimeStringFromDouble(time)
        }
    }

     fun getTimeStringFromDouble(time: Double): String
    {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

     fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)


}
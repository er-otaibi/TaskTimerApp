package com.example.tasktimerapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tasktimerapp.database.TaskDatabase
import com.example.tasktimerapp.database.Tasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyViewModel(application: Application): AndroidViewModel(application) {

    private val noteDao = TaskDatabase.getInstance(application).taskDao()
    private val notes: LiveData<List<Tasks>> = noteDao.getAllMyTasks()


    fun getTasks(): LiveData<List<Tasks>> {
        return notes
    }

    fun addTask(tasks: Tasks){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insertTask(tasks)
        }
    }

    fun updateTask(timer: String, id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(timer , id)
        }
    }

    fun deleteNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
          //  noteDao.deleteOBJ(Notes(noteID,noteText))
        }
    }
}
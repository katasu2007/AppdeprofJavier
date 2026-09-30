package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.StudyRepository
import com.example.ui.RachaScreen
import com.example.ui.RachaViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StudyRepository(database.studyDao())
        val viewModelFactory = RachaViewModel.Factory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[RachaViewModel::class.java]

        setContent {
            MyApplicationTheme {
                RachaScreen(viewModel = viewModel)
            }
        }
    }
}

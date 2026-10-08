package be.vives.jarne.assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import be.vives.jarne.assignment_2.models.MockupToDo
import be.vives.jarne.assignment_2.ui.ToDetailScreen
import be.vives.jarne.assignment_2.ui.theme.Assignment_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val toDo = MockupToDo.getToDos().first()
                    ToDetailScreen(
                        toDo = toDo,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

package be.vives.jarne.assignment_2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.vives.jarne.assignment_2.R
import be.vives.jarne.assignment_2.models.MockupToDo
import be.vives.jarne.assignment_2.models.ToDo
import be.vives.jarne.assignment_2.ui.theme.Assignment_1Theme
import be.vives.jarne.assignment_2.utility.Utilities
import java.util.Locale

@Composable
fun ToDoDetailScreen(toDo: ToDo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Top section with image, number, and status
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.todo_image),
                contentDescription = "ToDo Image",
                modifier = Modifier.size(120.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "#${toDo.number}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp
                )
                Text(
                    text = toDo.statusDescription,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 36.sp
                )
            }
        }

        // Red divider
        HorizontalDivider(color = Color.Red, thickness = 1.dp, modifier = Modifier.padding(bottom = 16.dp))

        // Title and Description
        Text(
            text = toDo.title,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = toDo.description,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val assignedUserText = if (toDo.assignedToUser != null) {
            "Assigned to ${toDo.assignedToUser?.firstName} ${toDo.assignedToUser?.lastName}"
        } else {
            "Not assigned yet"
        }
        Text(
            text = assignedUserText,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Time estimated and remaining
        Text(
            text = "Time estimated ${toDo.timeEstimated} hours",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val remainingFormatted = java.lang.String.format(Locale.US, "%.2f", toDo.timeRemaining.toFloat())
        Text(
            text = "Time remaining $remainingFormatted hours",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Boxed area for boolean flags using reusable utility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Utilities.ToDoProgressSection(
                analysisDone = toDo.analysisDone,
                developmentDone = toDo.developmentDone,
                reviewAndTestingDone = toDo.reviewAndTestingDone,
                acceptanceDone = toDo.acceptanceDone
            )
        }
    }
}

class ToDoPreviewParameterProvider : PreviewParameterProvider<ToDo> {
    private val toDos = MockupToDo.getToDos()
    override val values = toDos.asSequence()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ToDoDetailScreenPreview(
    @PreviewParameter(ToDoPreviewParameterProvider::class, limit = 3) toDo: ToDo,
) {
    Assignment_1Theme {
        ToDoDetailScreen(toDo)
    }
}

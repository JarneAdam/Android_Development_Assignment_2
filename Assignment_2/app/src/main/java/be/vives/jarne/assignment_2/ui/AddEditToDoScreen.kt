package be.vives.jarne.assignment_2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.vives.jarne.assignment_2.models.MockupToDo
import be.vives.jarne.assignment_2.models.ToDo
import be.vives.jarne.assignment_2.models.User

/**
 * Stateful screen component managing ToDo editing state.
 */
@Composable
fun AddEditToDoScreen(
    modifier: Modifier = Modifier,
    toDo: ToDo? = null,
    users: List<User> = MockupToDo.getUsers(),
    onSave: (ToDo) -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var title by remember { mutableStateOf(toDo?.title ?: "") }
    var description by remember { mutableStateOf(toDo?.description ?: "") }
    var selectedUser by remember { mutableStateOf(toDo?.assignedToUser) }
    var analysisDone by remember { mutableStateOf(toDo?.analysisDone ?: false) }
    var developmentDone by remember { mutableStateOf(toDo?.developmentDone ?: false) }
    var reviewAndTestingDone by remember { mutableStateOf(toDo?.reviewAndTestingDone ?: false) }
    var acceptanceDone by remember { mutableStateOf(toDo?.acceptanceDone ?: false) }

    AddEditToDoContent(
        title = title,
        onTitleChange = { title = it },
        description = description,
        onDescriptionChange = { description = it },
        selectedUser = selectedUser,
        onUserSelected = { selectedUser = it },
        users = users,
        analysisDone = analysisDone,
        onAnalysisDoneChange = { analysisDone = it },
        developmentDone = developmentDone,
        onDevelopmentDoneChange = { developmentDone = it },
        reviewAndTestingDone = reviewAndTestingDone,
        onReviewAndTestingDoneChange = { reviewAndTestingDone = it },
        acceptanceDone = acceptanceDone,
        onAcceptanceDoneChange = { acceptanceDone = it },
        onSave = {
            val updatedToDo = (toDo ?: MockupToDo.getToDos().first()).copy(
                title = title,
                description = description,
                assignedToUser = selectedUser,
                analysisDone = analysisDone,
                developmentDone = developmentDone,
                reviewAndTestingDone = reviewAndTestingDone,
                acceptanceDone = acceptanceDone,
            )
            onSave(updatedToDo)
        },
        onCancel = onCancel,
        modifier = modifier,
    )
}

/**
 * Stateless content component displaying the form UI.
 */
@Composable
fun AddEditToDoContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    selectedUser: User?,
    onUserSelected: (User?) -> Unit,
    users: List<User>,
    analysisDone: Boolean,
    onAnalysisDoneChange: (Boolean) -> Unit,
    developmentDone: Boolean,
    onDevelopmentDoneChange: (Boolean) -> Unit,
    reviewAndTestingDone: Boolean,
    onReviewAndTestingDoneChange: (Boolean) -> Unit,
    acceptanceDone: Boolean,
    onAcceptanceDoneChange: (Boolean) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Title text
        Text(
            text = "Add/Edit ToDo",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )

        // Title OutlinedTextField
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
        )

        // Description OutlinedTextField
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth(),
        )

        // Assign To Dropdown
        UserDropdown(
            selectedUser = selectedUser,
            users = users,
            onUserSelected = onUserSelected,
        )

        // Progress header
        Text(
            text = "Progress:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
        )

        // Switches
        SwitchRow(
            label = "Analysis Done",
            checked = analysisDone,
            onCheckedChange = onAnalysisDoneChange,
        )
        SwitchRow(
            label = "Development Done",
            checked = developmentDone,
            onCheckedChange = onDevelopmentDoneChange,
        )
        SwitchRow(
            label = "Review & Testing Done",
            checked = reviewAndTestingDone,
            onCheckedChange = onReviewAndTestingDoneChange,
        )
        SwitchRow(
            label = "Acceptance Done",
            checked = acceptanceDone,
            onCheckedChange = onAcceptanceDoneChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save & Cancel Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = onSave) {
                Text("Save")
            }
        }
    }
}

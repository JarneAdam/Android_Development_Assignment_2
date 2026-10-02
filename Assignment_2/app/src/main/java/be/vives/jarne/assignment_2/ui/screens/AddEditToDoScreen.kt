package be.vives.jarne.assignment_2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.vives.jarne.assignment_2.R
import be.vives.jarne.assignment_2.models.MockupToDo
import be.vives.jarne.assignment_2.models.ToDo
import be.vives.jarne.assignment_2.models.User
import be.vives.jarne.assignment_2.ui.theme.Assignment_1Theme

@Composable
fun AddEditToDoScreen(
    toDo: ToDo? = null,
    modifier: Modifier = Modifier,
    users: List<User> = remember { MockupToDo.getUsers() },
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
            val updatedToDo = toDo?.copy(
                title = title,
                description = description,
                assignedToUser = selectedUser,
                analysisDone = analysisDone,
                developmentDone = developmentDone,
                reviewAndTestingDone = reviewAndTestingDone,
                acceptanceDone = acceptanceDone,
            ) ?: ToDo(
                number = 0,
                title = title,
                description = description,
                createdByUser = users.firstOrNull() ?: User(),
                createOnDate = java.util.Date(),
                assignedToUser = selectedUser,
                finishedOnDate = null,
                timeEstimated = 0,
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

@OptIn(ExperimentalMaterial3Api::class)
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top section: Image & Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.todo_image),
                contentDescription = "ToDo Image",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Add/Edit ToDo",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Title Field
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )

        // Assigned User Dropdown Field
        var dropdownExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedUser?.let { "${it.firstName} ${it.lastName}" } ?: "Select User (Optional)",
                onValueChange = {},
                readOnly = true,
                label = { Text("Assign To") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Select User (Optional)") },
                    onClick = {
                        onUserSelected(null)
                        dropdownExpanded = false
                    }
                )
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text("${user.firstName} ${user.lastName}") },
                        onClick = {
                            onUserSelected(user)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Progress Section
        Text(
            text = "Progress:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        SwitchRow(
            label = "Analysis Done",
            checked = analysisDone,
            onCheckedChange = onAnalysisDoneChange
        )

        SwitchRow(
            label = "Development Done",
            checked = developmentDone,
            onCheckedChange = onDevelopmentDoneChange
        )

        SwitchRow(
            label = "Review & Testing Done",
            checked = reviewAndTestingDone,
            onCheckedChange = onReviewAndTestingDoneChange
        )

        SwitchRow(
            label = "Acceptance Done",
            checked = acceptanceDone,
            onCheckedChange = onAcceptanceDoneChange
        )

        // Action Buttons (Cancel & Save)
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 16.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                shape = RoundedCornerShape(50)
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onSave,
                shape = RoundedCornerShape(50)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddEditToDoScreenPreview() {
    Assignment_1Theme {
        AddEditToDoScreen()
    }
}

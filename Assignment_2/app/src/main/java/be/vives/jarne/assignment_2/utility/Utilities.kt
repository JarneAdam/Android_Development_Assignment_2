package be.vives.jarne.assignment_2.utility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import be.vives.jarne.assignment_2.models.User

object Utilities {

    @Composable
    fun SwitchRow(
        label: String,
        checked: Boolean,
        modifier: Modifier = Modifier,
        onCheckedChange: ((Boolean) -> Unit)? = null,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        }
    }

    @Composable
    fun ToDoProgressSection(
        analysisDone: Boolean,
        developmentDone: Boolean,
        reviewAndTestingDone: Boolean,
        acceptanceDone: Boolean,
        modifier: Modifier = Modifier,
        onAnalysisDoneChange: ((Boolean) -> Unit)? = null,
        onDevelopmentDoneChange: ((Boolean) -> Unit)? = null,
        onReviewAndTestingDoneChange: ((Boolean) -> Unit)? = null,
        onAcceptanceDoneChange: ((Boolean) -> Unit)? = null,
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Progress:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
            )
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
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserDropdown(
        users: List<User>,
        selectedUser: User?,
        onUserSelected: (User?) -> Unit,
        modifier: Modifier = Modifier,
        label: String = "Assign To",
        placeholder: String = "Select User (Optional)",
    ) {
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier.fillMaxWidth(),
        ) {
            OutlinedTextField(
                value = selectedUser?.let { "${it.firstName} ${it.lastName}" } ?: placeholder,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(placeholder) },
                    onClick = {
                        onUserSelected(null)
                        expanded = false
                    },
                )
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text("${user.firstName} ${user.lastName}") },
                        onClick = {
                            onUserSelected(user)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

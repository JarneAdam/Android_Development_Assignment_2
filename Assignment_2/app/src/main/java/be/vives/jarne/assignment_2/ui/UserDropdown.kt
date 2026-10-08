package be.vives.jarne.assignment_2.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import be.vives.jarne.assignment_2.models.User
import be.vives.jarne.assignment_2.utility.Utility

/**
 * Reusable dropdown composable for selecting a User from a list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDropdown(
    selectedUser: User?,
    users: List<User>,
    onUserSelected: (User?) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Assign To",
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth(),
    ) {
        val userText = Utility.getUserDisplayName(selectedUser)
        OutlinedTextField(
            value = userText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text("Select User (Optional)") },
                onClick = {
                    onUserSelected(null)
                    expanded = false
                },
            )
            users.forEach { user ->
                DropdownMenuItem(
                    text = { Text(Utility.getUserDisplayName(user)) },
                    onClick = {
                        onUserSelected(user)
                        expanded = false
                    },
                )
            }
        }
    }
}

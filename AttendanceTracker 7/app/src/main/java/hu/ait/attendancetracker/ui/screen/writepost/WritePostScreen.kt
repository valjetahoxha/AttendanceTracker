package hu.ait.attendancetracker.ui.screen.writepost

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

//@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun WritePostScreen(
    writePostViewModel: WritePostViewModel = viewModel(),
    onNavigateWhenSuccess : ()->Unit = {}
) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        OutlinedTextField(value = eventTitle,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Event title") },
            onValueChange = {
                eventTitle = it
            }
        )
        OutlinedTextField(value = eventDate,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Event date") },
            onValueChange = {
                eventDate = it
            }
        )
        OutlinedTextField(value = eventLocation,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Event location") },
            onValueChange = {
                eventLocation = it
            }
        )



        Button(onClick = {
            writePostViewModel.uploadPost(
                eventTitle,
                eventDate,
                eventLocation
            )
        }) {
            Text(text = "Create event")
        }



        when (writePostViewModel.writePostUiState) {
            is WritePostUiState.Init -> {}
            is WritePostUiState.LoadingPostUpload -> CircularProgressIndicator()
            is WritePostUiState.PostUploadSuccess -> onNavigateWhenSuccess()
            is WritePostUiState.ErrorDuringPostUpload -> {
                Text(
                    text = "Error: ${ (writePostViewModel.writePostUiState as
                            WritePostUiState.ErrorDuringPostUpload).error}"
                )
            }
        }

    }
}


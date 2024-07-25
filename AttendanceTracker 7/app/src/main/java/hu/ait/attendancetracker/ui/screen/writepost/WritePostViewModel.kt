package hu.ait.attendancetracker.ui.screen.writepost

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import hu.ait.attendancetracker.data.Post
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.UUID

class WritePostViewModel : ViewModel() {

    var writePostUiState: WritePostUiState by mutableStateOf(WritePostUiState.Init)

    fun uploadPost(
        eventTitle: String, eventDate: String, eventLocation: String
    ) {
        writePostUiState = WritePostUiState.LoadingPostUpload
        val newPost = Post(
            FirebaseAuth.getInstance().uid!!,
            FirebaseAuth.getInstance().currentUser?.email!!,
            eventTitle,
            eventDate,
            eventLocation
        )
        val postsCollection = FirebaseFirestore.getInstance().collection(
            "posts")
        postsCollection.add(newPost)
            .addOnSuccessListener{
                writePostUiState = WritePostUiState.PostUploadSuccess
            }
            .addOnFailureListener{
                writePostUiState = WritePostUiState.ErrorDuringPostUpload(
                    "Post upload failed")
            }
    }
}

sealed interface WritePostUiState {
    object Init : WritePostUiState
    object LoadingPostUpload : WritePostUiState
    object PostUploadSuccess : WritePostUiState
    data class ErrorDuringPostUpload(val error: String?) : WritePostUiState

    
}

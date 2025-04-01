package com.devlink.app.profile

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.authentication.LogoutViewModel
import com.devlink.app.authentication.ModifiedButton
import com.devlink.app.authentication.RetrofitClient
import com.devlink.app.connection_status.ConnectionViewModel
import com.devlink.app.connection_status.UserProfile
import com.devlink.app.ui.TopBar
import com.devlink.app.user_feed.UserData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileView(
    navController: NavController,
    userData: UserData,
    connectionViewModel: ConnectionViewModel,
    token: String,
    viewModel: LogoutViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showPreview by remember { mutableStateOf(false) }
    var isUploadSuccessful by remember { mutableStateOf(false) }
    var isUploadFailed by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
            Log.i("ImagePicker", "Selected URI: $uri")  // Debugging log
            showPreview = uri != null
        }
    )


    val permissionState = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    LaunchedEffect(permissionState) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(isUploadSuccessful, isUploadFailed) {
        if (isUploadSuccessful || isUploadFailed) {
            delay(3000) // Show the message for 3 seconds
            isUploadSuccessful = false
            isUploadFailed = false
        }
    }
//
//    val user by connectionViewModel.user
//    LaunchedEffect(user) {
//        if (user == null) {  // Fetch only if user is null
//            connectionViewModel.fetchProfileFromToken(token)
//        }
//    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colorResource(R.color.jet_black),
            topBar = {
                TopBar(
                    size = 120,
                    title = "Manage Profile",
                    showBackButton = true,
                    navController = navController
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val user by remember { connectionViewModel.user }
                    LaunchedEffect(user) {
                        connectionViewModel.fetchProfileFromToken(token)
                    }


                    if (!showPreview) {
                        Column(
                            modifier = Modifier
                                .height(screenHeight * 0.42f)
                                .width(screenWidth * 0.96f)
                                .clip(RoundedCornerShape(32.dp))
                                .background(color = colorResource(R.color.black_modified)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (user != null) {
                                AsyncImage(
                                    model = if (user?.photoURL != "" || user?.photoURL!!.isNotEmpty()) {
                                        user?.photoURL.toString()
                                    } else {
                                        R.raw.default_dp
                                    },
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .height(100.dp)
                                        .width(100.dp)
                                        .clip(CircleShape)
                                        .border(width = 0.5.dp, color = Color.White, shape = CircleShape)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "${user?.firstname.toString()} ${user?.lastname.toString()}",
                                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                    fontSize = 24.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "User uid: ${user?._id.toString()}",
                                    fontFamily = FontFamily(Font(R.font.josefin_sans)),
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "Work Mail: ${user?.email.toString()}",
                                    fontFamily = FontFamily(Font(R.font.josefin_sans)),
                                    fontSize = 16.sp
                                )
                                ModifiedButton(
                                    text = "Upload Profile Picture",
                                    onButtonClick = {
                                        Log.i("ButtonClick", "Upload Profile Picture button clicked")

                                        imagePickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                        .height(screenHeight * 0.05f)
                                )
                               Row(){
                                   Icon(imageVector = Icons.Default.Info, contentDescription = "info")

                                   Text(
                                       text = "You cannot change the email address",
                                       fontFamily = FontFamily(Font(R.font.josefin_sans)),
                                       fontSize = 16.sp,
                                       modifier = Modifier.padding(start = 4.dp)
                                   )
                               }
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        strokeWidth = 4.dp
                                    )
                                }
                            }
                        }
                    }


                    // Show Preview if Image is Selected
                    if (showPreview && imageUri != null) {
                        Log.i("ImagePicker", "Displaying UploadPostPreview for: $imageUri")
                        UploadProfilePicturePreview(
                            imageUri = imageUri,
                            context = context,
                            onUploadComplete = {
                                showPreview = false // Hide preview after upload is complete
                                imageUri = null // Reset imageUri
                            },
                            isUploadSuccessful = { success ->
                                isUploadSuccessful = success
                                isUploadFailed = !success
                            },
                            connectionViewModel = ConnectionViewModel(),
                            token = token
                        )
                    }

                    if (isUploadSuccessful) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 64.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                "Profile Updated Successfully",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    if (isUploadFailed) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 64.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                "Failed to Upload Image. Try Again.",
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Upload Button
                    if (!showPreview) {
//                        ModifiedButton(
//                            text = "Upload Profile Picture",
//                            onButtonClick = {
//                                Log.i("ButtonClick", "Upload Profile Picture button clicked")
//
//                                imagePickerLauncher.launch(
//                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                                )
//
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(12.dp)
//                                .height(screenHeight * 0.06f)
//                        )
//                        Spacer(modifier = Modifier.height(10.dp))
                        ModifiedButton(
                            text = "Logout",
                            onButtonClick = {
                                Log.i("Logout", "Logout button clicked")
                                viewModel.logoutUser(
                                    context = context,
                                    navController = navController,
                                    token = token
                                )


                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .height(screenHeight * 0.05f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun UploadProfilePicturePreview(
    imageUri: Uri?,
    context: Context,
    onUploadComplete: () -> Unit,
    isUploadSuccessful: (Boolean) -> Unit,
    connectionViewModel: ConnectionViewModel,
    token: String,
    modifier: Modifier = Modifier
) {
    var user by connectionViewModel.user
    LaunchedEffect(Unit) {
        connectionViewModel.fetchProfileFromToken(token)
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .height(500.dp)
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(R.color.black_modified)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp),
//                .clip(RoundedCornerShape(24.dp)),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            imageUri?.let {
//                Text(text = "Selected Image: $it")
                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = it,
                    contentDescription = "Selected Image Preview",
                    modifier = Modifier
//                        .fillMaxWidth()
                        .height(200.dp)
                        .width(200.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.White, CircleShape)
//                        .clip(RoundedCornerShape(16.dp)),
//                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.weight(1f))

                ModifiedButton(
                    text = "Update Profile Picture",
                    onButtonClick = {
                        imageUri?.let { uri ->
                            uploadProfilePictureAWS(
                                userId = user?._id.toString(),
                                context,
                                uri
                            ) { success, imageUrl ->
                                isUploadSuccessful(success)
                                if (success) {
                                    Log.d(
                                        "Upload Success",
                                        "Image uploaded successfully: $imageUrl"
                                    )
                                } else {
                                    Log.e("Upload Error", "Failed to upload image!")
                                }
                            }
                            onUploadComplete()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.05f)
                )
            }
        }
    }

}

data class ImageUploadResponse(
    val message: String,
    val user: UserProfile
)


fun uploadProfilePictureAWS(
    userId: String,
    context: Context,
    imageUri: Uri,
    onUploadComplete: (Boolean, String?) -> Unit
) {
    Log.i("Upload userId", userId)
    CoroutineScope(Dispatchers.IO).launch {
        runCatching {
            // Read image bytes from URI
            context.contentResolver.openInputStream(imageUri)?.use { it.readBytes() }
        }.onSuccess { byteArray ->
            if (byteArray != null) {
                Log.d("Upload Debug", "Image Byte Array Size: ${byteArray.size}")

                val requestFile = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val body =
                    MultipartBody.Part.createFormData("image", "uploaded_image.jpg", requestFile)

                // Make the API call
                val response = RetrofitClient.apiService.uploadImage(userId, body)
                if (response.isSuccessful && response.body() != null) {
                    val imageUrl = response.body()?.user?.photoURL
                    Log.d("Upload Success", "Image URL: $imageUrl")

                    withContext(Dispatchers.Main) {
                        onUploadComplete(true, imageUrl)
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(
                        "Upload Error",
                        "Response Code: ${response.code()}, ErrorBody: $errorBody"
                    )

                    withContext(Dispatchers.Main) {
                        onUploadComplete(false, null)
                    }
                }
            } else {
                Log.e("Upload Error", "Image byte array is null")
                withContext(Dispatchers.Main) {
                    onUploadComplete(false, null)
                }
            }
        }.onFailure { e ->
            Log.e("Upload Error", "Exception: ${e.message}")
            withContext(Dispatchers.Main) {
                onUploadComplete(false, null)
            }
        }
    }
}


// Convert URI to File (Works for content:// and file:// URIs)
fun uriToFile(context: Context, uri: Uri): File {
    val contentResolver: ContentResolver = context.contentResolver
    val file = File(context.cacheDir, "temp_image.jpg")

    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file
}
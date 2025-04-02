package com.devlink.app.create_post

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.authentication.LottieAnimationLoop
import com.devlink.app.authentication.ModifiedButton
import com.devlink.app.ui.TopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreatePostView(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showPreview by remember { mutableStateOf(false) }
    var isUploadSuccessful by remember { mutableStateOf(false) }
    var isUploadFailed by remember { mutableStateOf(false) }


    LaunchedEffect(isUploadSuccessful, isUploadFailed) {
        if (isUploadSuccessful || isUploadFailed) {
            delay(3000) // Show the message for 3 seconds
            isUploadSuccessful = false
            isUploadFailed = false
        }
    }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        showPreview = uri != null
    }


    val permissionState = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),

            containerColor = colorResource(R.color.jet_black),

            topBar = {
                TopBar(
                    size = 120,
                    title = "Create Post",
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
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (!showPreview) {
                        LottieAnimationLoop(
                            fileName = R.raw.upload_post_animation,
                            size = 400.dp
                        )
                    }


                    if (showPreview) {
                        UploadPostPreview(
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
                        )
                    }

                    if (isUploadSuccessful) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 64.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                "Post Created Successfully",
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
                                "Failed to Create Post. Try Again.",
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))
                    ModifiedButton(
                        text = "Select an Image to Upload",
                        onButtonClick = {

                            if (permissionState.status.isGranted) {
                                imagePickerLauncher.launch("image/*")
                            } else {
                                permissionState.launchPermissionRequest()
                            }

                            Log.i("Select image button", "Image button clicked")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(screenHeight * 0.06f)
//                            .align(Alignment.BottomCenter)
                    )
                }


            }

        }
    }
}


@Composable
fun UploadPostPreview(
    imageUri: Uri?,
    context: Context,
    onUploadComplete: () -> Unit,
    isUploadSuccessful: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .height(600.dp)
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
                        .fillMaxWidth()
                        .height(400.dp)
                        .clip(RoundedCornerShape(16.dp)),
//                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.weight(1f))

                ModifiedButton(
                    text = "Upload Post",
                    onButtonClick = {
                        imageUri?.let { uri ->
//                            uploadImageToSupabase(context, uri, "test@b.com") { success ->
//                                isUploadSuccessful(success)
//                            }
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


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestStoragePermission(onGranted: () -> Unit) {
    val permissionState = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    LaunchedEffect(permissionState) {
        if (permissionState.status.isGranted) {
            onGranted()
        }
    }
}


//val supabase: SupabaseClient = createSupabaseClient(
//    supabaseUrl = SupabaseCredentials.Url,
//    supabaseKey = SupabaseCredentials.Key
//) {
//    install(Storage)
//}
//
//suspend fun uploadPost(
//    filename: String,
//    byteArray: ByteArray,
//    isUploadSuccessful: (Boolean) -> Unit
//) {
//    try {
//        val bucket = supabase.storage["user-posts"]
//        bucket.upload(path = filename, byteArray)
//        Log.i("Image Upload", "Image uploaded successfully!")
//        isUploadSuccessful(true)
//    } catch (e: Exception) {
//        Log.e("Image Upload Error", "Error: ${e.message}")
//        isUploadSuccessful(false)
//    }
//}
//
//fun uploadImageToSupabase(
//    context: Context,
//    uri: Uri,
//    userEmail: String,
//    onUploadComplete: (Boolean) -> Unit
//) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val inputStream = context.contentResolver.openInputStream(uri)
//            val byteArray = inputStream?.readBytes()
//            inputStream?.close()
//
//            if (byteArray != null) {
//                val filename = "$userEmail/${System.currentTimeMillis()}.jpg"
//                uploadPost(filename, byteArray, isUploadSuccessful = {
//                    onUploadComplete(it)
//                })
//            }
//        } catch (e: Exception) {
//            Log.e("Upload Error", "Error: ${e.message}")
//            onUploadComplete(false)
//        }
//    }
//}

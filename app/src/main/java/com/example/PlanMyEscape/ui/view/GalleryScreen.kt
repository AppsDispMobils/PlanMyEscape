package com.example.PlanMyEscape.ui.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.PlanMyEscape.R
import com.example.PlanMyEscape.domain.model.Trip
import com.example.PlanMyEscape.ui.utils.copyUriInternal
import com.example.PlanMyEscape.ui.utils.saveBitmapInternal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    trip: Trip,
    navController: NavController, // Para navegar de vuelta o a otros destinos
    onAddImage: (Uri) -> Unit,
    onDeleteImage: (Uri) -> Unit,
    onBack: () -> Boolean
) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var zoomUri by remember { mutableStateOf<Uri?>(null) }
    var pendingDelete by remember { mutableStateOf<Uri?>(null) }

    val appColor = colorResource(id = R.color.app_color)
    val textColor = Color.DarkGray
    val iconColor = Color.Gray
    val surfaceColor = MaterialTheme.colorScheme.surface

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bmp -> bmp?.let { onAddImage(saveBitmapInternal(context, it)) } }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { onAddImage(copyUriInternal(context, it)) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id =
                        R.string.gallery_title, trip.destination),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back), tint = iconColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = appColor.copy(alpha = 0.8f), // Un poco de transparencia
                    titleContentColor = textColor
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { showSheet = true },
                containerColor = appColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.AddPhotoAlternate,
                    contentDescription = stringResource(R.string.add_image))
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (trip.images.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        Text(
                            stringResource(R.string.no_images_yet),
                            color = textColor.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(trip.images) { uri ->
                            Box {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .fillMaxWidth()
                                        .clickable { zoomUri = uri },
                                    contentScale = ContentScale.Crop
                                )
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)

                                        .background(surfaceColor.copy(alpha = 0.6f))
                                        .padding(2.dp)
                                ) {
                                    IconButton(onClick = { zoomUri =
                                        uri }, modifier = Modifier.size(28.dp)) {
                                        Icon(Icons.Default.ZoomIn,
                                            contentDescription = stringResource(R.string.zoom_image), tint =
                                            iconColor)
                                    }
                                    IconButton(onClick = {
                                        pendingDelete = uri }, modifier = Modifier.size(28.dp)) {
                                        Icon(Icons.Default.Delete,
                                            contentDescription = stringResource(R.string.delete_image), tint =
                                            Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )


    pendingDelete?.let { uri ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = {
                Text(stringResource(R.string.delete_confirmation_title), color =
                textColor) },
            text = {
                Text(stringResource(R.string.delete_confirmation_message), color =
                textColor.copy(alpha = 0.8f)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteImage(uri)
                        pendingDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(contentColor
                    = Color.Red)
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text(stringResource(R.string.cancel), color = textColor)
                }
            }
        )
    }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            containerColor = surfaceColor
        ) {
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.take_photo), color = textColor) },
                modifier = Modifier.clickable {
                    takePicture.launch(null)
                    showSheet = false
                },
                leadingContent = { Icon(Icons.Filled.CameraAlt,
                    contentDescription = null, tint = iconColor) }
            )
            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.choose_from_gallery), color = textColor)
                },
                modifier = Modifier.clickable {
                    pickImage.launch("image/*")
                    showSheet = false
                },
                leadingContent = { Icon(Icons.Filled.ImageSearch,
                    contentDescription = null, tint = iconColor) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


    zoomUri?.let { uri ->
        AlertDialog(
            onDismissRequest = { zoomUri = null },
            confirmButton = {},
            containerColor = Color.Black.copy(alpha = 0.8f),
            text = {
                AsyncImage(
                    model =
                    ImageRequest.Builder(context).data(uri).crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        )
    }
}

/*// Función para obtener metadatos (opcional, puedes implementarla si
la necesitas)
private suspend fun getMeta(context: Context, uri: Uri): String =
    withContext(Dispatchers.IO) {
        runCatching {
            uri.path?.let { path ->
                val file = File(path)
                val sizeKb = file.length() / 1024
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm",
Locale.getDefault())
                    .format(Date(file.lastModified()))

                "URI:\n$uri\n\nSize: $sizeKb KB\nDate: $date"
            }
        }.getOrNull() ?: stringResource(R.string.metadata_error)
    }

 */
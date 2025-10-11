package com.example.midtermexam.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.midtermexam.R
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

const val PICK_IMAGE_FILE = 1

class HomeFragment : Fragment() {
    private lateinit var ivPreview: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnUploadImage: Button
    private lateinit var btnOpenCamera: Button
    private var cameraImageUri: Uri? = null

    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPreview = view.findViewById(R.id.iv_preview)
        btnSelectImage = view.findViewById(R.id.selectImagebtn)
        btnUploadImage = view.findViewById(R.id.uploadImageBtn)
        btnOpenCamera = view.findViewById(R.id.openCameraBtn)

        btnOpenCamera.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        btnSelectImage.setOnClickListener {
            checkPermissionAndPickImage()
        }

        btnUploadImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun checkPermissionAndPickImage() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        requestStoragePermissionLauncher.launch(permission)
    }

    private fun uploadImage() {
        selectedImageUri?.let { uri ->
            lifecycleScope.launch {
                try {
                    val part = uriToMultipartBodyPart(uri)

                    if (part == null) {
                        Toast.makeText(requireContext(), "Failed to prepare image for upload", Toast.LENGTH_SHORT)
                            .show()

                        return@launch
                    }

//                    Show a loading indicator
                    Toast.makeText(requireContext(), "Uploading...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {

                }
            }
        } ?: Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
    }

    //    helper function to convert a Uri to MultipartBody.part
    private fun uriToMultipartBodyPart(uri: Uri): MultipartBody.Part? {
        return try {
            val contentResolver = requireContext().contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return null

//            create a temporary file
            val file = File(requireContext().cacheDir, "temp_image_file.jpg")
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

//            get the MIME type of the file
            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

//            create MultipartBody.Part
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //    handle permission request
    private val requestStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                launchGalleryPicker()
            } else {
                Toast.makeText(requireContext(), "Permission denied to read storage", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
            if (isGranted) {
                launchCamera()
            } else {
                Toast.makeText(requireContext(), "Permission denied to read camera", Toast.LENGTH_SHORT).show()
            }
        }

    private val pickImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
            uri?.let {
                ivPreview.load(it)
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {success: Boolean ->
            if (success) {
                cameraImageUri?.let { ivPreview.load(it) }
            }
        }

    //    handle activity results for picking an image
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                ivPreview.load(it)
                btnUploadImage.isEnabled = true
            }
        }

    private fun launchCamera() {
        cameraImageUri = createImageUri()
        takePictureLauncher.launch(cameraImageUri)
    }

    private fun launchGalleryPicker() {
        pickImageFromGalleryLauncher.launch("image/*")
    }

    private fun createImageUri(): Uri? {
        val imageFile = File(requireContext().cacheDir, "camera_photo_${System.currentTimeMillis()}.jpg")

        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            imageFile
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
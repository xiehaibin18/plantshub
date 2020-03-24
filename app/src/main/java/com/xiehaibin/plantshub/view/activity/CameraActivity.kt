package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiehaibin.plantshub.R
// Your IDE likely can auto-import these classes, but there are several
// different implementations so we list them here to disambiguate.
import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.Image
import android.os.Handler
import android.util.*
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.xiehaibin.plantshub.model.PictureRecognition
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.PictureRecognitionData
import com.xiehaibin.plantshub.model.data.PictureRecognitionDataItem
import com.xiehaibin.plantshub.util.Base64Util
import com.xiehaibin.plantshub.util.FileUtil
import com.xiehaibin.plantshub.view.fragment.dialog.PictureRecognitionDialog
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.util.Collections.max
import java.util.Collections.min
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// This is an arbitrary number we are using to keep track of the permission
// request. Where an app has multiple context for requesting permission,
// this can help differentiate the different contexts.
private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest.
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class CameraActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // Add this at the end of onCreate function

        viewFinder = camera_textureView

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    // Add this after onCreate
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    private fun startCamera() {
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(screenSize)
            setTargetRotation(viewFinder.display.rotation)
        }.build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        // 点击快门
        camera_capture.setOnClickListener {
            camera_capture.isEnabled = false
            val file = File(
                externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg"
            )

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        viewFinder.post {
                            camera_capture.isEnabled = true
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        if (file.exists()) {
//                            val shpName: String = "user_info"
//                            val shp: SharedPreferences =
//                                application.getSharedPreferences(shpName, Context.MODE_PRIVATE)
//                            val editor: SharedPreferences.Editor = shp.edit()
//                            editor.putString("Path", file.absolutePath)
//                            editor.apply()
                            CommonData.getInstance().setPath(file.absolutePath)
                        }
                        viewFinder.post {
                            camera_capture.isEnabled = true
                            camera_textureView.isVisible = false
                            camera_capture.isVisible = false
                            camera_imageView.isVisible = true
                            camera_upload_button.isVisible = true
                            camera_cancel_button.isVisible = true
                            camera_unknown_button.isVisible = true
                            if (CommonData.getInstance().getPath().isNotEmpty()) {
                                var myBitmap: Bitmap =
                                    BitmapFactory.decodeFile(CommonData.getInstance().getPath())
                                val m: Matrix = Matrix()
                                m.postRotate(90F)
                                myBitmap = Bitmap.createBitmap(
                                    myBitmap, 0, 0, myBitmap.width, myBitmap.height,
                                    m, true
                                )
                                camera_imageView.setImageBitmap(myBitmap)
                            }
                        }
                    }
                })
        }

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview, imageCapture)

        camera_cancel_button.setOnClickListener {
            camera_textureView.isVisible = true
            camera_capture.isVisible = true
            camera_imageView.isVisible = false
            camera_upload_button.isVisible = false
            camera_cancel_button.isVisible = false
            camera_unknown_button.isVisible = false
        }

        camera_upload_button.setOnClickListener {
            camera_upload_button.isEnabled = false
            camera_cancel_button.isEnabled = false
            camera_unknown_button.isEnabled = false
            try {
                val filePath = CommonData.getInstance().getPath()
                val imgData = FileUtil.readFileByBytes(filePath)
                val imgStr = Base64Util.encode(imgData)
                doAsync {
                    PictureRecognition().post(
                        "client",
                        imgStr,
                        CommonData.getInstance().getPictureRecognitionUrl(),
                        fun(err_code, msg, data) {
                            uiThread {
                                camera_upload_button.isEnabled = true
                                camera_cancel_button.isEnabled = true
                                camera_unknown_button.isEnabled = true
                                if (err_code == 0) {
//                                    startActivity<MainActivity>(
//                                        "err_code" to err_code,
//                                        "msg" to msg
//                                    )
                                    val info = Bundle()
                                    info.putString("data",data)
                                    val pictureRecognitionDialog = PictureRecognitionDialog.newInstance()
                                    pictureRecognitionDialog.setDialogData(info)
                                    pictureRecognitionDialog.show(supportFragmentManager,"pictureRecognitionDialog")
                                } else {
                                    toast("${err_code}:${msg}")
                                }
                            }
                        })
                }
            } catch (e: Exception) {
                toast("error:${e}")
            }

        }

        camera_unknown_button.setOnClickListener {
            camera_upload_button.isEnabled = false
            camera_cancel_button.isEnabled = false
            camera_unknown_button.isEnabled = false
            try {
                val filePath = CommonData.getInstance().getPath()
                val imgData = FileUtil.readFileByBytes(filePath)
                val imgStr = Base64Util.encode(imgData)
                doAsync {
                    PictureRecognition().post(
                        "unknown",
                        imgStr,
                        CommonData.getInstance().getPictureRecognitionUrl(),
                        fun(err_code, msg, data) {
                            uiThread {
                                camera_upload_button.isEnabled = true
                                camera_cancel_button.isEnabled = true
                                camera_unknown_button.isEnabled = true
                                if (err_code != 0) {
                                    toast("${err_code}:${msg}")
                                }
                            }
                        })
                }
            } catch (e: Exception) {
                toast("error:${e}")
            }
        }

    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     * 与用户请求权限
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

}

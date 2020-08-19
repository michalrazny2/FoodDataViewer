package com.example.fooddataviewer.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fooddataviewer.R
import io.fotoapparat.Fotoapparat
import kotlinx.android.synthetic.main.scan_fragment.*

class ScanFragment : Fragment(R.layout.scan_fragment) {

    private lateinit var fotoapparat: Fotoapparat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fotoapparat = Fotoapparat(
            context = requireContext(),
            view = cameraView
        )
    }

    override fun onStart() {
        super.onStart()
        // we need to handle permissions
        handleCameraPermission(false)
    }

    override fun onStop() {
        fotoapparat.stop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handleCameraPermission(true)
    }

    private fun handleCameraPermission(permissionResult: Boolean) {
        if(hasCameraPerimission()){
            fotoapparat.start()
        }else if(!permissionResult || shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    private fun hasCameraPerimission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
 }
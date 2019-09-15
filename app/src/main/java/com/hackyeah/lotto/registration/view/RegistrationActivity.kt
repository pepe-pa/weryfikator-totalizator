package com.hackyeah.lotto.registration.view

import android.content.Intent

import androidx.biometric.BiometricPrompt
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.hackyeah.lotto.R
import com.hackyeah.lotto.common.view.BaseActivity
import com.hackyeah.lotto.registration.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

class RegistrationActivity : BaseActivity() {

    override val layoutRes: Int = R.layout.activity_registration
    override val viewModel: RegistrationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        face1.setOnClickListener {
            takePhoto(RC_TAKE_PHOTO)
        }

        face2.setOnClickListener {
            takePhoto(RC_TAKE_PHOTO2)
        }

        check.setOnClickListener {
            viewModel.compareFaces()
        }

        biometric.setOnClickListener {
            biometricPromt()
        }
    }

    var fileUri: Uri? = null
    val RC_TAKE_PHOTO = 1
    val RC_TAKE_PHOTO2 = 2

    private fun takePhoto(requestCode: Int) {

        val pictureIntent = Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        )
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )

        fileUri  = FileProvider.getUriForFile(this, "com.hackyeah.lotto.provider", file)
        pictureIntent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            fileUri
        )
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(pictureIntent, requestCode);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            fileUri?.let {
                when(requestCode) {
                    RC_TAKE_PHOTO -> viewModel.detectFace(it)
                    RC_TAKE_PHOTO2 ->  viewModel.recognizeTextAndFoundFaceInId(it)
                }
            }
        }
    }

    private fun biometricPromt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_auth_register_title))
            .setSubtitle(getString(R.string.biometric_auth_register_subtitle))
            .setDescription(getString(R.string.biometric_auth_description))
            .setNegativeButtonText(getString(android.R.string.cancel))
            .build()
        val executor = Executors.newSingleThreadExecutor()

        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                Log.e("Piotrek",  "${getString(R.string.auth_error)} $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                Log.i("Piotrek", getString(R.string.auth_success))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e("Piotrek", getString(R.string.auth_failed))
            }
        })

        biometricPrompt.authenticate(promptInfo)
    }
}

package com.tpo_hr.tpohr.views.activities.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.utils.MonthYearPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat
import android.os.Build
import android.content.DialogInterface
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.tpo_hr.tpohr.BuildConfig
import com.tpo_hr.tpohr.models.AccessTokenResponse
import com.tpo_hr.tpohr.utils.Authorization
import com.tpo_hr.tpohr.views.dialogs.BaseDialog
import dagger.android.AndroidInjection
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private var selectedItem = -1
    private var nName: String = ""
    private var mSex: String = ""
    private var mDob: String = ""
    private var mAge: String = ""
    private var mThaiLevel: Int = 1
    private var mEducation: String = ""
    private var mPhone1: String = ""
    private var mPhone2: String = ""

    @Inject
    lateinit var mainPresenter: MainPresenter
    @Inject
    lateinit var authorization: Authorization


    private lateinit var listGenders: Array<String>
    private lateinit var listEducation: Array<String>

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listGenders = resources.getStringArray(R.array.genders_list)
        listEducation = resources.getStringArray(R.array.degree_list)

        setListenerViews()

        mainPresenter.getAccessToken(
            grantType = "client_credentials",
            clientId = 2,
            clientSecret = BuildConfig.BASIC_KEY,
            scope = ""
        )
    }

    private val CAMERA_PHOTO_REQUEST_CODE: Int = 128
    private val WRITE_EXTERNAL_STORAGE_PHOTO_REQUEST_CODE: Int = 128
    private val PERMISSION_REQUEST_CAMERA_CODE: Int = 129

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setListenerViews() {

        llWrapperMain.setOnClickListener {
            hideKeyboard(this)
        }


        ivDate.setOnClickListener {
            val myDatePicker = MonthYearPickerDialog.newInstance(0, 0, 0, 0, MonthYearPickerDialog.DD_MM_YYYY)

            myDatePicker.setListener { _, year, month, dayOfMonth ->
                etDate.setText(
                    "${myDatePicker.covertToKhmerNumber(dayOfMonth.toString())} ${myDatePicker.listMonths[month - 1]} ${myDatePicker.covertToKhmerNumber(
                        year.toString()
                    )}"
                )
            }

            myDatePicker.show(supportFragmentManager, "Select Date")
        }

        ivDateOfBirth.setOnClickListener {
            val myDatePicker = MonthYearPickerDialog.newInstance(0, 0, 0, 0, MonthYearPickerDialog.DD_MM_YYYY)

            myDatePicker.setListener { _, year, month, dayOfMonth ->
                etDateOfBirth.setText(
                    "${myDatePicker.covertToKhmerNumber(dayOfMonth.toString())} ${myDatePicker.listMonths[month - 1]} ${myDatePicker.covertToKhmerNumber(
                        year.toString()
                    )}"
                )

                val userAge = SimpleDateFormat("dd/MM/yyyy").parse("$dayOfMonth/$month/$year")

                val now = Date()
                val timeBetween = now.time - userAge.time;
                val yearsBetween = timeBetween / 3.15576e+10;
                val age = Math.floor(yearsBetween).toInt()

                mAge = age.toString()
                etAge.setText(myDatePicker.covertToKhmerNumber(age.toString()))


                mDob = "$year-$month-$dayOfMonth"
            }

            myDatePicker.show(supportFragmentManager, "Select Date")
        }

        ivSelfie.setOnClickListener {
            openCameraFace()
        }

        btnSent.setOnClickListener {

            hideKeyboard(this)
            nName = etName.text.toString()
            mPhone1 = etPhone1.text.toString()
            mPhone2 = etPhone2.text.toString()

            photoFile?.let { photo ->
                mainPresenter.registerCandidate(
                    "Bearer $accessToken",
                    photo = photo,
                    name = nName,
                    sex = mSex,
                    dob = mDob,
                    age = mAge,
                    thaiLevel = mThaiLevel,
                    education = mEducation,
                    phone1 = mPhone1,
                    phone2 = mPhone2
                )

                Toast.makeText(this, "កំពុងដំណើរការ", Toast.LENGTH_LONG).show()
            }

        }

        spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSex = when (position) {
                    0 -> "Male"
                    1 -> "Female"
                    else -> "Male"
                }
            }
        }

        spEducation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mEducation = listEducation[position]
            }
        }

        rgThaiLevel.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbOne -> mThaiLevel = 1
                R.id.rbTwo -> mThaiLevel = 2
                R.id.rbThree -> mThaiLevel = 3
            }
            hideKeyboard(this)
        }
    }

    private var photoFile: File? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_PHOTO_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    bitmap.let {
                        ivImageFront.setImageBitmap(it)
                        llImageFront.visibility = View.VISIBLE


                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        val tempUri = getImageUri(applicationContext, it)

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        photoFile = File(getRealPathFromURI(tempUri))
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermissionWriteExternal()) {
                    openCameraFace()
                } else {
                    requestPermissionWriteExteral()
                }
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel("You need to allow access permissions",
                            DialogInterface.OnClickListener { _, _ ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissionCamera()
                                }
                            })
                    }
                }
            }

            WRITE_EXTERNAL_STORAGE_PHOTO_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                openCameraFace()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel("You need to allow access permissions",
                            DialogInterface.OnClickListener { _, _ ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissionWriteExteral()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun openCameraFace() {
        if (checkPermissionCamera()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1)
            startActivityForResult(intent, CAMERA_PHOTO_REQUEST_CODE)
        } else {
            requestPermissionCamera()
        }
    }

    private fun requestPermissionCamera() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CAMERA_CODE
        )
    }

    private fun requestPermissionWriteExteral() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_PHOTO_REQUEST_CODE
        )
    }

    private fun checkPermissionCamera(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionWriteExternal(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val SIMPLE_DIALOG: String = "Simple Dialog"

    private fun showDialog(
        title: String,
        message: String,
        positiveLabel: String,
        icon: Int,
        posCode: () -> Unit,
        dismissCode: () -> Unit
    ) {
        val myDialogFragment = BaseDialog(title, message, icon, positiveLabel, posCode, dismissCode)
        myDialogFragment.show(this@MainActivity.supportFragmentManager, SIMPLE_DIALOG)
    }

    private fun setListGender(listGenders: Array<String>, spinner: Spinner, layout: Int) {


        spinner.adapter = object : ArrayAdapter<String>(this, layout, listGenders) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val v: View = super.getDropDownView(position, null, parent)
                v.setPadding(20, 20, 20, 20)
                val textView = v as TextView
                textView.setTextColor(
                    ContextCompat.getColor(context, R.color.colorPrimary)
                )
                if (position == selectedItem) {
                    v.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorTextHint)
                    )
                } else {
                    v.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.colorWhite)
                    )
                }
                return v
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }
    }

    private var accessToken: String = ""

    override fun onGetTokenSuccess(tokenResponse: AccessTokenResponse?) {
        accessToken = tokenResponse?.accessToken.toString()
        authorization.accessToken = accessToken
        authorization.expireDate = tokenResponse?.expiresIn.toString()
        authorization.tokenType = tokenResponse?.tokenType.toString()
    }

    override fun onGetTokenFail() {

    }

    override fun onRegisterFail() {
        BaseDialog("ការចុះឈ្មោះបរាជ័យ",
            resources.getString(R.string.register_failed_message),
            R.drawable.cross_signxxxhdpi,
            "យល់ព្រម", {}, {}).show(this@MainActivity.supportFragmentManager, SIMPLE_DIALOG)


    }

    override fun onRegisterSuccess() {
        BaseDialog(resources.getString(R.string.register_success_title),
            resources.getString(R.string.register_success_message),
            R.drawable.check_signxxxhdpi,
            resources.getString(R.string.close),
            {},
            {}).show(this@MainActivity.supportFragmentManager, SIMPLE_DIALOG)
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (contentResolver != null) {
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }
}

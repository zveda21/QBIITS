package com.example.rafael.my_application.activity

import android.Manifest
import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.media.MediaScannerConnection
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rafael.my_application.helpers.Helper
import com.example.rafael.my_application.R
import com.example.rafael.my_application.db.DatabaseHelper
import com.example.rafael.my_application.fragment.*
import com.example.rafael.my_application.helpers.InputValidation
import com.example.user.my_application.MyService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_radio_group.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.view.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback , NavigationView.OnNavigationItemSelectedListener , ActivityCompat.OnRequestPermissionsResultCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    private lateinit var mMap: GoogleMap
    lateinit var preferences: SharedPreferences
    lateinit var contactFragment: ContactFragment
    lateinit var settingsFragment: SettingsFragment
    lateinit var aboutusFragment: AboutUsFragment
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var registrationFragment: RegistrationFragment
    var mLocationX:Double = 0.0
    var mLocationY:Double = 0.0
    private val GALLERY = 1
    private val CAMERA = 2
    var markerAdded = false
    lateinit var addedMarker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        contactFragment = ContactFragment()
        registrationFragment= RegistrationFragment()
        settingsFragment= SettingsFragment()
        aboutusFragment= AboutUsFragment()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.open,
            R.string.close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        getLocation()
        initObjects()
        showPictureDialog()
        showUserName()


        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        nav_view.setNavigationItemSelectedListener(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        signInbutton.setOnClickListener {
            //openFragment(RadioButtonsFragment(), "rradiogroup_fragment", "Նորություններ")

          val intent=Intent(this,PagerActivity::class.java)
            startActivity(intent)
        }
        fab.setOnClickListener { view ->
           /* Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                   .setAction("Action", null)
                   .show()
                   */
            val intent=Intent(this,SmsActivity::class.java)
            startActivity(intent)
        }
      //  imagebutton_Chosse.setOnClickListener {
     //       Toast.makeText(this,"HElloo",Toast.LENGTH_LONG).show()
           // showPictureDialog()
     //   }
        fab2.setOnClickListener {
            val intent=Intent(this,CommentActivity::class.java)
            startActivity(intent)
        }
     /*   users_show.setOnClickListener {
            val intent=Intent(this,UsersListActivity::class.java)
            startActivity(intent)
        }*/
        preferences = getSharedPreferences("my_data", Context.MODE_PRIVATE)
        pref()
        val  intent= Intent(this, MyService::class.java)
        if (Build.VERSION.SDK_INT <  Build.VERSION_CODES.O) {
            startService(intent)
        } else {
//            startForegroundService(intent)
        }
    }
    private fun showUserName(){
        val sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE)
        val sharedPref1 = getSharedPreferences("sign_in", Context.MODE_PRIVATE)
       val email = sharedPref.getString("email","")!!
        val name=sharedPref1.getString("name"," ")!!
       nav_view.getHeaderView(0).headerUserName.setText( name+"\n"+email)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,
                R.raw.style_json
            ))
        } catch(e: Exception){
            e.printStackTrace()
        }
        if(Helper.isPermissionGranted(
                Manifest.permission.ACCESS_FINE_LOCATION, this,
                4573
            )
        ){
            mMap.isMyLocationEnabled = true
        }
    }

    fun pref() {
        preferences.edit()
            .putString("string_key", "Բարի Գալուստ")
        //    .putInt("int_key", 347239848)
         //   .putBoolean("bool_key", true)
            .apply()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        drawer_layout.closeDrawers()
        when (menuItem.itemId) {
            R.id.contact -> {
                openFragment(contactFragment, "contact_fragment", getString(R.string.contact_us))

            }
            R.id.settings->{
                openFragment(settingsFragment,"settings_fragment",getString(R.string.settings))
            }
            R.id.aboust_us->{
                openFragment(aboutusFragment,"aboutus_fragments",getString(R.string.about_us))
            }
        }

        return true

    }

    fun openFragment(fragment: Fragment,tag: String, text: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentFrameLayout,fragment,tag).commit()
        signInbutton.visibility = View.VISIBLE
        toolbarTitle.text = text
    }

    override fun onBackPressed() {
        getFragment()
        try {
            if (contactFragment.isVisible) {
                this.supportFragmentManager.beginTransaction().remove(contactFragment).commit()
                toolbarTitle.text="QBITS"
            } else  if (registrationFragment.isVisible) {
                this.supportFragmentManager.beginTransaction().remove(registrationFragment).commit()
                toolbarTitle.text="QBITS"
            }else  if (settingsFragment.isVisible) {
                this.supportFragmentManager.beginTransaction().remove(settingsFragment).commit()
                toolbarTitle.text="QBITS"
            }

            else  if (aboutusFragment.isVisible) {
                this.supportFragmentManager.beginTransaction().remove(aboutusFragment).commit()
                toolbarTitle.text="QBITS"
            }
            else {
                val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setMessage(
                        Html.fromHtml(
                            "<font color='#FFFFFF'>Տեղափոխվել գլխավոր էջ</font>",
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    )
                } else {
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>Are You sure You want to exit?</font>"))
                }
                builder.setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                    val sharedPref = getSharedPreferences("login_data", Context.MODE_PRIVATE).edit()
                    sharedPref.putString("email","")
                        .putString("password","").apply()
                    val intent=Intent(this,SiginOrLoginActivity::class.java)
                    startActivity(intent)
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getFragment() {
        try {
            contactFragment = (supportFragmentManager.findFragmentByTag("contact_fragment") as ContactFragment)
        } catch (e: TypeCastException) {
            e.printStackTrace()
        }
        try {
            registrationFragment = (supportFragmentManager.findFragmentByTag("register_fragment") as RegistrationFragment)
        } catch (e: TypeCastException) {
            e.printStackTrace()
        }
        try {
            settingsFragment = (supportFragmentManager.findFragmentByTag("setting_fragment") as SettingsFragment)
        } catch (e: TypeCastException) {
            e.printStackTrace()
        }
        try {
            aboutusFragment = (supportFragmentManager.findFragmentByTag("aboutus_fragment") as AboutUsFragment)
        } catch (e: TypeCastException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            4573 ->{
                mMap.isMyLocationEnabled = true
            }
        }
    }

    private fun getLocation(){
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        if(!checkLocation()) {
            enableLoc()
        }
        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    private fun enableLoc() {
        val locationRequest = LocationRequest.create();
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
        locationRequest.interval = 30 * 1000;
        locationRequest.fastestInterval = 5 * 1000;
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback {
            val status = it.status;
            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        status.startResolutionForResult(this, 199);
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            199 -> {
                val fusedLocationProviderClient :
                        FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient .lastLocation
                    .addOnSuccessListener(this) { location ->
                        if (location != null) {
                            mLocationX =  location.latitude
                            mLocationY = location.longitude
                            volleyRequest(mLocationX, mLocationY)
                            Log.d("locationgsg", mLocationX.toString() + "," + mLocationY.toString())
                            val geocoder = Geocoder(this, Locale.getDefault());
                            val addresses = geocoder.getFromLocation(mLocationX, mLocationY, 5);
                            Log.d("My Address",addresses.toString())
                        }
                    }
            }
        }
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveImage(bitmap)
                    Toast.makeText(this@GoogleMapsActivity, "Image Show!", Toast.LENGTH_SHORT).show()
                    image_user!!.setImageBitmap(bitmap)
                }
                catch (e: IOException)
                {
                    e.printStackTrace()
                    Toast.makeText(this@GoogleMapsActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (requestCode == CAMERA)
        {
            val nkar = data!!.extras!!.get("data") as Bitmap
            image_user!!.setImageBitmap(nkar)
            saveImage(nkar)
            Toast.makeText(this@GoogleMapsActivity, "Photo Show!", Toast.LENGTH_SHORT).show()
        }
    }
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
        val wallpaperDirectory = File (
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }
        try
        {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".png"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.getPath()), arrayOf("image/png"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException){
            e1.printStackTrace()
        }
        return ""
    }
    private fun showPictureDialog() {
        nav_view.getHeaderView(0).chosse_image.setOnClickListener {
            val pictureDialog = android.app.AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItems = arrayOf("Select image from gallery", "Capture photo from camera")
            pictureDialog.setItems(
                pictureDialogItems
            ) { dialog, which ->
                when (which) {
                    0 -> chooseImageFromGallery()
                    1 -> takePhotoFromCamera()
                }
            }
            pictureDialog.show()
        }
    }

    fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA)
    }


    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
    }

    private fun checkLocation(): Boolean {
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onConnected(p0: Bundle?) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val fusedLocationProviderClient :
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient .lastLocation
            .addOnSuccessListener(this) { location ->
                if (location != null) {
                    mLocationX =  location.latitude
                    mLocationY = location.longitude
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLocationX, mLocationY), 14.0f))
                    if(markerAdded) {
                        addedMarker.remove()
                        markerAdded = false
                    } else {
                        markerAdded = true
                        addedMarker = mMap.addMarker(MarkerOptions().position(LatLng(mLocationX, mLocationY)).title("my location"))
                    }
                    Log.d("locationgsg", mLocationX.toString() + "," + mLocationY.toString())
                    volleyRequest(mLocationX, mLocationY)
                }
            }
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i("tag", " Suspended");
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("tag", "Connection failed. Error: " + p0.errorCode)
    }

    private fun volleyRequest(mLocationX: Double, mLocationY: Double){
        val queue = Volley.newRequestQueue(this)
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                mLocationX + "%2C" + mLocationY + "&radius=" + 5000 + "&type=" + "park" +
                "&keyword=" + "" + "&key=AIzaSyAFjsbOViOoJg6LPFlKxOSXAOFPTVYEamE"
        Log.d("urlrgrdf", url)
        val stringRequest = StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                    Log.d("ERRtshergcdfOR", "response => " + response.toString())

                    for(i in 0 until JSONObject(response).getJSONArray("results").length() - 1) {
                        val ltlng = LatLng(JSONObject(response).getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                                    JSONObject(response).getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"))
                        val mo = MarkerOptions().position(ltlng).title(JSONObject(response).getJSONArray("results").getJSONObject(i).getString("name"))
                            .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((resources.getDrawable(
                                R.drawable.park_icon
                            )) as BitmapDrawable).bitmap, 70, 70, false)))
                        mMap.addMarker(mo)
                    }
            },
            Response.ErrorListener {error ->
                Log.d("ERRtshergcdfOR", "error => " + error.toString())
            })
        queue.add(stringRequest)
    }
    private fun initObjects() {

        databaseHelper = DatabaseHelper(this@GoogleMapsActivity)


    }


    companion object {
        private val IMAGE_DIRECTORY = "/nalhdaf"
    }
}

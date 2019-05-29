package com.example.rafael.my_application.activity

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.rafael.my_application.R
import kotlinx.android.synthetic.main.activity_sms.*
import kotlinx.android.synthetic.main.activity_sms.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SmsActivity : AppCompatActivity() {
    private var select_button: Button? = null
    private var imageViewSelect: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private var uriToSend : Uri? = null
    lateinit var preferences: SharedPreferences
    var string = ""
    // lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        select_button = findViewById(R.id.select_button) as Button
        imageViewSelect = findViewById(R.id.imageViewSelect) as ImageView
        smsText.visibility=View.GONE


        select_button!!.setOnClickListener { showPictureDialog() }

        send_sms.setOnClickListener {
            val loc_text: String = loc_editText.text.toString().trim()
            val sms_text: String = sms_editText.text.toString().trim()
                sendEmail(loc_text, sms_text)
            loc_editText!!.text=null
            sms_editText!!.text=null
            notific()

         imageViewSelect!!.setImageResource(R.drawable.ic_image_black_24dp)
            smsText.visibility=View.VISIBLE


        }


    }

    private fun sendEmail(loc_text: String, sms_text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("zveda.hayrapetyan.97@mail.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, loc_text)
        intent.putExtra(Intent.EXTRA_TEXT, sms_text)
        if(uriToSend != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uriToSend)
        }
        startActivity(intent)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveImage(bitmap)
                    Toast.makeText(this@SmsActivity, "Image Show!", Toast.LENGTH_SHORT).show()
                    imageViewSelect!!.setImageBitmap(bitmap)
                    uriToSend = contentURI!!
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@SmsActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            val camera_img = data!!.extras!!.get("data") as Bitmap
            imageViewSelect!!.setImageBitmap(camera_img)
            saveImage(camera_img)
            Toast.makeText(
                this@SmsActivity
                , "Photo Show!", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".png")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.getPath()), arrayOf("image/png"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }

    private fun showPictureDialog() {

        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Ընտրեք տարբերակ")
        val pictureDialogItems = arrayOf("Բեռնել նկար ", "Նկարել")
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

    fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA)
    }
    fun notific(){
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            //  notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val uri:Uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification= NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setContentTitle("QBITS")
            .setContentText("Դուք ցանկանում եք  ուղարկել հաղորդագր-ն" )
            .setSmallIcon(R.drawable.savelogo)
            .setPriority(Notification.PRIORITY_MAX)
            .setVibrate(longArrayOf(500,1000))
            .setLights(12,100,3000)
            .setSound(uri)


        notificationManager.notify(1,notification.build())

    }


    companion object {
        private val IMAGE_DIRECTORY = "/nalhdaf"
    }
}




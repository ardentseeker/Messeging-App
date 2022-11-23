package com.example.sendingsmstomobileno

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var msg:EditText
    lateinit var mob:EditText
    lateinit var send:Button

    private var userMsg:String = ""
    private var mobNo:String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        msg = findViewById(R.id.editTextTextMultiLine)
        mob = findViewById(R.id.editTextPhone)
        send = findViewById(R.id.button)

        send.setOnClickListener {

            userMsg = msg.text.toString()
            mobNo = mob.text.toString()

            sendMsg(userMsg,mobNo)

            msg.setText("")
            mob.setText("")
        }

    }

    private fun sendMsg(msg:String, phoneNo:String){

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
                  != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),1)
        }
        else
        {
            /*

            //new type of decleration

            val smsManager:SmsManager = if (Build.VERSION.SDK_INT >= 23) {
                this.getSystemService(SmsManager::class.java)
            } else {
                SmsManager.getDefault()
            }

             */

            val smsManager:SmsManager

            if (Build.VERSION.SDK_INT >= 23)
            {
                smsManager = this.getSystemService(SmsManager::class.java)
            }
            else
            {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(mobNo.toString(),null,userMsg,null,null)
            Toast.makeText(applicationContext,"Message Sent ",Toast.LENGTH_LONG).show()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            val smsManager:SmsManager

            if (Build.VERSION.SDK_INT >= 23)
            {
                smsManager = this.getSystemService(SmsManager::class.java)
            }
            else
            {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(mobNo.toString(),null,userMsg,null,null)

            Toast.makeText(applicationContext,"Message Sent ",Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(applicationContext,"Permission Denied ",Toast.LENGTH_LONG).show()

        }
    }




}
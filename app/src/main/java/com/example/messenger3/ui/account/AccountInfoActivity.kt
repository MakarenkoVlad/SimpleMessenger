package com.example.messenger3.ui.account


import android.content.Intent

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.messenger3.R
import com.example.messenger3.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account_info.*

class AccountInfoActivity : AppCompatActivity() {
    private var  pictUri = Uri.EMPTY
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        push_data_button.setOnClickListener {
            pushData()
        }

        pick_picture_button.setOnClickListener {
            pickPicture()
        }

        uploadDataIfExist()


    }


    private fun pushData(){

        val name = account_name_text_view.text.toString()
        val surname = account_surname_text_view.text.toString()

        if (name == "" || surname == ""){
            account_name_text_view.setHintTextColor(Color.RED)
            account_surname_text_view.setHintTextColor(Color.RED)
            return
        }

        AccountInfoViewModel.putPicture(name, userId!!, pictUri)

        AccountInfoViewModel.urlLiveData.observe(this, Observer {url ->
            AccountInfoViewModel.putUser(User(
                userId,
                name,
                surname,
                url.toString(),
                ""
            ))
        })


        AccountInfoViewModel.resultLiveData.observe(this, Observer {result ->
            if (result.first){
                toast("Successfully pushed data")
                Log.d("Messenger", "Successfully pushed data")
                data_pushed_text_view.setTextColor(Color.GRAY)
            }
            else{
                toast("Data hasn't been pushed, reason:${result.second}")
                Log.d("Messenger", "Data hasn't been pushed")
            }
        })



    }

    private fun uploadDataIfExist(){
        AccountInfoViewModel.getUser(userId!!)
        AccountInfoViewModel.currentUserLiveData.observe(this, Observer {
            if (it.name.isNullOrEmpty()) return@Observer
            account_name_text_view.hint = it.name
            account_surname_text_view.hint = it.surname
            Glide.with(this).load(it.downloadURL).into(pick_picture_button)
        })

    }


    private fun pickPicture(){
        val pPicker = Intent()
        pPicker.type = "image/*"
        pPicker.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(pPicker, "Select picture"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            1 -> {
                if (data?.data == null) return
                pictUri = data.data!!
                pick_picture_button.setImageDrawable(makeDrawable(pictUri))
            }
        }
    }

    private fun makeDrawable(uriOfPicture: Uri): Drawable{
        return Drawable.createFromStream(contentResolver.openInputStream(uriOfPicture), uriOfPicture.toString())
    }
}

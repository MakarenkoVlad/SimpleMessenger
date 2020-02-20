package com.example.messenger3


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_account_info.*
import java.io.File
import java.util.*

class AccountInfoActivity : AppCompatActivity() {
    private var  pictUri = Uri.EMPTY
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        push_data_button.setOnClickListener {
            pushData()
        }

        pick_picture_button.setOnClickListener {
            pickPicture()
        }

        uploadDataIfExist()


    }


    private fun pushData(){
        val userDocument = FirebaseFirestore.getInstance().collection("users").document("$userId")

//        If exist must not create new
        var uid: String = ""
        userDocument.get()
            .addOnSuccessListener {
                uid = it["UId"].toString()
            }
        uid = if (uid == "") "i" + UUID.randomUUID().toString() else uid
//        Toast.makeText(this, "${uid}", Toast.LENGTH_LONG).show()

        val name = account_name_text_view.text.toString()
        val surname = account_surname_text_view.text.toString()
        val accountInfo = hashMapOf(
            "Name" to name,
            "Surname" to surname,
            "UId" to uid
        )

        if (name == "" || surname == ""){
            account_name_text_view.setHintTextColor(Color.RED)
            account_surname_text_view.setHintTextColor(Color.RED)
            return
        }

        userDocument.set(accountInfo).addOnSuccessListener {
            Toast.makeText(this, "Successfully pushed data", Toast.LENGTH_LONG).show()
            Log.d("Messenger", "Successfully pushed data")
            data_pushed_text_view.setTextColor(Color.GRAY)

            uploadPhoto(uid)

        }.addOnFailureListener {
            Toast.makeText(this, "Data hasn't been pushed", Toast.LENGTH_LONG).show()
            Log.d("Messenger", "Data hasn't been pushed")
        }
    }

    private fun uploadDataIfExist(){
        //       Uploading name and surname and photo if exist
        FirebaseFirestore.getInstance().collection("users").document(userId!!).get()
            .addOnSuccessListener {documentSnapshot ->
                if (documentSnapshot["Name"].toString() == "null") return@addOnSuccessListener
                account_name_text_view.hint = documentSnapshot["Name"].toString()
                account_surname_text_view.hint = documentSnapshot["Surname"].toString()

                val uid = documentSnapshot["UId"].toString()
                //      Uploading photo
                var childRef = FirebaseStorage.getInstance().reference.child("images/${uid}")
                var file = File.createTempFile("picture", "webp")
                childRef.getFile(file)
                    .addOnSuccessListener {
                        pick_picture_button.foreground = Drawable.createFromPath(file.path)
                        Log.d("Messenger", "Image has been uploaded")
                    }
                    .addOnFailureListener{
                        Log.e("Messenger", "UId: ${uid}  Reason: ${it.message}")
                    }
            }
    }

    private fun uploadPhoto(id: String){
//        if (pictUri == null){
//            Log.d("Messenger", "Picture URI is NULL")
//        }
        FirebaseStorage.getInstance().getReference("images/${id}").putFile(pictUri).addOnSuccessListener {
            Log.d("Messenger", "Picture has uploaded")
        }
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
                pictUri = data?.data!!
                pick_picture_button.text = ""
                pick_picture_button.foreground = makeDrawable(pictUri)
            }
        }
    }

    private fun makeDrawable(uriOfPicture: Uri): Drawable{
        return Drawable.createFromStream(contentResolver.openInputStream(uriOfPicture), uriOfPicture.toString())
    }
}

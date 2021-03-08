package ru.androidlab.todoproj.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.databinding.ProfileBinding
import com.bumptech.glide.Glide
import ru.androidlab.todoproj.MainActivity

class ProfileActivity : AppCompatActivity() {

   private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        binding.idTxt.text = currentUser?.uid
        binding.nameTxt.text = currentUser?.displayName
        binding.emailTxt.text = currentUser?.email

        Glide.with(this).load(currentUser?.photoUrl).into(binding.profileImage)

        binding.signOutBtn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
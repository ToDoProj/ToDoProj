package ru.androidlab.todoproj.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.androidlab.todoproj.MainActivity
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.databinding.FragmentProfileBinding
import ru.androidlab.todoproj.views.activity.ProfileActivity
import ru.androidlab.todoproj.views.activity.TaskSetupActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    companion object {
        private const val RC_SIGN_IN = 120
        const val AUTHORISATION_STATUS = false
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        //Firebase Auth instance
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        binding.signOutBtn.setOnClickListener {
            mAuth.signOut()
            googleSignInClient.signOut()

            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            signIn()
            if(user != null){
                binding.constrainSignIn.visibility = View.VISIBLE
                binding.constrainSingOut.visibility = View.INVISIBLE
            }
        }

        if(user != null){
            binding.constrainSignIn.visibility = View.VISIBLE
        }else{
            binding.constrainSingOut.visibility = View.VISIBLE
        }

        setProfileValue()
        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, MainActivity.RC_SIGN_IN)
    }

    fun setProfileValue() {
        val currentUser = mAuth.currentUser

        binding.nameTxt.text = currentUser?.displayName
        binding.emailTxt.text = currentUser?.email

        Glide.with(this).load(currentUser?.photoUrl).into(binding.profileImage)
    }

    override fun onResume() {
        setProfileValue()
        super.onResume()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SettingsFragment", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SettingsFragment", "Google sign in failed", e)
                }
            } else {
                Log.w("SettingsFragment", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    Toast.makeText(context, "Enter success", Toast.LENGTH_SHORT).show()
                    binding.constrainSignIn.visibility = View.VISIBLE
                    binding.constrainSingOut.visibility = View.INVISIBLE
                    setProfileValue()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SignInActivity", "signInWithCredential:failure")
                }
            }
    }

}
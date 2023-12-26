package com.example.movieapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSigninScreenBinding
import com.example.movieapp.utils.LoadingDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SigninScreen : Fragment() {
    private lateinit var binding : FragmentSigninScreenBinding
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninScreenBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.singInSubmit.setOnClickListener{
            registerUser()
        }
        binding.signUpTxt.setOnClickListener{
            findNavController().navigate(R.id.action_signinScreen_to_signupScreen2)
        }
        binding.signInGoogle.setOnClickListener{
            val option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webClient_id))
                .requestEmail()
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(),option)
            signInClient.signInIntent.also {
                startActivityForResult(it,0)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            val accout = GoogleSignIn.getSignedInAccountFromIntent(data).result
            accout?.let {
                googleAuthForFirebase(it)
            }
        }
    }
    private fun googleAuthForFirebase(accout: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(accout.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credential).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),"Successfully logged in ",Toast.LENGTH_LONG).show()

                }
            }
            catch(e: java.lang.Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun registerUser(){
        val email = binding.siginEmail.text.toString()
        val password = binding.signinPass.text.toString()
        val loading = LoadingDialog(requireActivity())
        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch{
                try {
                    auth.signInWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),"Successful", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_signinScreen_to_mainActivity2)
                    }
                }
                catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),e.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }


}
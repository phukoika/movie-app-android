package com.example.movieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSignupScreenBinding
import com.example.movieapp.utils.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.wait


class SignupScreen : Fragment() {
    lateinit var binding : FragmentSignupScreenBinding
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
       binding.signUpBtn.setOnClickListener{
           registerUser()
       }


    }

    fun registerUser(){
        val email = binding.signUpEmail.text.toString()
        val password = binding.signUpPass.text.toString()
        val loading = LoadingDialog(requireActivity())
        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch{
                try {
                   auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        val bundle = bundleOf(
                            "id" to auth.currentUser?.uid.toString()
                        )
                        findNavController().navigate(R.id.action_signupScreen2_to_profileSetupFragment,bundle)
                    }
                }
                catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }


}
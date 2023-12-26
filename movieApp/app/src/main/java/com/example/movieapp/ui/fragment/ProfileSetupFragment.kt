package com.example.movieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentProfileSetupBinding
import com.example.movieapp.models.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile_setup.view.*


class ProfileSetupFragment : Fragment() {
    private lateinit var binding : FragmentProfileSetupBinding
    val args: ProfileSetupFragmentArgs by navArgs()
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileSetupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        database = FirebaseDatabase.getInstance().getReference("Users")
        binding.submitProfile.setOnClickListener{
            val userName =  binding.signInUserName.text.toString()
            val url = binding.avaProfile.resources.toString()
//            Toast.makeText(requireContext(),userName + url + id ,Toast.LENGTH_LONG).show()
            val user = User(userName,url)
            database.child(id).setValue(user).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(requireContext(),"update successful" ,Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_profileSetupFragment_to_mainActivity2)
                }
                else{
                    Toast.makeText(requireContext(),"failed" ,Toast.LENGTH_LONG).show()
                }
            }
        }



    }


}
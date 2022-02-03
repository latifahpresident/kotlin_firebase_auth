package com.latifah.authdemo

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.latifah.authdemo.databinding.FragmentRegisterBinding

/*
* STEP 8 AFTER CREATING FRAGMENTS CREATE NAVIGATION IN THE NAV GRAPH */

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
//    private val registerEmail = binding.editTextTextEmailAddress
//    private val registerPassword = binding.editTextTextPassword

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.tvLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerUser () {
        when {
            TextUtils.isEmpty(binding.editTextTextEmailAddress.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    activity,
                    "Please enter email",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(binding.editTextTextPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    activity,
                    "Please enter password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                val email : String = binding.editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                val password : String = binding.editTextTextPassword.text.toString().trim { it <= ' '}

                //Create an instance and register a user with an email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // double exclamation !! is used when you're sure a value will not be null.
                            // If the value is null it'll throw a NullPointerException
                            val user: FirebaseUser = task.result!!.user!!
                            Log.d("Register function", "user uid: ${user.uid}")
                            Toast.makeText(
                                activity,
                                "Welcome new user!",
                                Toast.LENGTH_SHORT
                            ).show()

                            //Now move to the profile screen
                            val action = RegisterFragmentDirections.actionRegisterFragmentToProfileFragment(user.uid)
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                activity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
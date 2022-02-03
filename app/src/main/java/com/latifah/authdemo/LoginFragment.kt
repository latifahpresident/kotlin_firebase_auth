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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.latifah.authdemo.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun loginUser() {
        //First check to make sure that the fields aren't blank
        when {
            TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    activity,
                    "Please enter an email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    activity,
                    "Please enter password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                val email : String = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                val password : String = binding.etLoginPassword.text.toString().trim { it <= ' '}

                activity?.let {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(it) {task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser!!
                                Log.d("login function", "loginUser: $user")
                                val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment(user.uid)
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Unable to login ${task.exception!!.message}",
                                    Toast.LENGTH_SHORT
                                )
                            }
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
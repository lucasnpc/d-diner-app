package com.example.ddinerapp.featureAuthentication.presentation.signUp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ddinerapp.R
import com.example.ddinerapp.databinding.FragmentSignupBinding
import com.example.ddinerapp.featureMain.presentation.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment() {

    private val binding: FragmentSignupBinding by lazy {
        FragmentSignupBinding.inflate(layoutInflater)
    }
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        } else
            Snackbar.make(requireView(), getString(R.string.error_happened), Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            email.setOnClickListener { openSignInTools() }
            google.setOnClickListener { openSignInTools(google = true) }
        }
    }

    private fun openSignInTools(google: Boolean = false) {
        signInLauncher.launch(AuthUI.getInstance().createSignInIntentBuilder().apply {
            if (google) setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
        }.build())
    }
}
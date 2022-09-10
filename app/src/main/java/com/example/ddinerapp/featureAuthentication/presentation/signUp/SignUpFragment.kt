package com.example.ddinerapp.featureAuthentication.presentation.signUp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.common.util.FirebaseUserLiveData
import com.example.ddinerapp.databinding.FragmentSignupBinding
import com.example.ddinerapp.featureMain.presentation.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val binding: FragmentSignupBinding by lazy {
        FragmentSignupBinding.inflate(layoutInflater)
    }
    private val viewModel: SignUpViewModel by viewModels()
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            Snackbar.make(requireView(), getString(R.string.error_happened), Snackbar.LENGTH_SHORT)
                .show()
        }
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
        observeFirebaseUser()
        observeLoading()
        observeAuthentication()
    }

    private fun observeLoading() {
        lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                setupComponentsVisibility(isLoading)
            }
        }
    }

    private fun observeFirebaseUser() {
        FirebaseUserLiveData().observe(viewLifecycleOwner) {
            if (it != null)
                viewModel.validateUser(it)
        }
    }

    private fun observeAuthentication() {
        lifecycleScope.launch {
            viewModel.authenticationState.collectLatest { state ->
                when (state) {
                    AuthenticationState.AUTHENTICATED -> {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                    AuthenticationState.INVALID_AUTHENTICATION -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.invalid_authentication),
                            Toast.LENGTH_LONG
                        ).show()
                        AuthUI.getInstance().signOut(requireContext())
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun openSignInTools(google: Boolean = false) {
        signInLauncher.launch(AuthUI.getInstance().createSignInIntentBuilder().apply {
            if (google) setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
        }.build())
    }

    private fun setupComponentsVisibility(loading: Boolean) {
        binding.run {
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            google.isEnabled = !loading
            email.isEnabled = !loading
        }
    }
}
package com.example.co_working.view.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.co_working.R
import com.example.co_working.databinding.FragmentLoginBinding
import com.example.co_working.model.request.LoginRequestModel
import com.example.co_working.utils.Resource
import com.example.co_working.utils.hideKeyboard
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    @ExperimentalCoroutinesApi
    override val viewModel: LoginViewModel by viewModels()
    lateinit var stringEmailorMobile: String
    lateinit var stringPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doinits()
    }

    private fun doinits() = with(binding) {

        buttonLogin.setOnClickListener {
            hideKeyboard()
            stringEmailorMobile = edEmailormobileLogin.text.toString().trim()
            stringPassword = edPasswordLogin.text.toString().trim()

            if(stringEmailorMobile.isEmpty() || stringPassword.isEmpty()){
                Toast.makeText(context, "Please add email and passowrd", Toast.LENGTH_SHORT).show()
            }else{
                getLogin()

            }

        }

        buttonRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }
    }

    private fun getLogin() {
        val dataModelLoginBody = LoginRequestModel(
            stringEmailorMobile,
            stringPassword
        )
        viewModel.loginUser(dataModelLoginBody)
        viewModel.loginData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                Log.d("============response", response.message.toString())
                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { toast(it) }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })
    }

    private fun showProgressBar() {
        progressbar_login.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_login.visibility = View.GONE
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)
}
package com.example.co_working.view.createaccount

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.provider.Settings
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.co_working.R
import com.example.co_working.databinding.FragmentCreateaccountBinding
import com.example.co_working.model.request.CreateAccountRequestModel
import com.example.co_working.utils.Resource
import com.example.co_working.utils.hideKeyboard
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.viewmodel.CreateAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.regex.Pattern

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CreateAccountFragment : BaseFragment<FragmentCreateaccountBinding, CreateAccountViewModel>() {
    @ExperimentalCoroutinesApi
    override val viewModel: CreateAccountViewModel by viewModels()
    lateinit var stringEmailorMobile: String
    lateinit var stringFullnameLogin: String
    lateinit var stringNumber: String
    lateinit var deviceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doinits()
    }

    private fun doinits() = with(binding) {

        buttonLogin.setOnClickListener {
            hideKeyboard()
            stringEmailorMobile = edEmailormobileLogin.text.toString().trim()
            stringFullnameLogin = edFullnameLogin.text.toString().trim()
            stringNumber = edNumberLogin.text.toString().trim()
            if  (ed_emailormobile_login.text.toString()
                        .isEmpty() or !isValidEmailaddress(stringEmailorMobile) ) {
                return@setOnClickListener
            } else {
                createAccountUser()
            }
        }

        loginTv.setOnClickListener{
            findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
        }
    }

    private fun createAccountUser() {
        val createAccountRequestModel = CreateAccountRequestModel(
            stringEmailorMobile,
            stringNumber
        )
        viewModel.createAccountUser(createAccountRequestModel)
        viewModel.loginData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                Log.d("============createAccountUser", response.message.toString())
                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
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


    private fun isValidEmailaddress(email: String): Boolean {

        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"

        val pat = Pattern.compile(emailRegex)
        return pat.matcher(email).matches()

    }




    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateaccountBinding.inflate(inflater, container, false)
}
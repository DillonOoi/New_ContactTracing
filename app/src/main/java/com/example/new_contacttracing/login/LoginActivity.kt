package com.example.new_contacttracing.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.new_contacttracing.MainActivity
import com.example.new_contacttracing.R
import com.example.new_contacttracing.database.RegisterDatabase
import com.example.new_contacttracing.database.RegisterRepository
import com.example.new_contacttracing.databinding.ActivityLoginBinding
import com.example.new_contacttracing.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var databinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // navigate to register screen
        databinding.switchToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao()

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        databinding.viewModel = loginViewModel

        databinding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.errotoast.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoUserDetails.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","Login Successful")
                loginViewModel.doneNavigatingUserDetails()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

//    private fun displayHomeFragment() {
//        Log.i("MYTAG","insidisplayUsersList")
//        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
//        NavHostFragment.findNavController(this).navigate(action)
//
//    }
//
//    private fun navigateHomeFragment() {
//        Log.i("MYTAG","insidisplayUsersList")
//        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//        NavHostFragment.findNavController(this).navigate(action)
//    }
}
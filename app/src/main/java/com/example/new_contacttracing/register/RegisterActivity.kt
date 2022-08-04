package com.example.new_contacttracing.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.new_contacttracing.R
import com.example.new_contacttracing.database.RegisterDatabase
import com.example.new_contacttracing.database.RegisterRepository
import com.example.new_contacttracing.databinding.ActivityRegisterBinding
import com.example.new_contacttracing.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var databinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        // navigate to login screen
        databinding.switchToLogIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao()

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        databinding.viewModel = registerViewModel

        databinding.lifecycleOwner = this


        registerViewModel.navigateto.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(this, Observer {
            Log.i("MYTAG",it.toString()+"000000000000000000000000")
        })


        registerViewModel.errotoast.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errotoastUsername.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "UserName Already taken", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })
    }
}


package com.example.githubbrowser.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.githubbrowser.BuildConfig
import com.example.githubbrowser.R
import com.example.githubbrowser.storage.GithubBrowserDatabase
import com.example.githubbrowser.viewmodel.AuthViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: AuthViewModel
    private lateinit var navViewButton: Button
    private lateinit var navAvatar: ImageView
    private lateinit var navNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Setup DB
        GithubBrowserDatabase.buildDatabase(this)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        // Setup Button State
        with(navView.getHeaderView(0)) {
            navViewButton = findViewById(R.id.button)
            navAvatar = findViewById(R.id.avatar)
            navNameTextView = findViewById(R.id.name_text_view)
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.myRepoFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()

        viewModel.checkLogged()

        viewModel.isLogin.observe(this, Observer { isLogin ->
            if (isLogin) {
                setButtonAsLogout()
            } else {
                setButtonAsLogin()
            }
        })

        val uri = intent.data
        if (uri.toString().startsWith(BuildConfig.GITHUB_CLIENT_REDIRECT_URL)) {
            val code = uri?.getQueryParameter("code")
            Log.d(javaClass.simpleName, uri.toString())

            code?.run {
                viewModel.login(this)
            }
        }

        viewModel.userData.observe(this, Observer {
            setButtonAsLogout()
            navNameTextView.text = it?.name ?: resources.getString(R.string.nav_header_user)
            Glide.with(this)
                .load(it?.avatarUrl ?: "")
                .error(R.drawable.github_octocat)
                .into(navAvatar)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setButtonAsLogin() {
        with(navViewButton) {
            text = resources.getString(R.string.nav_header_button_login)
            setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.GITHUB_CLIENT_ID}&scope=repo&redirect_url=${BuildConfig.GITHUB_CLIENT_REDIRECT_URL}")
                    )
                )
            }
        }
        navNameTextView.text = resources.getString(R.string.nav_header_user)
        Glide.with(this).load(R.drawable.github_octocat).into(navAvatar)
    }

    private fun setButtonAsLogout() {
        with(navViewButton) {
            text = resources.getString(R.string.nav_header_button_logout)
            setOnClickListener {
                viewModel.logout()
            }
        }
    }
}

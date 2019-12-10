package com.example.githubbrowser.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.githubbrowser.BuildConfig
import com.example.githubbrowser.R
import com.example.githubbrowser.storage.GithubBrowserDatabase
import com.example.githubbrowser.viewmodel.AuthViewModel

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
        GithubBrowserDatabase.buildDatabase(applicationContext)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        // TODO
        // Setup Button State
        with(navView.getHeaderView(0)) {
            navViewButton = findViewById(R.id.button)
            navAvatar = findViewById(R.id.avatar)
            navNameTextView = findViewById(R.id.name_text_view)
        }

        viewModel.login(null)

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

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()

        viewModel.userData.observe(this, Observer {
            setButtonAsLogout()
            navNameTextView.text = it.name
            Glide.with(this).load(it.avatarUrl).into(navAvatar)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setButtonAsLogin() {
        with(navViewButton) {
            text = "登入"
            setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.GITHUB_CLIENT_ID}&scope=repo&redirect_url=${BuildConfig.GITHUB_CLIENT_REDIRECT_URL}")
                    )
                )
            }
        }
        navNameTextView.text = resources.getString(R.string.nav_header_title)
        Glide.with(this).load(R.drawable.github_octocat).into(navAvatar)
    }

    private fun setButtonAsLogout() {
        with(navViewButton) {
            text = "登出"
            setOnClickListener {
                viewModel.logout()
            }
        }
    }
}

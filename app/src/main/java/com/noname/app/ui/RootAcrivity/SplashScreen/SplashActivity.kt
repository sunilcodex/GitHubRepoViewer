package com.noname.app.ui.RootAcrivity.SplashScreen

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.noname.app.R
import com.noname.app.databinding.ActivitySplashBinding
import com.noname.app.ui.Main.gitHub.GitHubRepoActivity


class SplashActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window: Window = getWindow()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(
                getResources().getColor(
                    R.color.colorPrimary,
                    this.getTheme()
                )
            );
            window.setNavigationBarColor(
                getResources().getColor(
                    R.color.transparent,
                    this.getTheme()
                )
            );
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary))
            window.setNavigationBarColor(getResources().getColor(R.color.transparent))
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.consoleLoadingView.setTextList(loadingSteps)


        countDownTimer = object : CountDownTimer(4500, 600) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                launchHomeScreen()
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer!!.cancel()
    }


    // Example Usage:
     val loadingSteps = listOf(
        "Initializing POS system..." to Color.LTGRAY,
        "Loading menu items..." to Color.LTGRAY,
        "Connecting to printer..." to Color.LTGRAY,
        "Checking inventory..." to Color.LTGRAY,
        "Loading customer database..." to Color.LTGRAY,
        "Syncing cloud data..." to Color.LTGRAY,
        "Setting up payment gateway..." to Color.LTGRAY,
        "Applying discounts and offers..." to Color.LTGRAY,
        "Optimizing UI settings..." to Color.LTGRAY,
        "Fetching latest orders..." to Color.LTGRAY,
        "Preparing tax calculations..." to Color.LTGRAY,
        "Checking internet connection..." to Color.LTGRAY,
        "Verifying card reader..." to Color.LTGRAY,
        "Loading employee shifts..." to Color.LTGRAY,
        "Updating software version..." to Color.LTGRAY,
        "Checking kitchen inventory..." to Color.LTGRAY,
        "Syncing tables and reservations..." to Color.LTGRAY,
        "Fetching latest promotions..." to Color.LTGRAY,
        "Configuring printer settings..." to Color.LTGRAY,
        "Scanning barcode database..." to Color.LTGRAY,
        "Loading delivery partner details..." to Color.LTGRAY,
        "Initializing analytics dashboard..." to Color.LTGRAY,
        "Preparing sales report..." to Color.LTGRAY,
        "Optimizing database queries..." to Color.LTGRAY,
        "Loading feedback system..." to Color.LTGRAY,
        "Fetching pending transactions..." to Color.LTGRAY,
        "Checking loyalty program..." to Color.LTGRAY,
        "Initializing security checks..." to Color.LTGRAY,
        "Updating cash register logs..." to Color.LTGRAY,
        "Loading dynamic pricing rules..." to Color.LTGRAY,
        "Verifying QR code scanner..." to Color.LTGRAY,
        "Fetching real-time updates..." to Color.LTGRAY,
        "Checking promotional campaigns..." to Color.LTGRAY,
        "Configuring table layout..." to Color.LTGRAY,
        "Setting up auto-ordering..." to Color.LTGRAY,
        "Syncing employee attendance..." to Color.LTGRAY,
        "Verifying user authentication..." to Color.LTGRAY,
        "Loading kitchen display system..." to Color.LTGRAY,
        "Checking pending invoices..." to Color.LTGRAY,
        "Updating POS security features..." to Color.LTGRAY,
        "Verifying tax compliance..." to Color.LTGRAY,
        "Fetching supplier details..." to Color.LTGRAY,
        "Checking stock expiration dates..." to Color.LTGRAY,
        "Initializing refund process..." to Color.LTGRAY,
        "Finalizing system setup..." to Color.LTGRAY
    )

    private fun launchHomeScreen() {
        val intent = Intent(this@SplashActivity, GitHubRepoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}
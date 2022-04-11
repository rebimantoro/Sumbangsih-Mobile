package com.feylabs.sumbangsih.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.feylabs.sumbangsih.R
import com.feylabs.sumbangsih.databinding.ActivityCommonControllerBinding
import com.feylabs.sumbangsih.util.BaseActivity
import com.feylabs.sumbangsih.util.DialogUtils
import com.feylabs.sumbangsih.util.sharedpref.RazPreferenceHelper
import org.koin.android.viewmodel.ext.android.viewModel


class CommonControllerActivity : BaseActivity() {

    private lateinit var binding: ActivityCommonControllerBinding
    private val commonViewModel by viewModel<CommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()

        binding = ActivityCommonControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_common_controller)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profileFragment,
                R.id.navigation_pengajuanBLTFragment
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        navView.
//        navController.navigate(R.id.navigation_createPinFragment)

        val loggedInPhoneNumber = RazPreferenceHelper.getPhoneNumber(this)

//        if (RazPreferenceHelper.isLoggedIn(this) && loggedInPhoneNumber.isNotEmpty()) {
//            navController.navigate(R.id.navigation_home)
//        }

        hideCustomTopbar()
        showNavView()
        hideActionBar()

        binding.btnNotification.setOnClickListener {
            getNavController().navigate(R.id.notificationFragment)
        }

        addNavChangedListener(navController)
    }

    private fun addNavChangedListener(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (getCurrentNav()) {
                R.id.navigation_createPinFragment, R.id.navigation_verifPinFragment,
                R.id.navigation_detailTutorialFragment, R.id.navigation_videoPlayerFragment
                -> {
                    hideCustomTopbar()
                    hideNavView()
                    hideActionBar()
                }
                R.id.navigation_home -> {
                    showNavView()
                    showCustomTopbar()
                }
                R.id.navigation_profileFragment -> {
                    showCustomTopbar()
                }
                R.id.navigation_listAllNewsFragment -> {
                    hideCustomTopbar()
                }
                R.id.navigation_pengajuanBLTFragment -> {
                    showCustomTopbar()
                    showNavView()
                }
                else -> {
                    showNavView()
                    showActionBar()
                }
            }

            /*
            Show Topbar and NavView only on main menu
             */
            when (getCurrentNav()) {
                R.id.navigation_home, R.id.navigation_profileFragment, R.id.navigation_pengajuanBLTFragment -> {
                    showNavView()
                    showCustomTopbar()
                }
                else -> {
                    hideNavView()
                    hideCustomTopbar()
                }
            }

        }

    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
    }

    override fun onBackPressed() {
        when (getCurrentNav()) {
            R.id.navigation_home -> {
                DialogUtils.showCustomDialog(
                    context = this,
                    title = "Anda Yakin ?",
                    message = "Ingin Menutup Aplikasi Sumbangsih ?",
                    positiveAction = Pair("Ya", {
                        finish();
                        System.exit(0);
                    }),
                    negativeAction = Pair("Tidak", {
                    }),
                    autoDismiss = true,
                    buttonAllCaps = false
                )
            }
            R.id.navigation_createPinFragment -> {
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun overrideBottomMenu(isDisabled: Boolean = false, title: String = "", reason: String = "") {
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_pengajuanBLTFragment -> {
                    if (isDisabled) {
                        DialogUtils.showCustomDialog(
                            context = this,
                            title = title,
                            message = reason,
                            positiveAction = Pair(getString(R.string.dialog_ok), {

                            }),
                            autoDismiss = true,
                            buttonAllCaps = false
                        )
                    } else {
                        getNavController().navigate(R.id.navigation_pengajuanBLTFragment)
                    }
                }
                R.id.navigation_home -> {
                    getNavController().navigate(R.id.navigation_home)
                }
                R.id.navigation_profileFragment -> {
                    getNavController().navigate(R.id.navigation_profileFragment)
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    fun getNavController(): NavController {
        return findNavController(R.id.nav_host_fragment_activity_common_controller)
    }

    fun hideCustomTopbar() {
        findViewById<Toolbar>(R.id.toolbar).makeViewGone()
        binding.toolbar.makeViewGone()
    }

    fun showCustomTopbar() {
        findViewById<Toolbar>(R.id.toolbar).makeViewVisible()
        binding.toolbar.makeViewVisible()
    }

    fun showNavView() {
        binding.navView.visibility = View.VISIBLE
//        AnimUtil.animateFromResource(binding.navView,R.anim.anim_slide_in_top)
    }

    fun hideNavView() {
//        AnimUtil.animateFromResource(binding.navView, R.anim.anim_slide_out_bottom)
        binding.navView.visibility = View.GONE
    }

    private fun getCurrentNav(): Int? {
        val navController = findNavController(R.id.nav_host_fragment_activity_common_controller)
        return navController.currentDestination?.id
    }
}
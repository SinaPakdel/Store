package com.sina.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sina.core.common.constants.Constants.Companion.ID
import com.sina.core.common.permission.PermissionManager
import com.sina.core.local.datastore.AppDataStore
import com.sina.presentation.notification.NotificationHelper
import com.sina.presentation.R
import com.sina.presentation.databinding.ActivityMainBinding
import com.sina.presentation.setting.StoreWorker
import com.sina.presentation.main.navigation.StartDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var notificationHelper: NotificationHelper
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    @Inject
    lateinit var appDataStore: AppDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        observers()
        PermissionManager.initialize(this)

        /**
         * this is test
        if (PermissionManager.checkNotificationPermission(this)) {
        notificationHelper.showNotification("عنوان نوتیفیکیشن", "متن نوتیفیکیشن")
        } else PermissionManager.requestNotificationPermission()
         */

        createOneTimeRequest()
        createPeriodicTimeRequest()

    }

    private fun createPeriodicTimeRequest() {
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<StoreWorker>(15, TimeUnit.MINUTES).build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork(
            ID,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun createOneTimeRequest() {
        val oneTimeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<StoreWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(false)
                    .build()
            ).build()

        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

    }

    private fun observers() {
        lifecycleScope.launch {
            appDataStore.readUserLoggedIn.collectLatest {
                navController.graph =
                    navController.navInflater.inflate(R.navigation.nav_graph).apply {
                        setStartDestination(
                            when (it) {
                                true -> StartDestination.Main.destination
                                false -> StartDestination.Login.destination
                            }
                        )
                    }
            }
        }
    }

    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigation
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> navView.visibility = View.VISIBLE
                R.id.cartFragment -> navView.visibility = View.VISIBLE
                R.id.magnetFragment -> navView.visibility = View.VISIBLE
                R.id.categoryFragment -> navView.visibility = View.VISIBLE
                R.id.profileFragment -> navView.visibility = View.VISIBLE
                else -> navView.visibility = View.GONE
            }
        }
        notificationHelper = NotificationHelper(this)
    }
}
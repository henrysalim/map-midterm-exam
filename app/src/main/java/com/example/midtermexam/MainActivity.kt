package com.example.midtermexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi semua komponen UI
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        // 2. Jadikan Toolbar sebagai Action Bar utama aplikasi
        setSupportActionBar(toolbar)

        // 3. Konfigurasi halaman mana yang dianggap "level atas" (tidak punya tombol kembali)
        // Ganti ID ini sesuai dengan ID fragment di menu bawah Anda
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.articleFragment)
        )

        // 4. Hubungkan Action Bar (Toolbar) dan Bottom Navigation dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
    }

    /**
     * 5. Fungsi ini WAJIB ada agar tombol kembali di Toolbar berfungsi.
     * Ini akan menangani aksi klik pada panah kembali.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
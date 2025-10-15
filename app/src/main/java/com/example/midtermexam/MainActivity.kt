package com.example.midtermexam

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    // inisialisasi variabel untuk kemudian diinisasi saat create (onCreate())
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi semua komponen UI
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavView = findViewById(R.id.bottom_navigation)
        toolbar = findViewById(R.id.toolbar)

        // 2. Jadikan Toolbar sebagai Action Bar utama aplikasi
        setSupportActionBar(toolbar)

        // 3. Konfigurasi halaman mana yang dianggap "level atas" (tidak punya tombol kembali)
        // Ganti ID ini sesuai dengan ID fragment di menu bawah Anda
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.articleFragment)
        )

        // 4. Hubungkan Action Bar (Toolbar) dan Bottom Navigation dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        // atur visibilitas support action bar dan bottom nav view
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // jika di halaman detail article, tampilkan tombol back pada support action bar
            if (destination.id == R.id.articleDetailFragment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // selain di halaman detail article, jangan tampilkan tombol back
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

            // jika sedang di halaman login/register, jangan tampilkan bottom nav view
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                bottomNavView.visibility = View.GONE
            } else bottomNavView.visibility = View.VISIBLE
        }

    }

    /**
     * 5. Fungsi ini WAJIB ada agar tombol kembali di Toolbar berfungsi.
     * Ini akan menangani aksi klik pada panah kembali.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
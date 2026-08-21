package io.codingskuy.profilepage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupProfileList()
    }

    private fun setupProfileList() {
        // 1. Barangnya: data yang mau ditampilkan
        val profileItems = listOf(
            ProfileItem(1, "Akun Saya", R.drawable.ic_launcher_foreground),
            ProfileItem(2, "Pengaturan", R.drawable.ic_launcher_foreground),
            ProfileItem(3, "Bantuan", R.drawable.ic_launcher_foreground),
            ProfileItem(4, "Tentang Aplikasi", R.drawable.ic_launcher_foreground)
        )

        // 2. Sambungkan etalase (RecyclerView) ke penata rak + penerjemah
        val recyclerView: RecyclerView = findViewById(R.id.rv_profile_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProfileAdapter(profileItems)
    }
}
package io.codingskuy.todo.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import io.codingskuy.todo.R
import io.codingskuy.todo.databinding.ActivityFragmentBinding

class FragmentActivity : androidx.fragment.app.FragmentActivity() {
    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnHome.setOnClickListener {
            val hero = HeroFragment().apply {
                arguments = Bundle().apply {
                    putString("name", "Rois")
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, hero)
                .commit()
        }

        binding.btnSettings.setOnClickListener {
            val setting = SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString("name", "Khoiron")
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, setting)
                .commit()
        }
    }
}
package io.codingskuy.todo.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import io.codingskuy.todo.R
import io.codingskuy.todo.databinding.FragmentHeroBinding

class HeroFragment : androidx.fragment.app.Fragment() {
    private var _binding: FragmentHeroBinding? = null
    private val binding get() = this._binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hero, container, false)

        val name = arguments?.getString("name") ?: "Gues"

        binding.name = "Home with $name"
        return binding.root
    }

    companion object {
        fun newInstance() =
            HeroFragment().apply {
                arguments = Bundle().apply {                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
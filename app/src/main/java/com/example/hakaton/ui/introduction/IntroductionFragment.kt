package com.example.hakaton.ui.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hakaton.databinding.GlavniuEkranBinding

class IntroductionFragment : Fragment() {

    private var _binding: GlavniuEkranBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(IntroductionViewModel::class.java)

        _binding = GlavniuEkranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvQuality
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
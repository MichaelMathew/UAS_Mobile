package com.michael.urgalon.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.michael.urgalon.R
import com.michael.urgalon.databinding.FragmentIsiulangBinding
import com.michael.urgalon.entity.Layanan
import com.michael.urgalon.viewmodel.HomeViewModel

class IsiUlangFragment : Fragment() {
    private lateinit var isiUlangBinding: FragmentIsiulangBinding
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isiUlangBinding = FragmentIsiulangBinding.inflate(inflater,container,false)

        isiUlangBinding.jumlahisi.text = homeVM.amountIsiUlang.toString()

        isiUlangBinding.isiminus.setOnClickListener {
            if (homeVM.amountIsiUlang > 0)
                homeVM.amountIsiUlang--
            isiUlangBinding.jumlahisi.text = homeVM.amountIsiUlang.toString()
            homeVM.checkFab()
        }
        isiUlangBinding.isiplus.setOnClickListener {
            homeVM.amountIsiUlang++
            isiUlangBinding.jumlahisi.text = homeVM.amountIsiUlang.toString()
            homeVM.checkFab()
        }
        homeVM.checkFab()

        return isiUlangBinding.root
    }

    override fun onStart() {
        super.onStart()
        homeVM.checkFab()
    }

    companion object {
        @JvmStatic
        fun newInstance() = IsiUlangFragment()
    }
}
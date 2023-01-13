package com.michael.urgalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.michael.urgalon.databinding.ActivityHomeBinding
import com.michael.urgalon.fragment.HistoryFragment
import com.michael.urgalon.fragment.HomeFragment
import com.michael.urgalon.fragment.ProfileFragment
import com.michael.urgalon.fragment.VoucherFragment
import com.michael.urgalon.viewmodel.HomeViewModel
import com.michael.urgalon.viewmodel.MainViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val viewModel: MainViewModel by viewModels()
    val homeVM: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_history->replaceFragment(HistoryFragment())
                R.id.bottom_voucher -> replaceFragment(VoucherFragment())
                R.id.bottom_profile -> replaceFragment(ProfileFragment())

                else -> {

                }

            }
            true
        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame1,fragment)
        fragmentTransaction.commit()

    }

    fun changeBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.bottom_home
    }
}

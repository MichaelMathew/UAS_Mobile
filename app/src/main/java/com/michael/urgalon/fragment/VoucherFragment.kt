package com.michael.urgalon.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.urgalon.HomeActivity
import com.michael.urgalon.R
import com.michael.urgalon.adapter.VoucherAdapter
import com.michael.urgalon.api.ApiClient
import com.michael.urgalon.api.ApiService
import com.michael.urgalon.databinding.FragmentVoucherBinding
import com.michael.urgalon.entity.Voucher
import com.michael.urgalon.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoucherFragment : Fragment() {
    private lateinit var voucherBinding: FragmentVoucherBinding
    private lateinit var vouchers: ArrayList<Voucher>
    private lateinit var voucherAdapter: VoucherAdapter
    private lateinit var api: ApiService
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ApiClient.getApiClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        voucherBinding = FragmentVoucherBinding.inflate(inflater, container, false)
        vouchers = ArrayList()
        voucherAdapter = VoucherAdapter(vouchers)
        voucherAdapter.setListener(object : VoucherAdapter.VoucherListener {
            override fun onClick(voucher: Voucher) {
                if (homeVM.loggedUser.value!!.point!! >= voucher.point_voucher) {
                    homeVM.selectedVoucher = voucher
                    Toast.makeText(context, "Voucher terpilih", Toast.LENGTH_LONG).show()
                    (activity as HomeActivity).changeBottomNav()
                } else {
                    Toast.makeText(context, "Point tidak mencukupi", Toast.LENGTH_LONG).show()
                }

            }
        })
        voucherBinding.rvVoucher.adapter = voucherAdapter
        voucherBinding.rvVoucher.layoutManager = LinearLayoutManager(context)

        homeVM.loggedUser.observe(viewLifecycleOwner) {
            voucherBinding.tvPointnow.text = it.point.toString()
        }
        return voucherBinding.root
    }

    override fun onStart() {
        super.onStart()
        fetchVouchers()
    }

    private fun fetchVouchers() {
        api.getVouchers().enqueue(object : Callback<ArrayList<Voucher>> {
            override fun onResponse(
                call: Call<ArrayList<Voucher>>,
                response: Response<ArrayList<Voucher>>
            ) {
                response.body()?.let {
                    vouchers.addAll(it)
                }
                voucherAdapter.notifyItemChanged(0)
            }

            override fun onFailure(call: Call<ArrayList<Voucher>>, t: Throwable) {
                Log.d("VOUCHER", t.message.toString())
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = VoucherFragment()
    }
}
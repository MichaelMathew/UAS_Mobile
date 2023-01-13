package com.michael.urgalon.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.urgalon.R
import com.michael.urgalon.adapter.HomeDepotAdapter
import com.michael.urgalon.adapter.HomeGalonAdapter
import com.michael.urgalon.api.ApiClient
import com.michael.urgalon.api.ApiService
import com.michael.urgalon.databinding.FragmentBeliGalonBinding
import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Galon
import com.michael.urgalon.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeliGalonFragment : Fragment() {
    private lateinit var beliGalonBinding: FragmentBeliGalonBinding
    private lateinit var galons: ArrayList<Galon>
    private lateinit var galonAdapter: HomeGalonAdapter
    private lateinit var api: ApiService
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ApiClient.getApiClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        beliGalonBinding = FragmentBeliGalonBinding.inflate(inflater, container, false)

        galons = ArrayList()
        galonAdapter = HomeGalonAdapter(galons)
        galonAdapter.setListener(object : HomeGalonAdapter.HomeGalonListener {
            override fun onTambah(galon: Galon, jumlah: Int) {
                homeVM.selectedGalon[galon] = jumlah
                homeVM.checkFab()
            }

            override fun onKurang(galon: Galon, jumlah: Int) {
                if (jumlah == 0) {
                    homeVM.selectedGalon.remove(galon)
                } else {
                    homeVM.selectedGalon[galon] = jumlah
                }
                homeVM.checkFab()
            }
        })
        beliGalonBinding.rvGalonBeligalon.adapter = galonAdapter
        beliGalonBinding.rvGalonBeligalon.layoutManager = GridLayoutManager(context, 2)

        return beliGalonBinding.root
    }

    override fun onStart() {
        super.onStart()
        fetchGalons()
    }

    private fun fetchGalons() {
        api.getGalons().enqueue(object : Callback<ArrayList<Galon>> {
            override fun onResponse(
                call: Call<ArrayList<Galon>>,
                response: Response<ArrayList<Galon>>
            ) {
                galons.clear()
                response.body()?.let {
                    galons.addAll(it)
                }
                galonAdapter.notifyItemChanged(0)
            }

            override fun onFailure(call: Call<ArrayList<Galon>>, t: Throwable) {
                Log.d("GALON", t.message.toString())
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = BeliGalonFragment()
    }
}
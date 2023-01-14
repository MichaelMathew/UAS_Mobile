package com.michael.urgalon.fragment

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.urgalon.adapter.HomeDepotAdapter
import com.michael.urgalon.databinding.FragmentHomeBinding
import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Layanan
import com.michael.urgalon.viewmodel.MainViewModel
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.michael.urgalon.HomeActivity
import com.michael.urgalon.PemesananActivity
import com.michael.urgalon.api.ApiClient
import com.michael.urgalon.api.ApiService
import com.michael.urgalon.entity.Galon
import com.michael.urgalon.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var selectedDepot: Depot
    private lateinit var depots: ArrayList<Depot>
    private lateinit var depotAdapter: HomeDepotAdapter
    private lateinit var api: ApiService
    private val viewModel: MainViewModel by activityViewModels()
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ApiClient.getApiClient()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)

        // Spinner Layanan
        val layanan = ArrayList<Layanan>()
        layanan.add(Layanan(getString(com.michael.urgalon.R.string.spinbeligalon)))
        layanan.add(Layanan(getString(com.michael.urgalon.R.string.spinisiulang)))
        val layananAdapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_dropdown_item,
                layanan
            )
        }
        homeBinding.spinlayanan.adapter = layananAdapter
        homeBinding.spinlayanan.setSelection(0,false)
        homeVM.typeBeli = 0
        homeBinding.spinlayanan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                homeVM.typeBeli = p2
                if (p2 == 0) {
                    val fragmentTransaction = childFragmentManager.beginTransaction()
                    fragmentTransaction.replace(homeBinding.containerHome.id, BeliGalonFragment.newInstance())
                    fragmentTransaction.commit()
                } else if (p2 == 1) {
                    val fragmentTransaction = childFragmentManager.beginTransaction()
                    fragmentTransaction.replace(homeBinding.containerHome.id, IsiUlangFragment.newInstance())
                    fragmentTransaction.commit()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                homeBinding.spinlayanan.setSelection(0,false)
            }
        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(homeBinding.containerHome.id, BeliGalonFragment.newInstance())
        fragmentTransaction.commit()

        // Recyclerview Depot
        depots = ArrayList()
        viewModel.depots.observe(viewLifecycleOwner) {
            depots.clear()
            depots.addAll(it)
            depotAdapter.notifyItemChanged(0)
        }
        depotAdapter = HomeDepotAdapter(depots)
        depotAdapter.setListener(object : HomeDepotAdapter.HomeDepotListener {
            override fun onClick(depot: Depot) {
                setSelectedDepot(depot)
                homeVM.selectedDepot = depot
                homeVM.checkFab()
                homeBinding.selectedDepot.visibility = View.VISIBLE
                homeBinding.rvDepotHome.visibility = View.GONE
                homeBinding.tvChangeDepot.visibility = View.VISIBLE
            }
        })
        homeBinding.rvDepotHome.adapter = depotAdapter
        homeBinding.rvDepotHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeBinding.tvChangeDepot.setOnClickListener {
            homeVM.selectedDepot = null
            homeVM.checkFab()
            homeBinding.selectedDepot.visibility = View.GONE
            homeBinding.rvDepotHome.visibility = View.VISIBLE
            homeBinding.tvChangeDepot.visibility = View.GONE
        }

        // FAB
        homeBinding.fabSubmit.visibility = View.GONE
        homeVM.isFabShow.observe(viewLifecycleOwner) {
            if (it) {
                homeBinding.fabSubmit.visibility = View.VISIBLE
            } else {
                homeBinding.fabSubmit.visibility = View.GONE
            }
        }
        homeBinding.fabSubmit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("DEPOT", selectedDepot)
            bundle.putInt("TYPE", homeVM.typeBeli!!)
            bundle.putParcelable("VOUCHER", homeVM.selectedVoucher)
            bundle.putInt("POINT", homeVM.loggedUser.value?.point ?: 0)
            if (homeVM.typeBeli == 0) {
                val selectedGalonArray = ArrayList<Galon>(homeVM.selectedGalon.keys)
                val selectedGalonArrayAmount = ArrayList<Int>(homeVM.selectedGalon.values)
                bundle.putParcelableArrayList("GALON", selectedGalonArray)
                bundle.putIntegerArrayList("GALON-AMOUNT", selectedGalonArrayAmount)
            } else if (homeVM.typeBeli == 1) {
                val selectedGalonArray = ArrayList<Galon>()
                selectedGalonArray.add(Galon(-1, "Isi Ulang", 8000, ""))
                val selectedGalonArrayAmount = ArrayList<Int>()
                selectedGalonArrayAmount.add(homeVM.amountIsiUlang)
                bundle.putParcelableArrayList("GALON", selectedGalonArray)
                bundle.putIntegerArrayList("GALON-AMOUNT", selectedGalonArrayAmount)
            }
            startActivity(
                Intent(
                    activity,
                    PemesananActivity::class.java
                ).putExtras(bundle)
            )
        }

        homeVM.loggedUser.observe(viewLifecycleOwner) {
            homeBinding.tvUserHome.text = "Selamat Datang, ${it.name}"
            homeBinding.tvPointHome.text = "Point Anda : ${it.point} Point"
        }
        return homeBinding.root
    }

    override fun onStart() {
        super.onStart()
//        fetchDepots()
        homeBinding.spinlayanan.setSelection(0,false)
        homeVM.selectedDepot = null
        homeBinding.selectedDepot.visibility = View.GONE
        homeBinding.rvDepotHome.visibility = View.VISIBLE
        homeVM.selectedGalon = mutableMapOf()
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(homeBinding.containerHome.id, BeliGalonFragment.newInstance())
        fragmentTransaction.commit()
        homeVM.checkFab()
    }

    private fun fetchDepots() {
        api.getDepots().enqueue(object : Callback<ArrayList<Depot>> {
            override fun onResponse(
                call: Call<ArrayList<Depot>>,
                response: Response<ArrayList<Depot>>
            ) {
                depots.clear()
                response.body()?.let {
                    depots.addAll(it)
                }
                depotAdapter.notifyItemChanged(0)
            }

            override fun onFailure(call: Call<ArrayList<Depot>>, t: Throwable) {
                Log.d("DEPOT", t.message.toString())
            }

        })
    }

    private fun setSelectedDepot(depot: Depot) {
        selectedDepot = depot
        val storage = Firebase.storage("gs://tubesmobile-13f1f.appspot.com")
        val storageRef = storage.reference
        val imagesRef = storageRef.child("Depot/${depot.image_depot}")
        Glide.with(requireActivity()).load(imagesRef).into(homeBinding.ivDepotItem)
        homeBinding.tvDepotName.text = depot.nama_depot
        homeBinding.tvDepotAddress.text = depot.alamat_depot
        homeBinding.tvDepotJarak.text = depot.jarak_depot.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
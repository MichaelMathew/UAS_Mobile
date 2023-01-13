package com.michael.urgalon.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.DetailHistoryActivity
import com.michael.urgalon.adapter.HistoryAdapter
import com.michael.urgalon.databinding.FragmentHistoryBinding
import com.michael.urgalon.entity.HistoryPemesanan
import com.michael.urgalon.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryFragment : Fragment() {
    private lateinit var historybinding: FragmentHistoryBinding
    private lateinit var historys: ArrayList<HistoryPemesanan>
    private lateinit var historyAdapter: HistoryAdapter
    private val homeVM: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        historybinding = FragmentHistoryBinding.inflate(inflater,container,false)
        historys = ArrayList()
        historyAdapter = HistoryAdapter(historys)
        historyAdapter.setListener(object : HistoryAdapter.HistoryListener{
            override fun onClick(history: HistoryPemesanan) {
                val intent = Intent(activity, DetailHistoryActivity::class.java)
                intent.putExtra("HISTORY", history)
                startActivity(intent)
            }
        })
        historybinding.rvPemesanan.adapter = historyAdapter
        historybinding.rvPemesanan.layoutManager = LinearLayoutManager(context)
        return historybinding.root
    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }

    private fun fetchData() {
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = database.getReference("users").child(Firebase.auth.currentUser!!.uid).child(HISTORY_REF)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                historys.clear()
                for (item: DataSnapshot in snapshot.children) {
                    item.getValue(HistoryPemesanan::class.java)?.let {
                        historys.add(it)
                    }
                }
                historyAdapter.notifyItemChanged(0)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        const val HISTORY_REF = "historys"
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
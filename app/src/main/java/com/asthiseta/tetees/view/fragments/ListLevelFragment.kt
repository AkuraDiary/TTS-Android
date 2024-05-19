package com.asthiseta.tetees.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asthiseta.tetees.R
import com.asthiseta.tetees.databinding.FragmentListLevelDebugBinding
import com.asthiseta.tetees.utils.ManagerLevel


/**
 *Created by Jumadi Janjaya
 *15, December, 2018
 *Jumbox Studios,
 *Bengkulu, Indonesia.
 */


class ListLevelFragment : Fragment() {

    companion object {
        val TAG = "ListLevelFragment"
    }
    private var binding : FragmentListLevelDebugBinding? = null
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentListLevelDebugBinding.inflate(inflater, container, false)


        adapter = ListAdapter(listener)


        val gridLayoutManager = GridLayoutManager(context, 4)
        binding?.recyclerview?.layoutManager = gridLayoutManager
        binding?.recyclerview?.adapter = adapter
        return binding?.root
    }

    private val listener = object : ListAdapter.LevelListener {
        override fun onClick(position: Int) {
            val fragment = MainGameFragment()
            val bundle = Bundle()
            Log.d("ListLevelFragment", "position: $position")
            bundle.putInt("level", position)
            fragment.arguments = bundle
            val transaction  = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            transaction.replace(R.id.fragment_container, fragment ).addToBackStack(TAG).commit()
        }

    }

    class ListAdapter(var levelListener: LevelListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_pilih_soal, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
//            Log.d("ListLevelFragment", "levelCount: ${ManagerLevel.managerLevel.levelCount()}")
            return ManagerLevel.managerLevel.levelCount()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvLevel.text = position.plus(1).toString()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            override fun onClick(p0: View?) {
                levelListener.onClick(position)
            }
            var tvLevel: TextView = itemView.findViewById(R.id.tv_level)

            init {
                itemView.setOnClickListener(this)

            }
        }

        interface LevelListener {
            fun onClick(position: Int)
        }
    }

}
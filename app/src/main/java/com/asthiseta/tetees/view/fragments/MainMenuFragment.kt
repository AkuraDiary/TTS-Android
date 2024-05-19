package com.asthiseta.tetees.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asthiseta.tetees.R
import com.asthiseta.tetees.databinding.FragmentMainMenuBinding
import com.asthiseta.tetees.utils.MediaManager

class MainMenuFragment : Fragment(){
    companion object {
        var TAG = "MainMenuFragment"
    }

    private var binding : FragmentMainMenuBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnMulai.setOnClickListener {
                // Do something
                MediaManager.sound!!.playClick()
//                if (BuildConfig.DEBUG) {
                    val fragment = ListLevelFragment()
                    val transaction  = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    transaction.replace(R.id.fragment_container, fragment).addToBackStack(TAG).commit()
//                } else {
//                    val fragment = MainGameFragment()
//                    val transaction  = activity!!.supportFragmentManager.beginTransaction()
//                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
//                    transaction.replace(R.id.fragment_container, fragment, MainGameFragment.TAG).addToBackStack(TAG).commit()
//                }
            }
//            btnMore.setOnClickListener {
//                // Do something
//            }
//            btnMoreApps.setOnClickListener {
//                // Do something
//            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
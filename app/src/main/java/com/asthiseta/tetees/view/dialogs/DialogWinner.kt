package com.asthiseta.tetees.view.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.asthiseta.tetees.R
import com.asthiseta.tetees.data.DataGame
import com.asthiseta.tetees.databinding.FragmentDialogWinnerBinding
import com.asthiseta.tetees.utils.MediaManager

class DialogWinner : DialogFragment() {
    companion object {
        const val POINT: String = "point"
    }

    private var binding : FragmentDialogWinnerBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogWinnerBinding.inflate(inflater, container, false)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(STYLE_NO_FRAME, android.R.style.Theme)


        setupBinding()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private var animShow: Animation? = null
    private var animExit: Animation? = null
    private fun setupBinding() {
        binding?.apply {
            btnShare.setOnClickListener {
//                MediaManager.sound!!.playClick()
//                val kirim = getString(R.string.txt_share_app, requireActivity().packageName)
//                val intent = Intent(Intent.ACTION_SEND)
//                intent.type = "text/plain"
//                intent.putExtra(Intent.EXTRA_TEXT, kirim)
//                startActivity(Intent.createChooser(intent, getString(R.string.title_share)))

                dismiss()
            }

            btnNext.setOnClickListener {
                MediaManager.sound!!.playClick()

//                val fragment = MainGameFragment()
//                val bundle = Bundle()
//                bundle.putInt(Utils.LEVEL, DataGame.get().data.level!!)
//                fragment.arguments = bundle
//                val transaction  : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
//                transaction.replace(R.id.fragment_container, fragment, MainGameFragment.TAG).addToBackStack(MainMenuFragment.TAG).commit()

                dismiss()
            }

            animShow = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            animExit = AnimationUtils.loadAnimation(context, R.anim.fade_out)

            animShow?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    countDownTimer.start()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            animExit?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    dismiss()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            tvPoint.text = arguments?.getInt(POINT).toString()

            countDownTimer.start()
            MediaManager.sound!!.playBerhasil()
            MediaManager.sound!!.playJawabanBenar()

        }
    }

    private val countDownTimer = object : CountDownTimer(4000, 10) {
        override fun onTick(l: Long) {

        }

        override fun onFinish() {
//            relativeLayout.visibility = View.VISIBLE
//            relativeLayout.startAnimation(animShow)
//            if (BuildConfig.isAdsAdmob) AdsGame.adsGame!!.showInterstitial()
//            else AdGame.adGame!!.showInterstitial()
        }
    }

}
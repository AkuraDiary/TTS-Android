package com.asthiseta.tetees

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.asthiseta.tetees.data.DataGame
import com.asthiseta.tetees.databinding.ActivityMainBinding
import com.asthiseta.tetees.utils.ManagerLevel
import com.asthiseta.tetees.utils.MediaManager
import com.asthiseta.tetees.view.fragments.MainGameFragment
import com.asthiseta.tetees.view.fragments.MainMenuFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    //    private val handler by lazy { Handler() }
    private var next = false
    private var pos = -1
    private var nextPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupView()
        setContentView(binding?.root)

    }

    private fun setupView() {
        DataGame.instance(this).create()
        ManagerLevel.instance()
        MediaManager.instance(this)

        val fragment = MainMenuFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, MainMenuFragment.TAG)
            .commitAllowingStateLoss()

//        if(BuildConfig.isAdsAdmob) {
//            AdsGame.adsGame!!.createBanner(iklan, object : com.google.android.gms.ads.AdListener() {
//                override fun onAdLoaded() {
//                    tv_pepatah.visibility = TextView.GONE
//                    img_logo.visibility = ImageView.GONE
//                    pos = -1
//                }
//
//                override fun onAdFailedToLoad(p0: Int) {
//                    super.onAdFailedToLoad(p0)
//                    AdsGame.adsGame!!.bannerLoad()
//                    iklan.setBackgroundResource(R.drawable.banner)
//                    tv_pepatah.visibility = TextView.VISIBLE
//                    img_logo.visibility = ImageView.VISIBLE
//                    if (pos == -1) iklan()
//                }
//            })
//            AdsGame.adsGame!!.reqInterstitial()
//            AdsGame.adsGame!!.reqVideoReward()
//        } else {
//            AdGame.adGame!!.createBanner(iklan, object : AdListener {
//                override fun onAdClicked(p0: Ad?) {
//
//                }
//
//                override fun onLoggingImpression(p0: Ad?) {
//
//                }
//
//                override fun onAdLoaded(ad: Ad) {
//                    tv_pepatah.visibility = TextView.GONE
//                    img_logo.visibility = ImageView.GONE
//                    pos = -1
//                }
//                override fun onError(ad: Ad, adError: AdError) {
//                    AdGame.adGame!!.bannerLoad()
//                    iklan.setBackgroundResource(R.drawable.banner)
//                    tv_pepatah.visibility = TextView.VISIBLE
//                    img_logo.visibility = ImageView.VISIBLE
//                    if (pos == -1) iklan()
//                }
//            })
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onBackPressed() {
        when {
//            supportFragmentManager.findFragmentByTag(MainGameFragment.TAG) != null && BuildConfig.DEBUG -> supportFragmentManager.popBackStack(
//                ListLevelDebugFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE
//            )

            supportFragmentManager.findFragmentByTag(MainGameFragment.TAG) != null -> supportFragmentManager.popBackStack(
                MainMenuFragment.TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            else -> super.onBackPressed()
        }
    }
}

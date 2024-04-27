package com.asthiseta.tetees.view.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.asthiseta.tetees.R
import com.asthiseta.tetees.data.DataGame
import com.asthiseta.tetees.data.model.TTS
import com.asthiseta.tetees.data.model.TtsOrientation
import com.asthiseta.tetees.databinding.FragmentMainGameBinding
import com.asthiseta.tetees.utils.ManagerLevel
import com.asthiseta.tetees.utils.MediaManager
import com.asthiseta.tetees.view.adapter.TtsAdapter
import com.asthiseta.tetees.view.dialogs.DialogWinner
import java.util.Locale

/**
 *Created by Jumadi Janjaya
 *13, December, 2018
 *Jumbox Studios,
 *Bengkulu, Indonesia.
 */
class MainGameFragmet : Fragment(), TtsAdapter.TtsListener {
//    override fun onInterstitialShow() {
//        adapter.bantuan(true)
//        DataGame.get().setBantuan(true)
//        adapter.updateAllDataTts()
//    }

//    override fun diClick(id: Int) {
//        when(id) {
//            JDialog.DIALOG_TONTON -> {
//                if (BuildConfig.isAdsAdmob && AdsGame.adsGame!!.isVideoRewardLoad()) {
//                    AdsGame.adsGame!!.showVideoReward()
//                } else if(!BuildConfig.isAdsAdmob && AdGame.adGame!!.isVideoRewardLoad()) {
//                    AdGame.adGame!!.showVideoReward()
//                } else {
//                    val dialogGagalLoad =  JDialog()
//                    val bundle = Bundle()
//                    bundle.putInt(JDialog.DIALOG, JDialog.DIALOG_GAGAL_LOAD)
//                    dialogGagalLoad.arguments = bundle
//                    dialogGagalLoad.setListener(this)
//                    val transaction = activity!!.supportFragmentManager.beginTransaction()
//                    transaction.add(dialogGagalLoad, null).show(dialogGagalLoad).commit()
//                    dialogGagalLoad.isCancelable = false
//                }
//            }
//            JDialog.DIALOG_GAGAL_LOAD -> {}
//
//            JDialog.DIALOG_GUNA_BANTUAN -> {
//                val dialogTonton =  JDialog()
//                val bundle = Bundle()
//                bundle.putInt(JDialog.DIALOG, JDialog.DIALOG_TONTON)
//                dialogTonton.arguments = bundle
//                dialogTonton.setListener(this)
//                val transaction = activity!!.supportFragmentManager.beginTransaction()
//                transaction.add(dialogTonton, null).show(dialogTonton).commit()
//                dialogTonton.isCancelable = false
//            }
//            JDialog.DIALOG_GUNA_INTERSTITIAL -> {
//                if(BuildConfig.isAdsAdmob) AdsGame.adsGame!!.showInterstitial(1)
//                else AdGame.adGame!!.showInterstitial(1)
//            }
//        }
//    }

    override fun click(posisi: Int, txtpKode: String, noUbah: Boolean) {
        val tts = adapter.get(posisi)
        this.txtJawaban = txtpKode

        var txtkode = ""
        var petunjuk = ""
        if (tts.kode!!.contains(":")) {

            val ko = tts.kode!!.split(":")
            val pt = tts.clue!!.split(";")

            if (txtpKode == ko[0]) {
                txtkode = ko[0]
                petunjuk = pt[0]
            } else if (txtpKode == ko[1]) {
                txtkode = ko[1]
                petunjuk = pt[1]
            }

        } else {
            txtkode = tts.kode ?: ""
            petunjuk = tts.clue ?: ""
        }

        setTextBtn(txtkode, tts.id, noUbah)

        if (petunjuk != "") {
            val u = petunjuk[0].toString()
            val bal = u.uppercase(Locale.getDefault())
            binding?.tvPetunjuk?.text = petunjuk.replaceFirst(u, bal)
        }
    }

//    override fun onRewardedVideoCompleted() {
//        adapter.bantuan(true)
//        DataGame.get().setBantuan(true)
//        adapter.updateAllDataTts()
//    }

    companion object {
        var TAG = "MainGameFragment"
    }

    private val btnKeypad: ArrayList<Button?> = ArrayList()
//    private lateinit var btnHapus: Button
//    private lateinit var btnCek: Button
//    private lateinit var btnBantuan: Button
//
//    private lateinit var tvPoint: TextView
//    private lateinit var tvLevel: TextView
//    private lateinit var tvScore: TextView
//    private lateinit var tvPetunjuk: TextView
//
//    private lateinit var bar: RelativeLayout
//    private lateinit var keypad: RelativeLayout
//    private lateinit var relPetunjuk: RelativeLayout

    private val adapter: TtsAdapter = TtsAdapter()
//    private lateinit var recyclerView: RecyclerView

    private lateinit var manager: ManagerLevel

    private var txtJawaban: String = ""
    private var jumKotakTts: Int = 0
    private var jumKotakBenarTts = 0
    var txtPetunjukOld: String = ""
    var jumCek: Int = 15
    var point: Int = 100

    private var binding : FragmentMainGameBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainGameBinding.inflate(inflater, container, false)
//        (activity as MainActivity).cekFragmentTag(true)

        btnKeypad.add(binding?.linKeypad?.btn1)
        btnKeypad.add(binding?.linKeypad?.btn2)
        btnKeypad.add(binding?.linKeypad?.btn3)
        btnKeypad.add(binding?.linKeypad?.btn4)
        btnKeypad.add(binding?.linKeypad?.btn5)
        btnKeypad.add(binding?.linKeypad?.btn6)
        btnKeypad.add(binding?.linKeypad?.btn7)
        btnKeypad.add(binding?.linKeypad?.btn8)
        btnKeypad.add(binding?.linKeypad?.btn9)
        btnKeypad.add(binding?.linKeypad?.btn10)



//        manager = ManagerLevel.managerLevel
        adapter.setListener(this)

//        if (BuildConfig.isAdsAdmob) AdsGame.adsGame!!.setListener(this)
//        else AdGame.adGame!!.setListener(this)

        binding?.recyclerview?.adapter = adapter
        createTts()

        for (i in 0 until btnKeypad.size) {
            val btn = btnKeypad[i]
            btn?.text = getText(R.string.tekan)[i].toString()

            btn?.setOnClickListener { view ->
                MediaManager.sound!!.playClick()
                var id = -1
                if(view.tag != null) id = view.tag as Int
                updateKotak(id, btn.text as String)
            }
        }

//        binding?.btnHapus.setOnClickListener { view ->
//            MediaManager.sound!!.playClick()
//            var id = -1
//            if(view.tag != null) id = view.tag as Int
//            updateKotak(id, "")
//        }

        binding?.linKeypad?.btnBantuan?.setOnClickListener {
//            MediaManager.sound!!.playClick()
//            val dialogBantuan =  JDialog()
//            val bundle = Bundle()
//            bundle.putInt(JDialog.DIALOG, JDialog.DIALOG_GUNA_BANTUAN)
//            dialogBantuan.arguments = bundle
//            dialogBantuan.setListener(this)
//            val transaction = activity!!.supportFragmentManager.beginTransaction()
//            transaction.add(dialogBantuan, null).show(dialogBantuan).commit()
//            dialogBantuan.isCancelable = false
        }

        binding?.linKeypad?.btnCek?.setOnClickListener {
            MediaManager.sound!!.playClick()
            if(jumCek > 0 && !(DataGame.get().data.bantuan!!)) {
                timerDown.start()
                adapter.bantuan(true)
                adapter.updateAllDataTts()
                jumCek -= 1
                point -=2
                binding?.tvPoint?.text = getString(R.string.point, point.toString())
                DataGame.get().setJumCek(jumCek)
            }else if(DataGame.get().data.bantuan!!)  {
                val txt = "<font color='red'><b>${getString(R.string.bantuan_sudah_digunakan)}</b></font>"
                txtPetunjukOld = binding?.tvPetunjuk?.text.toString()
                binding?.tvPetunjuk?.setText(Html.fromHtml(txt), TextView.BufferType.SPANNABLE)
                timerDown.start()
            } else if (jumCek == 0){

//                val dialogCekHabis =  JDialog()
//                val bundle = Bundle()
//                bundle.putInt(JDialog.DIALOG, JDialog.DIALOG_CEK_HABIS)
//                dialogCekHabis.arguments = bundle
//                val transaction = activity!!.supportFragmentManager.beginTransaction()
//                transaction.add(dialogCekHabis, null).show(dialogCekHabis).commit()
//                dialogCekHabis.isCancelable = false
            }else{

            }
        }

        return  binding?.root
    }

    private fun createTts() {
//        val level: Int = if (BuildConfig.DEBUG) {
            requireArguments().getInt("level")
//        } else {
//            DataGame.get().data.level!!
//        }
//
//        if (level > manager.levelCount()) {
//            val dialogGameOver =  JDialog()
//            val bundle = Bundle()
//            bundle.putInt(JDialog.DIALOG, JDialog.DIALOG_GAME_OVER)
//            dialogGameOver.arguments = bundle
//            dialogGameOver.setListener(this)
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.add(dialogGameOver, null).show(dialogGameOver).commit()
//            dialogGameOver.isCancelable = false
//            return
//        }

//        manager.setLevel(level)

        val sLevel =(manager.level()+1).toString()
        point = DataGame.get().getPoint()
        binding?.tvLevel?.text = getString(R.string.level, sLevel)
        binding?.tvPoint?.text = getString(R.string.point, "$point ")
//        binding?.tvScore?.text = getString(R.string.score, DataGame.get().data.jumlahPoint.toString())

        val tts = manager.Tts()
        val gridLayoutManager = GridLayoutManager(context, manager.kotak())
        binding?.recyclerview?.layoutManager = gridLayoutManager
        binding?.recyclerview?.isVerticalScrollBarEnabled = false

        adapter.setK(manager.kotak())
        DataGame.get().loadDataLevel(manager.level())

        if (DataGame.get().data.heightRecy == null) {
            val gridParams = binding?.recyclerview?.layoutParams
            gridParams?.height = ((DataGame.get().data.screenHeight!! - binding?.toolBar?.layoutParams?.height!!) - binding?.relPetunjuk?.layoutParams?.height!!) - binding?.linKeypad?.root?.layoutParams?.height!!
            binding?.recyclerview?.layoutParams = gridParams
            DataGame.get().setHeightRecy(gridParams?.height!!).save()
        }

        val heigRecy = (DataGame.get().data.heightRecy!! - (binding?.recyclerview?.paddingTop!! + binding?.recyclerview?.paddingBottom!!))
        val height = heigRecy / manager.kotak().toFloat()
        val sisa = heigRecy - (Math.round(height) * manager.kotak())

        val clueParams = binding?.relPetunjuk?.layoutParams
        clueParams?.height = clueParams?.height?.plus(sisa)
        binding?.relPetunjuk?.layoutParams = clueParams

        var count = 0
        for (i in 0 until manager.kotak()) {
            for (j in 0 until manager.kotak()) {
                val mTts = TTS()
                mTts.id = count
                mTts.tts = tts.hTts(i, j)
                mTts.kode = tts.hKode(i, j)
                mTts.clue = tts.hClue(i, j)
                mTts.orientation = tts.hOrie(i, j)
                mTts.up = DataGame.get().loadKotak(count)
                count++
                adapter.add(mTts)
            }
        }

        adapter.bantuan = DataGame.get().data.bantuan!!
        adapter.notifyDataSetChanged()

    }

    private val timerDown = object : CountDownTimer(1000, 100) {

        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            if (jumCek > -1 && !DataGame.get().data.bantuan!!) {
                adapter.bantuan(false)
            } else {
                binding?.tvPetunjuk?.text = txtPetunjukOld
            }
            adapter.updateAllDataTts()
        }
    }

    private fun updateKotak(id: Int, txt: String) {
        var nextPos = 0
        var posisi = 0
        if (id < 0) {
            return
        }

        for (cr in adapter.ttsList) {
            if (cr.id == id) {
                val index = adapter.ttsList.indexOf(cr)
                posisi = index
                if (txt != "") {
                    if (cr.kode!!.contains(":")) {
                        val ko = cr.kode!!.split(":")
                        if (txtJawaban == ko[0]) {
                            nextPos = index + 1
                        } else if (txtJawaban == ko[1]) {
                            nextPos = index + manager.kotak()
                        }
                    } else {
                        nextPos = if (cr.orientation == TtsOrientation.HORIZONTAL) index + 1 else index + manager.kotak()
                    }
                } else {

                    if (cr.kode!!.contains(":")) {
                        val ko = cr.kode!!.split(":")
                        if (txtJawaban == ko[0]) {
                            nextPos = index - 1
                        } else if (txtJawaban == ko[1]) {
                            nextPos = index - manager.kotak()
                        }
                    } else {
                        nextPos = if (cr.orientation == TtsOrientation.HORIZONTAL) index - 1 else index - manager.kotak()
                    }
                }


                if (nextPos > adapter.itemCount - 1 || nextPos < 0) {
                    nextPos = posisi
                } else if (nextPos < adapter.itemCount - 1 || nextPos > 0) {
                    val tc = adapter.get(nextPos)
                    if (tc.kode == null || adapter.block(posisi, nextPos)) {
                        nextPos = posisi
                    }
                }
                cr.tts = cr.tts
                cr.id = cr.id
                cr.clue = cr.clue
                cr.kode = cr.kode
                cr.up = txt
                adapter.remove(index)
                adapter.add(index, cr)
                DataGame.get().saveKotak(index, txt)
                break
            }
        }
        adapter.nextPos(posisi, nextPos)

        if (cekGame()) {
            val dialogWinner =  DialogWinner()
//            dialogWinner.setListener(this)
            val bundle = Bundle()
            bundle.putString(DialogWinner.POINT, point.toString())
            dialogWinner.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(dialogWinner, null).show(dialogWinner).commit()
            dialogWinner.isCancelable = false
        }
    }

    private fun cekGame(): Boolean {
        jumlahBenar()
        var mCek = false
        val jPoint =DataGame.get().data.jumlahPoint!!.plus(point)
        if (jumKotakBenarTts == jumKotakTts) {
            DataGame.get().setLevel(manager.level()+1)
                    .addDataKotak()
                    .clearKotak()
                    .setPoint(point)
                    .setJumCek(15)
                    .setJumPoints(jPoint)
                    .setBantuan(false)
                    .save()
            mCek = true

        }
        return mCek
    }

    private fun jumlahBenar() {
        var jumlahBenar = 0
        var jumlahTts = 0
        for (i in 0 until adapter.ttsList.size) {
            val tc = adapter.get(i)
            if (tc.tts.toString().toUpperCase() == DataGame.get().loadKotak(i).toUpperCase()) {
                if (DataGame.get().loadKotak(i) != "") {
                    jumlahBenar++
                }
            }

            if (tc.tts != " ") {
                jumlahTts++
            }
        }
        this.jumKotakTts = jumlahTts
        this.jumKotakBenarTts = jumlahBenar
    }

    private fun setTextBtn(str: String, id: Int, noUbah: Boolean) {
        val random = java.util.Random()
        val huruf = "abcdefghijklmnopqrstuvwxyz"
        val b = 10
        val arr = arrayOfNulls<String>(b)
        for (i in 0 until str.length) {
            arr[i] = str.substring(i, i + 1)
        }
        for (i in str.length until b) {
            arr[i] = "" + huruf[random.nextInt(huruf.length)]
        }

        for (i in 0 until b) {
            val str2 = arr[i]
            val nextInt = random.nextInt(b)
            arr[i] = arr[nextInt]
            arr[nextInt] = str2
        }

//        binding?.btnHapus.tag = id
        for (i in 0 until btnKeypad.size) {
            val btn = btnKeypad[i]
            if (!noUbah) {
                btn?.text = arr[i]
            }
            btn?.tag = id
        }

    }
}
package com.fphoenixcorneae.palette.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.palette.sample.databinding.ActivityMainBinding

/**
 * @desc：
 * @date：2021/11/03 14:01
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mViewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding!!.root)

        mViewBinding?.apply {
            onClick = this@MainActivity
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

    override fun onClick(v: View?) {
        mViewBinding?.apply {
            when (v) {
                btnGlide -> startActivity(Intent(this@MainActivity, GlideActivity::class.java))
                btnCoil -> startActivity(Intent(this@MainActivity, CoilActivity::class.java))
            }
        }
    }
}
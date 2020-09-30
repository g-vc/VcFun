package com.veganchen.vcfun.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.veganchen.vcfun.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 * created by vc on 2020/4/16
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init(){

    }
}
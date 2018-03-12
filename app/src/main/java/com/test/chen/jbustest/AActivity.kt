package com.test.chen.jbustest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.test.jasonchen.assemblydemo.view.JBus
import kotlinx.android.synthetic.main.activity_main.*

class AActivity : AppCompatActivity(), JBus.OnEventMessageListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        JBus.registerEvent(this::class.java, this)
        btn1.setOnClickListener {
            JBus.sendMsg("All Hello")

        }
        btn2.setOnClickListener {
            startActivity(Intent(this, BActivity::class.java))
        }

    }

    override fun <T> onMsg(s: T) {
        when (s) {
            is String -> Toast.makeText(this, "I'm ${this::class.java.simpleName} msg = $s", Toast.LENGTH_SHORT).show()
        }
    }


}

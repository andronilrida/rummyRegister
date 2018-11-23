package andronil.com.rummyregister.view.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import andronil.com.rummyregister.R
import kotlinx.android.synthetic.main.activity_player.*
import java.text.ParseException

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        supportActionBar!!.title = "Player Details"

        btnAdd.setOnClickListener {
            calculateScore(it.id)
        }
        btnSub.setOnClickListener {
            calculateScore(it.id)
        }

    }

    private fun calculateScore(id:Int) {
        val position = intent.getIntExtra(getString(R.string.position),-1)
        val name = intent.getStringExtra(getString(R.string.player))
        val oldScore = intent.getIntExtra(getString(R.string.score),0)

        val score = try {
            etScore.text.toString().toInt()
        } catch (e: ParseException) {
            0
        }

        val sendData = Intent()
        sendData.putExtra(getString(R.string.player),name)
        sendData.putExtra(getString(R.string.score),when(id){
            R.id.btnAdd -> oldScore + score
            R.id.btnSub -> oldScore - score
            else -> 0
        })
        sendData.putExtra(getString(R.string.position),position)
        setResult(Activity.RESULT_OK,sendData)
        finish()
    }
}

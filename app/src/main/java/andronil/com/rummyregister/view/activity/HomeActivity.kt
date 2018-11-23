package andronil.com.rummyregister.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import andronil.com.rummyregister.R
import andronil.com.rummyregister.other.db.AppDataBase
import andronil.com.rummyregister.other.db.entity.GameDetail
import andronil.com.rummyregister.other.util.CommonUtils
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inIt()
        attachListeners()
    }

    private fun attachListeners() {
        btnStart.setOnClickListener {
            val gamePoint = try {
                etGamePoint.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            val betAmount = try {
                etBetAmount.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            if (gamePoint == 0){
                Toast.makeText(this,"Game can not start with out a game point.",Toast.LENGTH_SHORT).show()
                etGamePoint.error = "set a game point"
            }
            else{
                val startGameTask = Runnable {
                    val isFromActivity = try {
                        intent.getStringExtra(getString(R.string.from)).isEmpty()
                    } catch (e: Exception) {
                        false
                    }
                    db?.getGameDetailDao()?.addGameDetail(GameDetail(betAmount,gamePoint))
                    if (isFromActivity){
                        finish()
                    }
                    else
                        startActivity(Intent(this,MainActivity::class.java))
                }
                if (betAmount == 0){
                    CommonUtils.createDialog(this,"Alert!","Are you sure to start game with out any bet?",
                            false,"Yes",startGameTask)
                }else{
                    startGameTask.run()
                }
            }
        }
    }

    private var db: AppDataBase? = null

    private fun inIt() {
        db = AppDataBase.getInstance(this)
    }
}

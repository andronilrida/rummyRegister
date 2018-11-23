package andronil.com.rummyregister.view.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import andronil.com.rummyregister.R
import andronil.com.rummyregister.other.db.AppDataBase
import andronil.com.rummyregister.model.Player
import andronil.com.rummyregister.view.dialog.PlayerEntryDialogFragment
import andronil.com.rummyregister.view.adapter.PlayerAdapter
import andronil.com.rummyregister.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), PlayerAdapter.CallBack, PlayerEntryDialogFragment.CallBack {

    override fun onPlayerItemCount(playerCount: Int) {
        if (playerCount == 0){
            rvPlayer.visibility = View.GONE
            tvNoPlayer.visibility = View.VISIBLE
        }else{
            rvPlayer.visibility = View.VISIBLE
            tvNoPlayer.visibility = View.GONE
        }
    }

    override fun onDoneClick(playerName: String, betAmount: Int) {
        mainActivityVm.addPlayer(playerName,betAmount)
    }

    override fun onPlayerClick(player: Player,position:Int) {
        val data = Intent(this, PlayerActivity::class.java)
        data.putExtra(getString(R.string.player),player.name)
        data.putExtra(getString(R.string.score),player.score)
        data.putExtra(getString(R.string.position),position)
        startActivityForResult(data, SCORE_CALCULATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            SCORE_CALCULATION ->{
                if (resultCode == Activity.RESULT_OK){
                    val score = data?.getIntExtra(getString(R.string.score),0)
                    val playerName = data?.getStringExtra(getString(R.string.player))
                    val position = data?.getIntExtra(getString(R.string.position),-1)
                    mainActivityVm.addScore(playerName!!,score!!,position!!)
                }else if (resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(this,"no new score added.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private lateinit var mainActivityVm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        inIt()
        attachListener()
    }

    private fun attachListener() {
        fabAddPlayer.setOnClickListener {
            PlayerEntryDialogFragment().show(supportFragmentManager, PlayerEntryDialogFragment::class.java.simpleName)
        }
        fabSortPlayer.setOnClickListener {
            mainActivityVm.sortPlayer(it)
        }
    }

    private fun inIt() {
        mainActivityVm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityVm.db = AppDataBase.getInstance(this)
        mainActivityVm.playerAdapter = PlayerAdapter(mainActivityVm.db?.getPlayerDao()?.getPlayerList()
                ?: ArrayList(), this)
        rvPlayer.layoutManager = LinearLayoutManager(this)
        rvPlayer.adapter = mainActivityVm.playerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val SCORE_CALCULATION: Int = 11
    }
}

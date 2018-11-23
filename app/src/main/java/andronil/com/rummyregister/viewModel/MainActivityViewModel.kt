package andronil.com.rummyregister.viewModel

import android.arch.lifecycle.ViewModel
import android.view.View
import andronil.com.rummyregister.view.adapter.PlayerAdapter
import andronil.com.rummyregister.other.db.AppDataBase
import andronil.com.rummyregister.model.Player

class MainActivityViewModel :ViewModel() {

    fun addPlayer(playerName: String, betAmount: Int) {
        val player = Player(playerName,0,false,betAmount)
        db?.getPlayerDao()?.addPlayer(player)
        playerAdapter.refreshAdapter(db?.getPlayerDao()?.getPlayerList() ?:  ArrayList())
    }

    fun addScore(playerName: String, score: Int, position: Int) {
        db?.getPlayerDao()?.addScore(playerName,score)
        val player = playerAdapter.getPlayerList()[position]
        if (player.name == playerName){
            player.score = score
        }
        playerAdapter.notifyDataSetChanged()
    }

    private fun organizePlayer(order: Int) {
        val playerList =
        if (order == ASCENDING_ORDER)
            db?.getPlayerDao()?.getPlyaerListInAscending() ?: playerAdapter.getPlayerList()
        else
            db?.getPlayerDao()?.getPlyaerListInAscending() ?: playerAdapter.getPlayerList()
        playerAdapter.refreshAdapter(playerList)
    }

    fun sortPlayer(it: View) {
        val tag = try {
            it.tag as Int
        } catch (e: Exception) {
            MainActivityViewModel.ASCENDING_ORDER
        }
        if (tag == MainActivityViewModel.ASCENDING_ORDER){
            it.tag = MainActivityViewModel.DESCENDING_ORDER
        }else{
            it.tag = MainActivityViewModel.ASCENDING_ORDER
        }
        organizePlayer(tag)
    }

    lateinit var playerAdapter: PlayerAdapter
    var db: AppDataBase? = null

    companion object {
        const val ASCENDING_ORDER = 1
        const val DESCENDING_ORDER = 2
    }
}
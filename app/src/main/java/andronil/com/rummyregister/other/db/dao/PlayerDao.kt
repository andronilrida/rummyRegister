package andronil.com.rummyregister.other.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import andronil.com.rummyregister.model.Player

@Dao
interface PlayerDao {

    @Insert
    fun addPlayer(player:Player)

    @Delete
    fun removePlayer(player: Player)

    @Query("select * from Player")
    fun getPlayerList():ArrayList<Player>

    @Query("select * from Player order by score asc")
    fun getPlyaerListInAscending():ArrayList<Player>


    @Query("select * from Player order by score desc")
    fun getPlyaerListInDescending():ArrayList<Player>

    @Query("update Player set score = score where name = '' ")
    fun addScore(playerName: String, score: Int)
}
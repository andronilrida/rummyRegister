package andronil.com.rummyregister.other.db.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import andronil.com.rummyregister.other.db.entity.GameDetail

interface GameDetailDao {

    @Query("select * from GameDetail")
    fun getGameDetail():GameDetail

    @Insert
    fun addGameDetail(gameDetail: GameDetail)
}
package andronil.com.rummyregister.other.db.entity

import android.arch.persistence.room.Entity

@Entity
data class GameDetail(var basicBetAmount:Int,
                      var gamePoint:Int,
                      var noOfPlayers:Int = 0) {
}
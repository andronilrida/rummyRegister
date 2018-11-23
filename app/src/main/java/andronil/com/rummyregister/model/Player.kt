package andronil.com.rummyregister.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Player(@PrimaryKey var name: String,
                  var score: Int,
                  var isLeading: Boolean,
                  var betAmount: Int)
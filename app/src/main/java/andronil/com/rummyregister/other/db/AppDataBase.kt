package andronil.com.rummyregister.other.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import andronil.com.rummyregister.other.db.dao.PlayerDao
import andronil.com.rummyregister.model.Player
import andronil.com.rummyregister.other.db.dao.GameDetailDao
import andronil.com.rummyregister.other.db.entity.GameDetail

@Database(entities = [Player::class,GameDetail::class],version = 1)
abstract class AppDataBase:RoomDatabase() {

    abstract fun getPlayerDao(): PlayerDao
    abstract fun getGameDetailDao():GameDetailDao

    companion object {
        private var db: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase?{
            if (db == null){
                synchronized(AppDataBase::class.java){
                    db = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java,
                            "Rummy db").build()
                }
            }
            return db
        }
    }
}
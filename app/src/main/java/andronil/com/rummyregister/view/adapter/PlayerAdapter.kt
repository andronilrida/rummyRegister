package andronil.com.rummyregister.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import andronil.com.rummyregister.R
import andronil.com.rummyregister.databinding.RowPlayerBinding
import andronil.com.rummyregister.model.Player

class PlayerAdapter(private var list:ArrayList<Player>,private val callback: CallBack) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = DataBindingUtil.inflate<RowPlayerBinding>(LayoutInflater.from(parent.context), R.layout.row_player,parent,false)
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int {
//        callback.onPlayerItemCount(list.size)
        return list.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun getPlayerList():ArrayList<Player>{
        return list
    }

    fun refreshAdapter(list: ArrayList<Player>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class PlayerViewHolder(private val binding: RowPlayerBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(player:Player){
            binding.root.setOnClickListener {
                callback.onPlayerClick(player,adapterPosition)
            }
            binding.player = player
            binding.executePendingBindings()
        }
    }

    interface CallBack{
        fun onPlayerClick(player: Player,position:Int)
        fun onPlayerItemCount(playerCount: Int)
    }
}
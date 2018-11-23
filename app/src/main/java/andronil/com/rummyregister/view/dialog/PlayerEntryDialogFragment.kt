package andronil.com.rummyregister.view.dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import andronil.com.rummyregister.R
import java.lang.Exception

class PlayerEntryDialogFragment: DialogFragment() {
    private lateinit var callback: CallBack

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as CallBack
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.player_entry_layout,container,false)
        inIt(rootView)
        attachListener(rootView)
        return rootView
    }

    private fun attachListener(rootView: View) {
        rootView.findViewById<Button>(R.id.btnDone).setOnClickListener {
            val playerName = etPlayerName.text.toString()
            if (playerName.isBlank() || playerName.isEmpty())
                etPlayerName.error = "Player name is required"
            else if (playerName.length > playerNameLength)
                etPlayerName.error = "Player name must be of max $playerNameLength character."
            else{
                val betAmount = try {
                    rootView.findViewById<EditText>(R.id.etBetAmount).text.toString().toInt()
                }catch (e:Exception){
                    0
                }
                callback.onDoneClick(playerName,betAmount)
                dialog.dismiss()
            }
        }
        rootView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

    }
    private lateinit var etPlayerName:EditText
    private fun inIt(rootView: View) {
        dialog.setCancelable(false)
        etPlayerName = rootView.findViewById(R.id.etPlayerName)
        etPlayerName.maxEms = playerNameLength
        etPlayerName.hint = "Enter player name"
    }

    companion object {
        private const val playerNameLength = 7
    }
    interface CallBack{
        fun onDoneClick(playerName: String, betAmount: Int)
    }
}
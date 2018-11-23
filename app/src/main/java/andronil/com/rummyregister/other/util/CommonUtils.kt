package andronil.com.rummyregister.other.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button

class CommonUtils {

    companion object {
        fun createDialog(context:Context,title:String = "Alert",message:String = "",cancelable:Boolean = true,
                         positiveButton:String="ok",taskForPositiveButton:Runnable? = null ,
                         negativeButton:String = "",taskForNegativeButton:Runnable? = null,
                         view: View? = null): AlertDialog {
            val builder =AlertDialog.Builder(context)
            builder.setTitle(title).setView(view)
            builder.setPositiveButton(positiveButton) { _: DialogInterface, _: Int ->
                taskForPositiveButton?.run()
            }
            if (negativeButton.isNotEmpty())
                builder.setNegativeButton(negativeButton){ _, _ ->
                    taskForNegativeButton?.run()
                }
            return builder.create()
        }
    }
}
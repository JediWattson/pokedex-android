package com.famtrees.pokemonex

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class ErrorDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var build = AlertDialog.Builder(it)
            build.setMessage("Fire Ze Missyles")
                .setNeutralButton("OK",
                    DialogInterface.OnClickListener{ dialog, id ->
                        dialog.cancel()
                    }
                )
            build.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
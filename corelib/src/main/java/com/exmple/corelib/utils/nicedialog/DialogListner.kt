package com.exmple.corelib.utils.nicedialog

import android.os.Parcel
import android.os.Parcelable
import android.view.View

abstract class DialogListner : Parcelable {

    abstract fun convertView(view: View, dialog: MNiceDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor() {}

    protected constructor(`in`: Parcel) {}

    companion object {
        val CREATOR: Parcelable.Creator<DialogListner> = object : Parcelable.Creator<DialogListner> {
            override fun createFromParcel(source: Parcel): DialogListner {
                return object : DialogListner(source) {
                    override fun convertView(view: View, dialog: MNiceDialog) {

                    }
                }
            }

            override fun newArray(size: Int): Array<DialogListner?> {
                return arrayOfNulls(size)
            }
        }
    }
}

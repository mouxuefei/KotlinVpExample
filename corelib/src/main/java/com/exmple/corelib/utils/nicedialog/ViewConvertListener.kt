package com.exmple.corelib.utils.nicedialog

import android.os.Parcel
import android.os.Parcelable

abstract class ViewConvertListener : Parcelable {

    abstract fun convertView(holder: ViewHolder, dialog: BaseNiceDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor() {}

    protected constructor(`in`: Parcel) {}

    companion object {
        val CREATOR: Parcelable.Creator<ViewConvertListener> = object : Parcelable.Creator<ViewConvertListener> {
            override fun createFromParcel(source: Parcel): ViewConvertListener {
                return object : ViewConvertListener(source) {
                    override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {

                    }
                }
            }

            override fun newArray(size: Int): Array<ViewConvertListener?> {
                return arrayOfNulls(size)
            }
        }
    }
}

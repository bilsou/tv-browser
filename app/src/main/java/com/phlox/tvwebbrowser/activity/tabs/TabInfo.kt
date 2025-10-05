package com.phlox.tvwebbrowser.activity.tabs

import android.os.Parcel
import android.os.Parcelable

data class TabInfo(
    val id: Long,
    val title: String,
    val url: String,
    val faviconHash: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(faviconHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TabInfo> {
        override fun createFromParcel(parcel: Parcel): TabInfo {
            return TabInfo(parcel)
        }

        override fun newArray(size: Int): Array<TabInfo?> {
            return arrayOfNulls(size)
        }
    }
}

package edward.mamani.timer_quizzdos

import android.os.Parcel
import android.os.Parcelable

data class TemaClass(
    var id_tema: Int,
    var tema: String
):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString() ?: "")

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id_tema)
        dest.writeString(tema)
    }

    companion object CREATOR : Parcelable.Creator<TemaClass> {
        override fun createFromParcel(parcel: Parcel): TemaClass {
            return TemaClass(parcel)
        }

        override fun newArray(size: Int): Array<TemaClass?> {
            return arrayOfNulls(size)
        }
    }
}
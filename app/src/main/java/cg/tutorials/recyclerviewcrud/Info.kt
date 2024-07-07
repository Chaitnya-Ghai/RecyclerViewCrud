package cg.tutorials.recyclerviewcrud

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Info(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String?=null,
    var description :String?=null
)

package cg.tutorials.recyclerviewcrud

data class Info(
    var title:String,
    var description :String
){
    override fun toString(): String {
        return "$title\n$description"
    }
}

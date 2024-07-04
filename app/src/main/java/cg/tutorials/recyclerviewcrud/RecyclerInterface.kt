package cg.tutorials.recyclerviewcrud

interface RecyclerInterface {
    fun delete(position:Int)
    fun update(position: Int,oldTitile:String,oldDesp:String)
}
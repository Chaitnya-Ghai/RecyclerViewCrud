package cg.tutorials.recyclerviewcrud

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cg.tutorials.recyclerviewcrud.databinding.ActivityMainBinding
import cg.tutorials.recyclerviewcrud.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity(),RecyclerInterface{
    private lateinit var binding: ActivityMainBinding
    private var array = arrayListOf<Info>()
    private lateinit var todoDatabase: TodoDatabase
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var recyclerAdapter= RecyclerAdapter(this,array,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//
        todoDatabase = TodoDatabase.getInstance(this)
//
        binding.fab.setOnClickListener {
            val dialogBinding =CustomDialogBinding.inflate(layoutInflater)
            val dialog = Dialog(this).apply {
                setContentView(dialogBinding.root)
                setCancelable(false)
                window?.setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                show()
            }
            dialogBinding.okBtn.setOnClickListener {
                if (dialogBinding.edName.text.toString().isNullOrBlank()){
                    dialogBinding.edName.error="enter a title"
                }
                if (dialogBinding.edDescription.text.toString().isNullOrBlank()){
                    dialogBinding.edDescription.error="enter Description"
                }
                else{
                    val info = Info(title =dialogBinding.edName.text.toString(),
                        description = dialogBinding.edDescription.text.toString())
                    todoDatabase.todoInterface().insertTodo(info)
                    recyclerAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                    getData()
                }
            }
            dialogBinding.cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
        }

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = recyclerAdapter

        getData()
    }
    override fun delete(position: Int) {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("DELETE")
            setMessage("Are you really want to delete the information")
            setCancelable(false)
            setPositiveButton("No") { _, _ ->
                setCancelable(true)
            }
            setNegativeButton("Yes") { _, _ ->
                todoDatabase.todoInterface().deleteTodo(array[position])
                array.removeAt(position)
                recyclerAdapter.notifyDataSetChanged()
            }
            show()
        }
    }

    override fun update(position: Int, oldTitile:String, oldDesp:String) {

        val dialogBinding =CustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
            setCancelable(false)
            window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            show()
        }
        val update = "Update"
        dialogBinding.okBtn.text =update
        dialogBinding.edName.setText(oldTitile)
        dialogBinding.edDescription.setText(oldDesp)
        dialogBinding.okBtn.setOnClickListener {
            val newName =dialogBinding.edName.text.toString()
            val newDes = dialogBinding.edDescription.text.toString()
            if (newName.isBlank()){
                dialogBinding.edName.error="enter a title"
            }
            if (newDes.isBlank()) {
                dialogBinding.edDescription.error="Enter description"
            }
            else{
                val newInfo= Info(
                    id = array[position].id,
                    title =  dialogBinding.edName.text.toString(),
                    description = dialogBinding.edDescription.text.toString()
                )
                todoDatabase.todoInterface().updateTodo(newInfo)
                array[position]=newInfo
                recyclerAdapter.notifyItemChanged(position)
                dialog.dismiss()
                getData()
            }
        }
        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun getData(){
        array.clear()
        array.addAll(todoDatabase.todoInterface().getList())
        recyclerAdapter.notifyDataSetChanged()
    }
}
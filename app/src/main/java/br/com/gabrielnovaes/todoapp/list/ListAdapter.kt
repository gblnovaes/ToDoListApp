package br.com.gabrielnovaes.todoapp.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielnovaes.todoapp.R
import br.com.gabrielnovaes.todoapp.data.models.Priority
import br.com.gabrielnovaes.todoapp.data.models.ToDoData
import br.com.gabrielnovaes.todoapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var dataList = emptyList<ToDoData>()
    private lateinit var binding : RowLayoutBinding
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
          binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       binding.titleTxt.text = dataList[position].title
        binding.descriptionTxt.text = dataList[position].description
        binding.rowBackground.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)

        }


        when (dataList[position].priority) {
            Priority.HIGH -> {
                binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
            }
            Priority.MEDIUM -> {
                binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.yellow
                    )
                )
            }
            Priority.LOW -> {
                binding.priorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )
            }

        }

    }

    override fun getItemCount(): Int = dataList.size

    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}
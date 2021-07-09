package br.com.gabrielnovaes.todoapp.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
 import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.gabrielnovaes.todoapp.R
import br.com.gabrielnovaes.todoapp.SharedViewModel
import br.com.gabrielnovaes.todoapp.data.models.ToDoData
import br.com.gabrielnovaes.todoapp.data.viewmodel.ToDoViewModel
import br.com.gabrielnovaes.todoapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater,container,false)

        binding.args = args.currentItem
         binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmDeleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteItem() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel.deleteData(args.currentItem)
            Toast.makeText(context, "Successfully Deleted!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you want to remove ${args.currentItem.title}")
        builder.create().show()

    }

    private fun updateItem() {
        val mTitle = binding.currentTitleEt.text.toString()
        val mDescription = binding.currentDescriptionEt.text.toString()
        val getPriority = binding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = ToDoData(
                args.currentItem.id,
                mTitle,
                mSharedViewModel.parsePriority(getPriority),
                mDescription
            )
            mToDoViewModel.updateData(newData)
            Toast.makeText(context, "Successfully Added!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }

    }



}
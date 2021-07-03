package br.com.gabrielnovaes.todoapp.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrielnovaes.todoapp.R
import br.com.gabrielnovaes.todoapp.SharedViewModel
import br.com.gabrielnovaes.todoapp.data.viewmodel.ToDoViewModel
import br.com.gabrielnovaes.todoapp.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        setupRecyclerView()
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkEmptyDatabaseEmpty(data)
            adapter.setData(data)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmDeleteAllItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAllItem() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAllData()
            Toast.makeText(context, "Successfully Removed Everything!!", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure want to remove everything?")
        builder.create().show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
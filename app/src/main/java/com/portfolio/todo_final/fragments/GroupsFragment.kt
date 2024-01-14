package com.portfolio.todo_final.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.todo_final.MainActivity
import com.portfolio.todo_final.MyApplication
import com.portfolio.todo_final.R
import com.portfolio.todo_final.adapters.ListsAdapter
import com.portfolio.todo_final.models.Group
import com.portfolio.todo_final.utils.GridSpacingItemDecoration
import kotlinx.coroutines.launch

class GroupsFragment: Fragment(R.layout.fragment_groups) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listsAdapter: ListsAdapter

    private var arrayOfGroups = listOf<Group>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loadGroups()
        setupRecyclerView()
    }

    // MARK: - Private methods
    private fun init(view: View) {
        val parentActivity = (requireActivity() as? MainActivity)?: return
        parentActivity.setAppBarTitle("My Lists")
        parentActivity.menuButtonVisibility(View.GONE)
        parentActivity.propertiesButtonVisibility(View.VISIBLE)

        recyclerView = view.findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = gridLayoutManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_layout_margin)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, false))

        listsAdapter = ListsAdapter(arrayOfGroups)

        listsAdapter.onItemClick = { group ->
            if (group.id == 0L) {
                val bottomSheetFragment = AddGroupBottomSheetFragment()
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            } else {
                val args = Bundle().apply {
                    putLong("id", group.id)
                    putString("label", group.label)
                }

                findNavController().navigate(R.id.action_listsFragment_to_tasksFragment, args)
            }
        }

        recyclerView.adapter = listsAdapter
    }

    fun loadGroups() {
        val database = (activity?.application as MyApplication).database
        var groupsDao = database.groupsDao()

        lifecycleScope.launch {
            try {
                val groups = groupsDao.getGroups().toMutableList()
                groups.add(0, Group(0L, "Add List", R.drawable.ic_checked, 0))
                listsAdapter.updateGroups(groups)
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d("Saba", it) }
                Toast.makeText(activity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
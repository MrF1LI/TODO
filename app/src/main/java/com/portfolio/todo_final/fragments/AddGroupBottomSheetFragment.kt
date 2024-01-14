package com.portfolio.todo_final.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.portfolio.todo_final.MyApplication
import com.portfolio.todo_final.R
import com.portfolio.todo_final.models.Group
import kotlinx.coroutines.launch

class AddGroupBottomSheetFragment: BottomSheetDialogFragment(R.layout.fragment_add_group_bottom_sheet) {
    private lateinit var editTextName: AppCompatEditText
    private lateinit var buttonCreateGroup: AppCompatButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerListeners()
    }

    // MARK: - Private methods
    private fun init(view: View) {
        editTextName = view.findViewById(R.id.editTextName)
        buttonCreateGroup = view.findViewById(R.id.buttonCreateGroup)
    }

    private fun registerListeners() {
        buttonCreateGroup.setOnClickListener {
            val database = (activity?.application as MyApplication).database
            var groupsDao = database.groupsDao()
            val label = editTextName.text.toString()

            lifecycleScope.launch {
                groupsDao.insertGroup(Group(label = label, icon = R.drawable.ic_menu))
                dismiss()
                // Reload groups list here
            }
        }
    }
}
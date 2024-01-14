package com.portfolio.todo_final.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.portfolio.todo_final.MainActivity
import com.portfolio.todo_final.R

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    // MARK: - Private methods
    private fun init() {
        val parentActivity = (activity as? MainActivity)?: return
        parentActivity.setAppBarTitle("Settings")
        parentActivity.menuButtonVisibility(View.GONE)
        parentActivity.propertiesButtonVisibility(View.GONE)
    }
}
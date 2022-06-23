package com.lashaandzura.mines.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lashaandzura.mines.R
import com.lashaandzura.mines.fragments.ImageFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt("imageUrl", R.drawable.zura_da_me)
                }
            }
            else -> return ImageFragment().apply {
                arguments = Bundle().apply {
                    putInt("imageUrl", R.drawable.chven)
                }
            }
        }
    }
}
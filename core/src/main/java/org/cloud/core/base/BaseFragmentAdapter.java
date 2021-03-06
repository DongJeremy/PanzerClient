package org.cloud.core.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
* FileName: BaseFragmentAdapter
* Author: Admin
* Date: 2020/11/14 8:40
* Description: FragmentAdapter 基类
*/
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList;

    public BaseFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}


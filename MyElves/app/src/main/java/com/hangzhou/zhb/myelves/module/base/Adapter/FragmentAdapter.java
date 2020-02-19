package com.hangzhou.zhb.myelves.module.base.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hangzhou.zhb.myelves.module.base.BaseFragment;

import java.util.List;

/**
 * Created by zhb on 2017/12/27.
 *
 */

public class FragmentAdapter  extends FragmentPagerAdapter {
	private List<BaseFragment> list;

	public FragmentAdapter(FragmentManager fm, List<BaseFragment> list) {
		super(fm);
		this.list = list;
		notifyDataSetChanged();
	}

	public void setList(List<BaseFragment> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void removeListAt(int index) {
		if(index < list.size() && index >= 0) {
			list.remove(index);
			notifyDataSetChanged();
		}
	}

	public void addListAt(int index, BaseFragment baseFragment) {
		if(index < list.size() && index >= 0) {
			list.add(index, baseFragment);
			notifyDataSetChanged();
		}
	}

	public void replaceListAt(int index, BaseFragment baseFragment) {
		if(index < list.size() && index >= 0) {
			list.remove(index);
			list.add(index, baseFragment);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Fragment getItem(int position) {
		return null;
	}

	public BaseFragment getItemFragment(int position) {
		return list.get(position);
	}
}

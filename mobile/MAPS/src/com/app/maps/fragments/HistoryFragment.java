package com.app.maps.fragments;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.fragments.historySubFragments.HistoryFragment_TakesView;
import com.app.maps.fragments.historySubFragments.HistoryFragment_tempView;
import com.app.maps.fragments.historySubFragments.HistoryFragment_BPmeasuresView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends Fragment{

	private FragmentTabHost mTabHost;
//	private ActionBar actionBar;
	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String SELECT_TAB = "select_tab";
    private int select_tab = -1;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment newInstance(int sectionNumber) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    public static HistoryFragment newInstanceOpeningTab(int sectionNumber, int selectTab) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(SELECT_TAB, selectTab);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        View historyView = inflater.inflate(R.layout.fragment_history, container, false);
//        return historyView;
    	
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(this.getActivity(), getChildFragmentManager(), R.id.container);

        mTabHost.addTab(mTabHost.newTabSpec("bp_measures_view").setIndicator("P. Arterial"),
                HistoryFragment_BPmeasuresView.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("drugtake_view").setIndicator("Temperatura"),
                HistoryFragment_tempView.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("takes_view").setIndicator("Medicação"),
                HistoryFragment_TakesView.class, null);
        mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.red_drawer_menu));
        
        
        select_tab = getArguments().getInt(SELECT_TAB);
        if (select_tab != -1)
        	mTabHost.setCurrentTab(select_tab);
        

        return mTabHost;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HistoryFragment_land fragment = HistoryFragment_land.newInstanceOpeningTab(0, mTabHost.getCurrentTab());
	    FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.container, fragment);
	    fragmentTransaction.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
 
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
        
}

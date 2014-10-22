/**
 * BaseViewBinderAdapter.java
 * 
 * Created by zouyong on May 16, 2014,2014
 */
package com.chriszou.androidlibs;

import java.util.List;

import android.R.anim;
import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zouyong
 *
 */
public class BaseViewBinderAdapter<E> extends BaseListAdapter<E>{
    private ViewBinder<E> mViewBinder;
    private int mItemLayoutRes;
	/**
	 * @param context
	 * @param data
	 */
	public BaseViewBinderAdapter(Context context, List<E> data, int itemLayoutRes, ViewBinder<E> viewBinder) {
		super(context, data);
        mItemLayoutRes = itemLayoutRes;
        mViewBinder = viewBinder;
	}

    public BaseViewBinderAdapter(Context context, List<E> data, ViewBinder<E> viewBinder) {
    	this(context, data, android.R.layout.simple_list_item_1,viewBinder);
    }
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
        	convertView = mInflater.inflate(mItemLayoutRes, null);
        }
        
        mViewBinder.bindView(position, convertView, getItem(position), parent);
        return convertView;
	}
	
	public static interface ViewBinder<E> {
		public void bindView(int position, View view, E item, ViewGroup parent);
	}

}

/**
 * BaseListAdapter.java
 * 
 * Created by zouyong on 7:10:15 AM, 2014
 */
package com.chriszou.androidlibs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * An implementation of BaseAdapter that uses a List as data source.
 * @author zouyong
 *
 */
public abstract class BaseListAdapter<E> extends BaseAdapter {

    protected List<E> mData; 
    protected LayoutInflater mInflater;
    
    public BaseListAdapter(Context context, List<E> data) {
        mData = new ArrayList<E>();
    	for(E e: data){
    		mData.add(e);
    	}
        
    	mInflater = LayoutInflater.from(context);
    }
    
    /**
     * Add an element
     * @param e
     */
    public void add(E e){
    	mData.add(e);
        notifyDataSetChanged();
    }
    /**
     * Add several elements from an array
     * @param es
     */
    public void addAll(E[] es){
        for(E e: es) {
        	mData.add(e);
        }
        notifyDataSetChanged();
    }
    /**
     * Add several elements from a collection
     * @param es
     */
    public void addAll(Collection<E> es) {
    	mData.addAll(es);
        notifyDataSetChanged();
    }
    /**
     * Remove the element at the given index
     * @param index
     */
    public void remove(int index) {
    	mData.remove(index);
        notifyDataSetChanged();
    }
    
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public E getItem(int position) {
		// TODO Auto-generated method stub
        return mData.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}

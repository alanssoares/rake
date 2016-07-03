package com.utils;

import java.util.Comparator;
import java.util.Map;

public class MapValueComparator implements Comparator<Integer>{

	Map<Integer,Float> base;
	
	public MapValueComparator(Map<Integer,Float> base){
		this.base = base;
	}
	@Override
	public int compare(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return base.get(a).compareTo(base.get(b));
	}

}

package hr.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class MathUtil {
	public static int randomIndex(int size){
		int v=(int) Math.floor(Math.random()*size);
		return v;
	}
	
	public static <T> List<T> randomListItem(List<T> list,int count){
		List<T> newList=new ArrayList<T>(list);
		List<Integer> positionList=new ArrayList<Integer>();
		count=Math.min(newList.size(),count);
		for(int i=0;i<count;i++){
			int k= MathUtil.randomIndex(count);
			positionList.add(k);
			newList.remove(k);
		}
		Collections.sort(positionList);
		List<T> ret=new ArrayList<T>();
		for(Integer pos:positionList){
			ret.add(list.get(pos));
		}
		return ret;
	}
	
	
	
	   
}

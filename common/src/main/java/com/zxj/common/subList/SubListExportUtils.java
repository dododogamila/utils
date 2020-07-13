package cn.zxj.utils.subList;

import java.util.ArrayList;
import java.util.HashSet;

public class SubListExportUtils {
    private static HashSet<ArrayList<Integer>> getSubsets(ArrayList<Integer> subList) {
        HashSet<ArrayList<Integer>> allsubsets = new HashSet<ArrayList<Integer>>();
        int max = 1 << subList.size();
        for(int loop = 0; loop < max; loop++) {
            int index = 0;
            int temp = loop;
            ArrayList<Integer> currentCharList = new ArrayList<Integer>();
            while(temp > 0) {
                if((temp & 1) > 0) {
                    currentCharList.add(subList.get(index));
                }
                temp>>=1;
                index++;
            }
            allsubsets.add(currentCharList);
        }
        return allsubsets;
    }

    public static HashSet<ArrayList<Integer>> getSubsetsWithSize(int size){
        if (size<=0){
            return null;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int num = 1;num<=size;num++){
            list.add(num);
        }
        HashSet<ArrayList<Integer>> allsubsets = getSubsets(list);
        return allsubsets;
    }

    public static HashSet<ArrayList<Integer>> getSubsetsWithList(ArrayList<Integer> list){
        if (list==null||list.size()==0){
            return null;
        }
        HashSet<ArrayList<Integer>> allsubsets = getSubsets(list);
        return allsubsets;
    }

    public static void main(String[] args){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        // HashSet<ArrayList<Integer>> allsubsets = getSubsets(list);
        HashSet<ArrayList<Integer>> allsubsets = getSubsetsWithSize(3);

        for(ArrayList<Integer> subList : allsubsets) {
            System.out.println(subList);
        }
    }
}

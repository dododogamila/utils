package cn.zxj.utils.pailie;


import java.util.ArrayList;
import java.util.List;

public class PaiLieUtils {
    private static void pl(List<Integer> s,List<Integer> rs,List<List<Integer>>result){
        if(s.size()==1)
        {
            rs.add(s.get(0));
            result.add(new ArrayList<Integer>(rs));
            rs.remove(rs.size()-1);
        }else{
            for(int i=0;i<s.size();i++){

                rs.add(s.get(i));
                List<Integer> tmp=new ArrayList<Integer>();
                for(Integer a:s)
                    tmp.add(a);
                tmp.remove(i);
                pl(tmp,rs,result);
                rs.remove(rs.size()-1);
            }
        }
    }

    public static List<List<Integer>> pailie(List<Integer> list){
        if (list==null||list.size()==0){
            return null;
        }
        List<Integer> rs=new ArrayList<Integer>();
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        pl(list,rs,result);
        return result;
    }
    public static void main(String[] args) {

//        List<Integer> s=new ArrayList<Integer>();
//        List<Integer> rs=new ArrayList<Integer>();
//        List<List<Integer>> result = new ArrayList<List<Integer>>();
//        for(int i=1;i<=2;i++)
//            s.add(i);
//        pl(s,rs,result);
//        System.out.println("----");
//        for (List<Integer> lst:result){
//            System.out.println(lst.toString());
//        }

//        Integer num1 = 1;
//        Integer num2 = 1;
//        if (num1==num2){
//            System.out.println("eqauls");
//        }else {
//            System.out.println("noe equals");
//        }


    }

}

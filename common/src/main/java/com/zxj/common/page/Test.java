package cn.zxj.utils.page;

import cn.zxj.utils.page.dto.PageHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    @FunctionalInterface
    private interface Process<T>{
        int action(List<T> list);
    }

    private static int batchProcessWithCount(PageHandle pageHandle, Process process, int pageSize) {
        long pageCount = PageUtil.page(1, pageSize, pageHandle).getPageCount();
        int resultCount = 0;
        System.out.println("开始分页处理，共"+pageCount+"页");
        for (int page = 0; page < pageCount; page++) {
            List<Object> list = PageUtil.page(page,pageSize, pageHandle).getRows();
//            resultCount += process.action(list);
            System.out.println("分页处理"+pageSize+"条记录，已处理"+resultCount+"条记录");
        }
        return resultCount;
    }
    public static void main(String[] args){
//        batchProcessWithCount(() -> demoDao.findBsRosterBySchoolId(demoQuery), (Process<demo>) list -> {
//            //todo action
//            return 0;
//        }, 200000);
    }
}

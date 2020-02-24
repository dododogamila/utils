package cn.zxj.utils.page;
import cn.zxj.utils.page.dto.BaseQuery;
import cn.zxj.utils.page.dto.Page;
import cn.zxj.utils.page.dto.PageHandle;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis分页
 */
public class PageUtil {

	public static <E> Page<E> page(BaseQuery query, PageHandle pageHandle){
		return page(query.getPageNo(),query.getPageSize(), query.getOrderString(),pageHandle);
	}

	public static <E> Page<E> page(int pageNo,PageHandle pageHandle){
		return page(pageNo,Page.DEFAULT_PAGE_SIZE,null,pageHandle);
	}

	public static <E> Page<E> page(int pageNo,int pageSize,PageHandle pageHandle){
		return page(pageNo,pageSize,null,pageHandle);
	}

	public static <E> Page<E> page(int pageNo,int pageSize,String order,PageHandle pageHandle){
		com.github.pagehelper.Page<?> page = PageHelper.startPage(pageNo,pageSize,order);
		pageHandle.doQuery();
		Page<E> p=new Page(page.getPageNum(),page.getPageSize(), page.getTotal(), new ArrayList(page.getResult()));
		return p;
	}
	
	public static <E> List<E> list(BaseQuery query,PageHandle pageHandle){
		return list(query.getPageNo(),query.getPageSize(), query.getOrderString(),pageHandle);
	}

	public static <E> List<E> list(int pageNo,PageHandle pageHandle){
		return list(pageNo,Page.DEFAULT_PAGE_SIZE,null,pageHandle);
	}
	
	public static <E> List<E> list(int pageNo,int pageSize,PageHandle pageHandle){
		return list(pageNo,pageSize,null,pageHandle);
	}
	
	public static <E> List<E> list(int pageNo,int pageSize,String order,PageHandle pageHandle){
		com.github.pagehelper.Page<?> page =PageHelper.startPage(pageNo,pageSize,order);
		page.setCount(false);
		pageHandle.doQuery();
		return new ArrayList(page.getResult());
	}
	/**
	 * 总数查询
	 * @return
	 */
	public static long count(PageHandle pageHandle){
		com.github.pagehelper.Page<?> page = PageHelper.startPage(1, -1, true);
		pageHandle.doQuery();
		return page.getTotal();
	}

	public static long pageCount(int pageNum, int pageSize, PageHandle pageHandle) {
		com.github.pagehelper.Page<?> page = PageHelper.startPage(pageNum, pageSize, true);
		pageHandle.doQuery();
		return page.getPages();
	}
}

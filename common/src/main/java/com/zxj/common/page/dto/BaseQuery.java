package cn.zxj.utils.page.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件封装对象基类
 *
 */
public class BaseQuery implements Serializable {

	public List<OrderBy> getOrderBys() {
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<OrderBy>();
		}
		return this.orderBys;
	}

	@Transient
	public String getOrderString() {
		StringBuilder rtn = new StringBuilder();
		for (OrderBy orderBy : this.getOrderBys()) {
			if (!StringUtils.isEmpty(orderBy.getField())) {
				rtn.append(", ")
						.append(orderBy.getField())
						.append(" ")
						.append(orderBy.getDirection() == null ? ESortType.ASC
								.toString() : orderBy.getDirection());
			}
		}
		if (rtn.length() > 0) {
			return rtn.toString().substring(1);
		} else {
			return rtn.toString();
		}
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setOrderBys(List<OrderBy> orderBys) {
		this.orderBys = orderBys;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private List<OrderBy> orderBys = new ArrayList<OrderBy>();

	private int pageSize = Page.DEFAULT_PAGE_SIZE; // 页面大小

	private int pageNo = Page.DEFAULT_PAGE_NO; // 页码
}

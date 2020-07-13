package cn.zxj.utils.page.dto;

import org.springframework.util.StringUtils;

public class OrderBy {
	public OrderBy() {

	}

	public OrderBy(String field, String direction) {
		super();
		this.field = field;
		this.direction = direction;
	}

	public String getDirection() {
		if (StringUtils.isEmpty(this.direction)) {
			this.direction = ESortType.ASC.name();

		}
		return this.direction;
	}

	public String getField() {
		return this.field;
	}

	public void setDirection(String orderType) {
		this.direction = orderType;
	}

	public void setField(String orderby) {
		this.field = orderby;
	}

	private String field;

	private String direction;
}

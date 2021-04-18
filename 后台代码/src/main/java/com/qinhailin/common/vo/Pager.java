/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.common.vo;

public class Pager{
	public static final String SORT_DESC = "desc";
	public static final String SORT_ASC = "asc";
	private int startIndex;
	private int endIndex;
	private int pageNumber = 1;
	private int pageSize = 50;
	private int totalPage = 0;
	private long totalRow;
	private String sortname;
	private String sortorder;


	public long getTotalRow() {
		return totalRow;
	}

	public Pager setTotalRow(long totalRow) {
		this.totalRow = totalRow;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Pager setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public Pager setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public Pager setEndIndex(int endIndex) {
		this.endIndex = endIndex;
		return this;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public Pager setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public Pager setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		return this;
	}

	public String getSortname() {
		return sortname;
	}

	public Pager setSortname(String sortname) {
		this.sortname = sortname;
		return this;
	}

	public String getSortorder() {
		return sortorder;
	}

	public Pager setSortorder(String sortorder) {
		this.sortorder = sortorder;
		return this;
	}

	/**
	 * 倒序排序
	 * @param sortProperty
	 */
	public Pager sortDesc(String sortProperty) {
		this.sortorder = SORT_DESC;
		this.sortname = sortProperty;
		return this;
	}

	/**
	 * 顺序排序
	 * @param sortProperty
	 */
	public Pager sortAsc(String sortProperty) {
		this.sortorder = SORT_ASC;
		this.sortname = sortProperty;
		return this;
	}

}

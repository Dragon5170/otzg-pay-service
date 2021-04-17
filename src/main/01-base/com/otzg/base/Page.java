package com.otzg.base;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	/**
	 * 每页显示的记录数
	 */
	public final static int PAGESIZE = 20;

	private int pageSize = PAGESIZE;

	/**
	 * 返回的记录
	 */
	private List<T> items;

	/**
	 * 总记录数
	 */
	private long totalCount;

	/**
	 * 得到分页索引的数组
	 */
	private long[] indexes = new long[0]; 

	/**
	 * 当前分页索引
	 */
	private long startIndex = new Long(0);

	/**
	 * 存放页码数组，根据当前页判断生成
	 */
	private List<Long> pagination = new ArrayList<Long>();
	

	public Page(List<T> items, long totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(0);
		setPagination();
	}
	
	/**
	 * xy-2014-01-04-11
	 * @param totalCount
	 * @param startIndex
	 * @param PageSize
	 */
	public Page(long totalCount,int PageSize,int startIndex){
		setPageSize(PageSize);
		setTotalCount(totalCount);
		setStartIndex(startIndex);
		setPagination();
	}

	public Page(List<T> items, long totalCount, int startIndex) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		setPagination();
	}

	public Page(List<T> items, long totalCount, int pageSize,int startIndex) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		setPagination();
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = (int) (totalCount / pageSize);
			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new long[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = new Long(0);
		}
	}

	public long[] getIndexes() {
		return indexes;
	}

	public void setIndexes(long[] indexes) {
		this.indexes = indexes;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0) {
			this.startIndex = new Long(0);
		} else if (startIndex >= totalCount) {
			this.startIndex = indexes[indexes.length - 1];
		} else if (startIndex < 0) {
			this.startIndex = new Long(0);
		} else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	public long getNextIndex() {
		long nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount) {
			return getStartIndex();
		} else {
			return nextIndex;
		}
	}

	public long getPrevIndex() {
		long prevIndex = getStartIndex() - pageSize;
		if (prevIndex < 0) {
			return 0;
		} else {
			return prevIndex;
		}
	}

	/**
	 * 总页数； 总记录数%每页记录数
	 * @return
	 */
	public long getTotalPages() {
		long totalPages;
		if (getTotalCount() % getPageSize() > 0) {
			totalPages = getTotalCount() / getPageSize() + 1;
		} else {
			totalPages = getTotalCount() / getPageSize();
		}
		return totalPages;
	}

	/**
	 * 当前页数； 起始位置/每页记录数 + 1
	 * @return
	 */
	public long getCurrentPage() {
		long currentPage = getStartIndex() / getPageSize() + 1;
		return currentPage;
	}

	/**
	 * 尾 页; (总页数-1)*每页记录数
	 * 
	 * @return
	 */
	public long getLastPage() {
		long lastPage = (getTotalPages() - 1) * getPageSize();
		return lastPage;
	}

	public List<Long> getPagination() {
		return pagination;
	}

	/**
	 * 获取将要在页面显示的页码组，根据当前页和总页数判断
	 * @author lhr 2016年6月29日 下午5:20:23
	 */
	public void setPagination() {
		long total = getTotalPages();
		if (getCurrentPage() <= 7) {
			for (long i = 1; i <= total; i++) {
				pagination.add(i);
				if (i == 7) {
					break;
				}
			}
		} else {
			for (long i = getCurrentPage() - 3; i <= total; i++) {
				pagination.add(i);
				if (i == getCurrentPage() + 3) {
					break;
				}
			}
		}
	}
}

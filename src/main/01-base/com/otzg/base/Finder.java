package com.otzg.base;

import com.otzg.util.CheckUtil;

import java.io.Serializable;

/**
 * 基本查询类
 * @author G/2014-05-06
 *
 */
public class Finder implements Serializable {

	//查询关键词
	private String keyword;
	
	/**
	 * 支持layer的分页参数
	 * page 代表当前页码、limit 代表每页数据量
	 * layer会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
	 */

	//查询起始页数
	private int page=1;
	//每页显示记录数
	private int pageSize = 10;
	
	public final void setPage(int page) {
		this.page = page;
	}

	public final void setPageNum(int pageNum) {
		this.page = pageNum;
	}

	//兼容layer的分页参数
	public final void setLimit(int limit) {
		this.pageSize = limit;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final int getStartIndex() {
		return this.pageSize*(page>0?page-1:0);
	}

	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//到结束日期的最后1秒
	private static String sj = " 23:59:59";
	//控制查询状态
	private Integer status;
	//控制查询范围
	private String scop;
	//控制排序
	private String sorter;

	//地区代码
	private String areaCode;
	private String areaName;
	//低价
	private String loPrice;
	//高价
	private String hiPrice;
	//维度
	private String lat;
	//经度
	private String lng;


	public Finder() {
	}

	public final void setScop(String scop) {
		this.scop = scop;
	}

	public final String getScop() {
		return scop;
	}

	public final void setKeyword(String keyword) {
		this.keyword=keyword;
	}

	public final String getKeyword() {
		return keyword;
	}

	public String[] getKeywordSeg(){
		return (this.keyword!=null?this.keyword.split(","):null);
	}

	public final void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public final String getStartTime() {
		return startTime;
	}

	public final void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public final String getEndTime() {
		//如果传的是日期型，则后面加到当然最后一秒
		if(CheckUtil.isDate(endTime)){
			return endTime+sj;
		}else{
			//如果是时间型则直接返回
			return endTime;
		}
	}

	public final void setSorter(String sort) {
		this.sorter = sort;
	}

	public final String getSorter() {
		return sorter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getPage() {
		return page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static String getSj() {
		return sj;
	}

	public static void setSj(String sj) {
		Finder.sj = sj;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLoPrice() {
		return loPrice;
	}

	public void setLoPrice(String loPrice) {
		this.loPrice = loPrice;
	}

	public String getHiPrice() {
		return hiPrice;
	}

	public void setHiPrice(String hiPrice) {
		this.hiPrice = hiPrice;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}

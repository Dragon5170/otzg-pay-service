package com.otzg.file.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.persistence.*;

@Entity
@Table(name = "file_index")
public class FileIndex  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//名称
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	//文件存储路径
	@Column(name = "path", length = 255, nullable = false)
	private String path;

	//文件夹
	@ManyToOne
	@JoinColumn(name = "folder_id")
	private Folder folder;

	//大小
	@Column(name = "size")
	private Long size;

	//类型{0:头像,1:}
	@Column(name = "type")
	private Integer type;

	//修改时间
	@Column(name = "update_time", nullable = false, length = 19)
	private Date updateTime;

	@Column(name = "status", nullable = false)
	private Integer status;

	public FileIndex(){

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public JSONObject getBaseJson(){
		JSONObject jo = new JSONObject();
		jo.put("id",id);
		jo.put("name",name);
		jo.put("path",path);
		return jo;
	}

	public JSONObject getJson(){
		JSONObject jo = getBaseJson();
		jo.put("type",type);
		jo.put("size",size);
		jo.put("folder",Optional.ofNullable(this.getFolder()).map(Folder::getJson).orElse(new HashMap()));
		return jo;
	}
}

package org.qqbot.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaucenaoResult {
	private int status;
	private String msg;
	private SaucenaoHeaderItem header;
	private SaucenaoDataItem data;

	public SaucenaoResult() {

	}

	public SaucenaoResult(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SaucenaoHeaderItem getHeader() {
		return header;
	}

	public void setHeader(SaucenaoHeaderItem header) {
		this.header = header;
	}

	public SaucenaoDataItem getData() {
		return data;
	}

	public void setData(SaucenaoDataItem data) {
		this.data = data;
	}
}

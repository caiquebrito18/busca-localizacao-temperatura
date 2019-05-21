package com.temperatura.gerenciador.dtos;

import java.io.Serializable;

public class Fonte implements Serializable{

	private static final long serialVersionUID = 1008371098505993166L;

	private String title;
	private String slug;
	private String url;
	private String crawl_rate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCrawl_rate() {
		return crawl_rate;
	}
	public void setCrawl_rate(String crawl_rate) {
		this.crawl_rate = crawl_rate;
	}
	
	
}

package br.com.selecaoinvolves.goa.util;

public class Message {
	private boolean error=false;
	private String text;
	
	public Message() {}
	
	public Message(String text) {
		this.text=text;
	}
	
	public Message(String text,boolean error) {
		this.text=text;
		this.error=error;
	}
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}

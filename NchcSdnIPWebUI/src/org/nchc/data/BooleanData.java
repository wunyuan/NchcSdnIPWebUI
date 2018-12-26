package org.nchc.data;

public class BooleanData<T> {
	
	private boolean result;
	private T data;
	
	public BooleanData ( boolean result){
		this.result = result;
	}
	
	public BooleanData ( boolean result, T data){
		this.result = result;
		this.data = data;
	}
	
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean checked) {
		this.result = checked;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	

}

package br.com.brasilprev.core.util;

public interface IDomain {

	public Long getId();
	
	public Object clone() throws CloneNotSupportedException;
	
}

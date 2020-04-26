package br.com.brasilprev.core.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatterUtils {

	public static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"ddMMyyyy");
	public static final SimpleDateFormat sdf3 = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat sdf4 = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");
	
	public static final DecimalFormat df1 = new DecimalFormat("###,##0.00");
	
	
	static final Logger LOG = LoggerFactory.getLogger(FormatterUtils.class);
	
	public static String toCPF(String cpf) {
		
		if(cpf == null || cpf.contains("\\."))
			return cpf;
		
		for (int i=cpf.length() ; i<11 ; i++)
			cpf = "0" + cpf;
		
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
		Matcher matcher = pattern.matcher(cpf);
		if (matcher.matches()) 
			cpf = matcher.replaceAll("$1.$2.$3-$4");
		return cpf;			
	}

	public static String toCNPJ(String cnpj) {
		
		if(cnpj == null || cnpj.contains("\\."))
			return cnpj;
		
		for (int i=cnpj.length() ; i<14 ; i++)
			cnpj = "0" + cnpj;
		
		Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
		Matcher matcher = pattern.matcher(cnpj);
		if (matcher.matches()) 
			cnpj = matcher.replaceAll("$1.$2.$3/$4-$5");
		return cnpj;
	}

	public static String toCurrency(Double valor) {

		if (valor == null)
			return null;

		return df1.format(valor);
	}

	public static String toDate(Date data) {

		if (data == null)
			return null;

		return sdf4.format(data);
	}

	public static Date toDate(String data) {

		if (data == null || data.trim().length() == 0)
			return null;

		if (data.contains("/")) {
			try {
				return sdf1.parse(data);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} else if (data.contains("-")) {
			try {
				return sdf3.parse(data);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} else if (data.contains("Z")) {
			try {
				return sdf4.parse(data);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} else {
			try {
				return sdf2.parse(data);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		return null;
	}
	
	public static String toFillStringWithZero(String value, int len) {
		if(value == null)
			value = "";
		
		while(value.length() < len)
			value = "0" + value;
		
		return value;
		
	}
	
	public static String converterFormato(String data, SimpleDateFormat sdf) {
		
		if(sdf == null)
			return null;
		
		Date date = toDate(data);
		if(date != null)
			return sdf.format(date);
					
		return null;
	}
	
}

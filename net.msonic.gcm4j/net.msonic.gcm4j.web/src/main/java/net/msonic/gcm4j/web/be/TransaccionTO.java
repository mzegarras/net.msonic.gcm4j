package net.msonic.gcm4j.web.be;

import java.util.Date;

public class TransaccionTO {
	
	
	private String name;
	private String lastName;
	private Date fecha;
	private String numberTx;
	private String cuentaCargo;
	private String cuentaCargoMoneda;
	private String cuentaCargoAlias;
	
	private String moneda;
	private double monto;
	
	
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	private String cuentaDestino;
	private String nombreDestino;
	
	public String getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	public String getNombreDestino() {
		return nombreDestino;
	}
	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}
	public String getCuentaCargo() {
		return cuentaCargo;
	}
	public void setCuentaCargo(String cuentaCargo) {
		this.cuentaCargo = cuentaCargo;
	}
	public String getCuentaCargoMoneda() {
		return cuentaCargoMoneda;
	}
	public void setCuentaCargoMoneda(String cuentaCargoMoneda) {
		this.cuentaCargoMoneda = cuentaCargoMoneda;
	}
	public String getCuentaCargoAlias() {
		return cuentaCargoAlias;
	}
	public void setCuentaCargoAlias(String cuentaCargoAlias) {
		this.cuentaCargoAlias = cuentaCargoAlias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNumberTx() {
		return numberTx;
	}
	public void setNumberTx(String numberTx) {
		this.numberTx = numberTx;
	}
	
	
}

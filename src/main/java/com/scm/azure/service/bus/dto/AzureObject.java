package com.scm.azure.service.bus.dto;

public class AzureObject {
	private String patientName;
	private String patientAddress;
	private String disease;
	private String dateOfBirth;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "AzureObject [patientName=" + patientName + ", patientAddress=" + patientAddress + ", disease=" + disease
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}
}
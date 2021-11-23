package com.revature.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.sql.rowset.serial.SerialBlob;

public class ERS_reimbursement {

	private int reimb_id; 
	private double reimb_amount;
	private LocalDateTime reimb_submitted;
	private LocalDateTime reimb_resolved; 
	private String reimb_status; 
	private String reimb_type;
	private String reimb_description; 
	private SerialBlob reimb_receipt;
	private int reimb_author; 
	private int reimb_resolver; 
	
	public ERS_reimbursement() {
		
		this.reimb_status = "pending";
		this.reimb_submitted = LocalDateTime.now(); 
		
	}
	
	public ERS_reimbursement(double reimb_amount, String reimb_type, String reimb_description,
			SerialBlob reimb_receipt, int reimb_author) {
		
		this.reimb_amount = reimb_amount;
		this.reimb_submitted = LocalDateTime.now(); 
		this.reimb_status = "pending";
		this.reimb_type = reimb_type; 
		this.reimb_description = reimb_description;
		this.reimb_receipt = reimb_receipt; 
		this.reimb_author = reimb_author; 
		
		
	}
	
	

	public int getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}

	public double getReimb_amount() {
		return reimb_amount;
	}

	public void setReimb_amount(double reimb_amount) {
		this.reimb_amount = reimb_amount;
	}

	public LocalDateTime getReimb_submitted() {
		return reimb_submitted;
	}

	public void setReimb_submitted(LocalDateTime reimb_submitted) {
		this.reimb_submitted = reimb_submitted;
	}

	public LocalDateTime getReimb_resolved() {
		return reimb_resolved;
	}

	public void setReimb_resolved(LocalDateTime reimb_resolved) {
		this.reimb_resolved = reimb_resolved;
	}

	public String getReimb_status() {
		return reimb_status;
	}

	public void setReimb_status(String reimb_status) {
		this.reimb_status = reimb_status;
	}

	public String getReimb_type() {
		return reimb_type;
	}

	public void setReimb_type(String reimb_type) {
		this.reimb_type = reimb_type;
	}

	public String getReimb_description() {
		return reimb_description;
	}

	public void setReimb_description(String reimb_description) {
		this.reimb_description = reimb_description;
	}

	public SerialBlob getReimb_receipt() {
		return reimb_receipt;
	}

	public void setReimb_receipt(SerialBlob reimb_receipt) {
		this.reimb_receipt = reimb_receipt;
	}

	public int getReimb_author() {
		return reimb_author;
	}

	public void setReimb_author(int reimb_author) {
		this.reimb_author = reimb_author;
	}

	public int getReimb_resolver() {
		return reimb_resolver;
	}

	public void setReimb_resolver(int reimb_resolver) {
		this.reimb_resolver = reimb_resolver;
	}



	@Override
	public int hashCode() {
		return Objects.hash(reimb_amount, reimb_author, reimb_description, reimb_id, reimb_receipt, reimb_resolved,
				reimb_resolver, reimb_status, reimb_submitted, reimb_type);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ERS_reimbursement other = (ERS_reimbursement) obj;
		return Double.doubleToLongBits(reimb_amount) == Double.doubleToLongBits(other.reimb_amount)
				&& reimb_author == other.reimb_author && Objects.equals(reimb_description, other.reimb_description)
				&& reimb_id == other.reimb_id && Objects.equals(reimb_receipt, other.reimb_receipt)
				&& Objects.equals(reimb_resolved, other.reimb_resolved) && reimb_resolver == other.reimb_resolver
				&& Objects.equals(reimb_status, other.reimb_status)
				&& Objects.equals(reimb_submitted, other.reimb_submitted)
				&& Objects.equals(reimb_type, other.reimb_type);
	}



	@Override
	public String toString() {
		return "ERS_reimbursement [reimb_id=" + reimb_id + ", reimb_amount=" + reimb_amount + ", reimb_submitted="
				+ reimb_submitted + ", reimb_resolved=" + reimb_resolved + ", reimb_status=" + reimb_status
				+ ", reimb_type=" + reimb_type + ", reimb_description=" + reimb_description + ", reimb_receipt="
				+ reimb_receipt + ", reimb_author=" + reimb_author + ", reimb_resolver=" + reimb_resolver + "]";
	}
	
	
	
	
}

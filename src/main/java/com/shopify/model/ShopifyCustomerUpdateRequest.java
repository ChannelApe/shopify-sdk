package com.shopify.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.ALWAYS)
public class ShopifyCustomerUpdateRequest {

	private String id;
	private String email;
	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastname;
	private String phone;

	private ShopifyCustomerUpdateRequest(final Steps steps) {
		this.id = steps.id;
		this.email = steps.email;
		this.firstName = steps.firstName;
		this.lastname = steps.lastname;
		this.phone = steps.phone;
	}

	public static interface BuildStep {
		ShopifyCustomerUpdateRequest build();
	}

	public static interface PhoneStep {
		BuildStep withPhone(final String phone);
	}

	public static interface EmailStep {
		PhoneStep withEmail(final String email);
	}

	public static interface LastNameStep {
		EmailStep withLastName(final String lastName);
	}

	public static interface FirstNameStep {
		LastNameStep withFirstName(final String firstName);
	}

	public static interface IdStep {
		FirstNameStep withId(final String id);
	}

	public static IdStep newBuilder() {
		return new Steps();
	}

	private static class Steps implements IdStep, FirstNameStep, LastNameStep, EmailStep, PhoneStep, BuildStep {
		private String id;
		private String email;
		private String firstName;
		private String lastname;
		private String phone;

		@Override
		public ShopifyCustomerUpdateRequest build() {
			return new ShopifyCustomerUpdateRequest(this);
		}

		@Override
		public BuildStep withPhone(final String phone) {
			this.phone = phone;
			return this;
		}

		@Override
		public PhoneStep withEmail(final String email) {
			this.email = email;
			return this;
		}

		@Override
		public EmailStep withLastName(final String lastName) {
			this.lastname = lastName;
			return this;
		}

		@Override
		public LastNameStep withFirstName(final String firstName) {
			this.firstName = firstName;
			return this;
		}

		@Override
		public FirstNameStep withId(final String id) {
			this.id = id;
			return this;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

}

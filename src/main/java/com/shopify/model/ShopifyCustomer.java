package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import com.shopify.model.adapters.TagsAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyCustomer extends AbstractModel {

	private String id;
	private String email;
	@XmlElement(name = "accepts_marketing")
	private boolean acceptsMarketing;
	@XmlElement(name = "accepts_marketing_updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime acceptsMarketingUpdatedAt;
	@XmlElement(name = "created_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime createdAt;
	@XmlElement(name = "updated_at")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime updatedAt;
	@XmlElement(name = "first_name")
	private String firstName;
	@XmlElement(name = "last_name")
	private String lastname;
	private String phone;
	@XmlElement(name = "orders_count")
	private long ordersCount;
	private String state;
	@XmlElement(name = "total_spent")
	private BigDecimal totalSpent;
	private String note;
	@XmlElement(name = "last_order_id")
	private String lastOrderId;
	@XmlElement(name = "verified_email")
	private Boolean verifiedEmail;
	@XmlElement(name = "multipass_identifier")
	private String multipass_identifier;
	@XmlElement(name = "tax_exempt")
	private Boolean taxExempt;
	@XmlJavaTypeAdapter(TagsAdapter.class)
	@XmlElement(name = "tags")
	private Set<String> tags = new HashSet<>();
	@XmlElement(name = "last_order_name")
	private String lastOrderName;
	private String currency;
	@XmlElement(name = "marketing_opt_in_level")
	private String marketingOptInLevel;
	@XmlElement(name = "tax_exemptions")
	private List<String> taxExemptions;
	@XmlElement(name = "sms_marketing_consent")
	private SMSMarketingConsent smsMarketingConsent;
	@XmlElement(name = "default_address")
	private ShopifyAddress defaultAddress;
	private List<ShopifyAddress> addresses;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAcceptsMarketing() {
		return acceptsMarketing;
	}

	public void setAcceptsMarketing(boolean acceptsMarketing) {
		this.acceptsMarketing = acceptsMarketing;
	}

	public DateTime getAcceptsMarketingUpdatedAt() {
		return acceptsMarketingUpdatedAt;
	}

	public void setAcceptsMarketingUpdatedAt(final DateTime acceptsMarketingUpdatedAt) {
		this.acceptsMarketingUpdatedAt = acceptsMarketingUpdatedAt;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getOrdersCount() {
		return ordersCount;
	}

	public void setOrdersCount(long ordersCount) {
		this.ordersCount = ordersCount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(BigDecimal totalSpent) {
		this.totalSpent = totalSpent;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(final String lastOrderId) {
		this.lastOrderId = lastOrderId;
	}

	public Boolean getVerifiedEmail() {
		return verifiedEmail;
	}

	public void setVerifiedEmail(final Boolean verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}

	public String getMultipass_identifier() {
		return multipass_identifier;
	}

	public void setMultipass_identifier(final String multipass_identifier) {
		this.multipass_identifier = multipass_identifier;
	}

	public Boolean getTaxExempt() {
		return taxExempt;
	}

	public void setTaxExempt(final Boolean taxExempt) {
		this.taxExempt = taxExempt;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	public String getLastOrderName() {
		return lastOrderName;
	}

	public void setLastOrderName(final String lastOrderName) {
		this.lastOrderName = lastOrderName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public String getMarketingOptInLevel() {
		return marketingOptInLevel;
	}

	public void setMarketingOptInLevel(final String marketingOptInLevel) {
		this.marketingOptInLevel = marketingOptInLevel;
	}

	public List<String> getTaxExemptions() {
		return taxExemptions;
	}

	public void setTaxExemptions(final List<String> taxExemptions) {
		this.taxExemptions = taxExemptions;
	}

	public SMSMarketingConsent getSmsMarketingConsent() {
		return smsMarketingConsent;
	}

	public void setSmsMarketingConsent(final SMSMarketingConsent smsMarketingConsent) {
		this.smsMarketingConsent = smsMarketingConsent;
	}

	public ShopifyAddress getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(final ShopifyAddress defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public List<ShopifyAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(final List<ShopifyAddress> addresses) {
		this.addresses = addresses;
	}
}

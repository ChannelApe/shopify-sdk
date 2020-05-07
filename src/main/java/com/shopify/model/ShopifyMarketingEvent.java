package com.shopify.model;

import com.shopify.model.adapters.DateTimeAdapter;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopifyMarketingEvent {
    @XmlElement(name = "remote_id")
    private String remoteId;
    @XmlElement(name = "event_type")
    private String eventType;
    @XmlElement(name = "marketing_channel")
    private String marketingChannel;
    private boolean paid;
    @XmlElement(name = "referring_domain")
    private String referringDomain;
    private BigDecimal budget;
    private Currency currency;
    @XmlElement(name = "budget_type")
    private String budgetType;
    @XmlElement(name = "started_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime startedAt;
    @XmlElement(name = "ended_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime endedAt;
    @XmlElement(name = "scheduled_to_end_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime scheduledToEndAt;
    @XmlElement(name = "UTM Parameters")
    private Object utmParameters;
    private String description;
    @XmlElement(name = "manage_url")
    private String manageUrl;
    @XmlElement(name = "preview_url")
    private String previewUrl;
    @XmlElement(name = "marketed_resources")
    private List<Object> marketedResources;

    public void setRemoteId(final String remoteId) {
        this.remoteId = remoteId;
    }
    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }
    public void setMarketingChannel(final String marketingChannel) {
        this.marketingChannel = marketingChannel;
    }
    public void setPaid(final boolean paid) {
        this.paid = paid;
    }
    public void setReferringDomain(final String referringDomain) {
        this.referringDomain = referringDomain;
    }
    public void setBudget(final BigDecimal budget) {
        this.budget = budget;
    }
    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }
    public void setBudgetType(final String budgetType) {
        this.budgetType = budgetType;
    }
    public void setStartedAt(final DateTime startedAt) {
        this.startedAt = startedAt;
    }
    public void setEndedAt(final DateTime endedAt) {
        this.endedAt = endedAt;
    }
    public void setScheduledToEndAt(final DateTime scheduledToEndAt) {
        this.scheduledToEndAt = scheduledToEndAt;
    }
    public void setUtmParameters(final Object utmParameters) {
        this.utmParameters = utmParameters;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public void setManageUrl(final String manageUrl) {
        this.manageUrl = manageUrl;
    }
    public void setPreviewUrl(final String previewUrl) {
        this.previewUrl = previewUrl;
    }
    public void setMarketedResources(final List<Object> marketedResources) {
        this.marketedResources = marketedResources;
    }

    public String getRemoteId() {
        return remoteId;
    }
    public String getEventType() {
        return eventType;
    }
    public String getMarketingChannel() {
        return marketingChannel;
    }
    public boolean getPaid() {
        return paid;
    }
    public String getReferringDomain() {
        return referringDomain;
    }
    public BigDecimal getBudget() {
        return budget;
    }
    public Currency getCurrency() {
        return currency;
    }
    public String getBudgetType() {
        return budgetType;
    }
    public DateTime getStartedAt() {
        return startedAt;
    }
    public DateTime getEndedAt() {
        return endedAt;
    }
    public DateTime getScheduledToEndAt() {
        return scheduledToEndAt;
    }
    public Object getUtmParameters() {
        return utmParameters;
    }
    public String getDescription() {
        return description;
    }
    public String getManageUrl() {
        return manageUrl;
    }
    public String getPreviewUrl() {
        return previewUrl;
    }
    public List<Object> getMarketedResources() {
        return marketedResources;
    }
}

package com.shopify.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopify.model.adapters.DateTimeAdapter;
import lombok.Data;
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
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private MarketingEvent utmParameters;
    private String description;
    @XmlElement(name = "manage_url")
    private String manageUrl;
    @XmlElement(name = "preview_url")
    private String previewUrl;
    @XmlElement(name = "marketed_resources")
    private List<MarketedResource> marketedResources;
}

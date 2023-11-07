package ${package};

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "code_icao",
    "code_iata",
    "code_lid",
    "timezone",
    "name",
    "city",
    "airport_info_url"
})
@Generated("jsonschema2pojo")
public class Destination {

    @JsonProperty("code")
    private String code;
    @JsonProperty("code_icao")
    private String codeIcao;
    @JsonProperty("code_iata")
    private String codeIata;
    @JsonProperty("code_lid")
    private String codeLid;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("name")
    private String name;
    @JsonProperty("city")
    private String city;
    @JsonProperty("airport_info_url")
    private String airportInfoUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("code_icao")
    public String getCodeIcao() {
        return codeIcao;
    }

    @JsonProperty("code_icao")
    public void setCodeIcao(String codeIcao) {
        this.codeIcao = codeIcao;
    }

    @JsonProperty("code_iata")
    public String getCodeIata() {
        return codeIata;
    }

    @JsonProperty("code_iata")
    public void setCodeIata(String codeIata) {
        this.codeIata = codeIata;
    }

    @JsonProperty("code_lid")
    public String getCodeLid() {
        return codeLid;
    }

    @JsonProperty("code_lid")
    public void setCodeLid(String codeLid) {
        this.codeLid = codeLid;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("airport_info_url")
    public String getAirportInfoUrl() {
        return airportInfoUrl;
    }

    @JsonProperty("airport_info_url")
    public void setAirportInfoUrl(String airportInfoUrl) {
        this.airportInfoUrl = airportInfoUrl;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


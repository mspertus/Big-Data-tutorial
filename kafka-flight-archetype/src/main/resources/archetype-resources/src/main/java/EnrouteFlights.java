package ${package};

import java.util.LinkedHashMap;
import java.util.List;
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
    "enroute",
    "links",
    "num_pages"
})
@Generated("jsonschema2pojo")
public class EnrouteFlights {

    @JsonProperty("enroute")
    private List<Enroute> enroute;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("num_pages")
    private Integer numPages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("enroute")
    public List<Enroute> getEnroute() {
        return enroute;
    }

    @JsonProperty("enroute")
    public void setEnroute(List<Enroute> enroute) {
        this.enroute = enroute;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("num_pages")
    public Integer getNumPages() {
        return numPages;
    }

    @JsonProperty("num_pages")
    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
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

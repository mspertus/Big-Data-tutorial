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
    "ident",
    "ident_icao",
    "ident_iata",
    "actual_runway_off",
    "actual_runway_on",
    "fa_flight_id",
    "operator",
    "operator_icao",
    "operator_iata",
    "flight_number",
    "registration",
    "atc_ident",
    "inbound_fa_flight_id",
    "codeshares",
    "codeshares_iata",
    "blocked",
    "diverted",
    "cancelled",
    "position_only",
    "origin",
    "destination",
    "departure_delay",
    "arrival_delay",
    "filed_ete",
    "scheduled_out",
    "estimated_out",
    "actual_out",
    "scheduled_off",
    "estimated_off",
    "actual_off",
    "scheduled_on",
    "estimated_on",
    "actual_on",
    "scheduled_in",
    "estimated_in",
    "actual_in",
    "progress_percent",
    "status",
    "aircraft_type",
    "route_distance",
    "filed_airspeed",
    "filed_altitude",
    "route",
    "baggage_claim",
    "seats_cabin_business",
    "seats_cabin_coach",
    "seats_cabin_first",
    "gate_origin",
    "gate_destination",
    "terminal_origin",
    "terminal_destination",
    "type"
})
@Generated("jsonschema2pojo")
public class Enroute {

    @JsonProperty("ident")
    private String ident;
    @JsonProperty("ident_icao")
    private String identIcao;
    @JsonProperty("ident_iata")
    private String identIata;
    @JsonProperty("actual_runway_off")
    private String actualRunwayOff;
    @JsonProperty("actual_runway_on")
    private Object actualRunwayOn;
    @JsonProperty("fa_flight_id")
    private String faFlightId;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("operator_icao")
    private String operatorIcao;
    @JsonProperty("operator_iata")
    private String operatorIata;
    @JsonProperty("flight_number")
    private String flightNumber;
    @JsonProperty("registration")
    private String registration;
    @JsonProperty("atc_ident")
    private Object atcIdent;
    @JsonProperty("inbound_fa_flight_id")
    private String inboundFaFlightId;
    @JsonProperty("codeshares")
    private List<String> codeshares;
    @JsonProperty("codeshares_iata")
    private List<String> codesharesIata;
    @JsonProperty("blocked")
    private Boolean blocked;
    @JsonProperty("diverted")
    private Boolean diverted;
    @JsonProperty("cancelled")
    private Boolean cancelled;
    @JsonProperty("position_only")
    private Boolean positionOnly;
    @JsonProperty("origin")
    private Origin origin;
    @JsonProperty("destination")
    private Destination destination;
    @JsonProperty("departure_delay")
    private Integer departureDelay;
    @JsonProperty("arrival_delay")
    private Integer arrivalDelay;
    @JsonProperty("filed_ete")
    private Integer filedEte;
    @JsonProperty("scheduled_out")
    private String scheduledOut;
    @JsonProperty("estimated_out")
    private String estimatedOut;
    @JsonProperty("actual_out")
    private String actualOut;
    @JsonProperty("scheduled_off")
    private String scheduledOff;
    @JsonProperty("estimated_off")
    private String estimatedOff;
    @JsonProperty("actual_off")
    private String actualOff;
    @JsonProperty("scheduled_on")
    private String scheduledOn;
    @JsonProperty("estimated_on")
    private String estimatedOn;
    @JsonProperty("actual_on")
    private Object actualOn;
    @JsonProperty("scheduled_in")
    private String scheduledIn;
    @JsonProperty("estimated_in")
    private String estimatedIn;
    @JsonProperty("actual_in")
    private Object actualIn;
    @JsonProperty("progress_percent")
    private Integer progressPercent;
    @JsonProperty("status")
    private String status;
    @JsonProperty("aircraft_type")
    private String aircraftType;
    @JsonProperty("route_distance")
    private Integer routeDistance;
    @JsonProperty("filed_airspeed")
    private Integer filedAirspeed;
    @JsonProperty("filed_altitude")
    private Integer filedAltitude;
    @JsonProperty("route")
    private String route;
    @JsonProperty("baggage_claim")
    private Object baggageClaim;
    @JsonProperty("seats_cabin_business")
    private Object seatsCabinBusiness;
    @JsonProperty("seats_cabin_coach")
    private Object seatsCabinCoach;
    @JsonProperty("seats_cabin_first")
    private Integer seatsCabinFirst;
    @JsonProperty("gate_origin")
    private String gateOrigin;
    @JsonProperty("gate_destination")
    private String gateDestination;
    @JsonProperty("terminal_origin")
    private String terminalOrigin;
    @JsonProperty("terminal_destination")
    private String terminalDestination;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("ident")
    public String getIdent() {
        return ident;
    }

    @JsonProperty("ident")
    public void setIdent(String ident) {
        this.ident = ident;
    }

    @JsonProperty("ident_icao")
    public String getIdentIcao() {
        return identIcao;
    }

    @JsonProperty("ident_icao")
    public void setIdentIcao(String identIcao) {
        this.identIcao = identIcao;
    }

    @JsonProperty("ident_iata")
    public String getIdentIata() {
        return identIata;
    }

    @JsonProperty("ident_iata")
    public void setIdentIata(String identIata) {
        this.identIata = identIata;
    }

    @JsonProperty("actual_runway_off")
    public String getActualRunwayOff() {
        return actualRunwayOff;
    }

    @JsonProperty("actual_runway_off")
    public void setActualRunwayOff(String actualRunwayOff) {
        this.actualRunwayOff = actualRunwayOff;
    }

    @JsonProperty("actual_runway_on")
    public Object getActualRunwayOn() {
        return actualRunwayOn;
    }

    @JsonProperty("actual_runway_on")
    public void setActualRunwayOn(Object actualRunwayOn) {
        this.actualRunwayOn = actualRunwayOn;
    }

    @JsonProperty("fa_flight_id")
    public String getFaFlightId() {
        return faFlightId;
    }

    @JsonProperty("fa_flight_id")
    public void setFaFlightId(String faFlightId) {
        this.faFlightId = faFlightId;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("operator_icao")
    public String getOperatorIcao() {
        return operatorIcao;
    }

    @JsonProperty("operator_icao")
    public void setOperatorIcao(String operatorIcao) {
        this.operatorIcao = operatorIcao;
    }

    @JsonProperty("operator_iata")
    public String getOperatorIata() {
        return operatorIata;
    }

    @JsonProperty("operator_iata")
    public void setOperatorIata(String operatorIata) {
        this.operatorIata = operatorIata;
    }

    @JsonProperty("flight_number")
    public String getFlightNumber() {
        return flightNumber;
    }

    @JsonProperty("flight_number")
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @JsonProperty("registration")
    public String getRegistration() {
        return registration;
    }

    @JsonProperty("registration")
    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @JsonProperty("atc_ident")
    public Object getAtcIdent() {
        return atcIdent;
    }

    @JsonProperty("atc_ident")
    public void setAtcIdent(Object atcIdent) {
        this.atcIdent = atcIdent;
    }

    @JsonProperty("inbound_fa_flight_id")
    public String getInboundFaFlightId() {
        return inboundFaFlightId;
    }

    @JsonProperty("inbound_fa_flight_id")
    public void setInboundFaFlightId(String inboundFaFlightId) {
        this.inboundFaFlightId = inboundFaFlightId;
    }

    @JsonProperty("codeshares")
    public List<String> getCodeshares() {
        return codeshares;
    }

    @JsonProperty("codeshares")
    public void setCodeshares(List<String> codeshares) {
        this.codeshares = codeshares;
    }

    @JsonProperty("codeshares_iata")
    public List<String> getCodesharesIata() {
        return codesharesIata;
    }

    @JsonProperty("codeshares_iata")
    public void setCodesharesIata(List<String> codesharesIata) {
        this.codesharesIata = codesharesIata;
    }

    @JsonProperty("blocked")
    public Boolean getBlocked() {
        return blocked;
    }

    @JsonProperty("blocked")
    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @JsonProperty("diverted")
    public Boolean getDiverted() {
        return diverted;
    }

    @JsonProperty("diverted")
    public void setDiverted(Boolean diverted) {
        this.diverted = diverted;
    }

    @JsonProperty("cancelled")
    public Boolean getCancelled() {
        return cancelled;
    }

    @JsonProperty("cancelled")
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    @JsonProperty("position_only")
    public Boolean getPositionOnly() {
        return positionOnly;
    }

    @JsonProperty("position_only")
    public void setPositionOnly(Boolean positionOnly) {
        this.positionOnly = positionOnly;
    }

    @JsonProperty("origin")
    public Origin getOrigin() {
        return origin;
    }

    @JsonProperty("origin")
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @JsonProperty("destination")
    public Destination getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @JsonProperty("departure_delay")
    public Integer getDepartureDelay() {
        return departureDelay;
    }

    @JsonProperty("departure_delay")
    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }

    @JsonProperty("arrival_delay")
    public Integer getArrivalDelay() {
        return arrivalDelay;
    }

    @JsonProperty("arrival_delay")
    public void setArrivalDelay(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    @JsonProperty("filed_ete")
    public Integer getFiledEte() {
        return filedEte;
    }

    @JsonProperty("filed_ete")
    public void setFiledEte(Integer filedEte) {
        this.filedEte = filedEte;
    }

    @JsonProperty("scheduled_out")
    public String getScheduledOut() {
        return scheduledOut;
    }

    @JsonProperty("scheduled_out")
    public void setScheduledOut(String scheduledOut) {
        this.scheduledOut = scheduledOut;
    }

    @JsonProperty("estimated_out")
    public String getEstimatedOut() {
        return estimatedOut;
    }

    @JsonProperty("estimated_out")
    public void setEstimatedOut(String estimatedOut) {
        this.estimatedOut = estimatedOut;
    }

    @JsonProperty("actual_out")
    public String getActualOut() {
        return actualOut;
    }

    @JsonProperty("actual_out")
    public void setActualOut(String actualOut) {
        this.actualOut = actualOut;
    }

    @JsonProperty("scheduled_off")
    public String getScheduledOff() {
        return scheduledOff;
    }

    @JsonProperty("scheduled_off")
    public void setScheduledOff(String scheduledOff) {
        this.scheduledOff = scheduledOff;
    }

    @JsonProperty("estimated_off")
    public String getEstimatedOff() {
        return estimatedOff;
    }

    @JsonProperty("estimated_off")
    public void setEstimatedOff(String estimatedOff) {
        this.estimatedOff = estimatedOff;
    }

    @JsonProperty("actual_off")
    public String getActualOff() {
        return actualOff;
    }

    @JsonProperty("actual_off")
    public void setActualOff(String actualOff) {
        this.actualOff = actualOff;
    }

    @JsonProperty("scheduled_on")
    public String getScheduledOn() {
        return scheduledOn;
    }

    @JsonProperty("scheduled_on")
    public void setScheduledOn(String scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    @JsonProperty("estimated_on")
    public String getEstimatedOn() {
        return estimatedOn;
    }

    @JsonProperty("estimated_on")
    public void setEstimatedOn(String estimatedOn) {
        this.estimatedOn = estimatedOn;
    }

    @JsonProperty("actual_on")
    public Object getActualOn() {
        return actualOn;
    }

    @JsonProperty("actual_on")
    public void setActualOn(Object actualOn) {
        this.actualOn = actualOn;
    }

    @JsonProperty("scheduled_in")
    public String getScheduledIn() {
        return scheduledIn;
    }

    @JsonProperty("scheduled_in")
    public void setScheduledIn(String scheduledIn) {
        this.scheduledIn = scheduledIn;
    }

    @JsonProperty("estimated_in")
    public String getEstimatedIn() {
        return estimatedIn;
    }

    @JsonProperty("estimated_in")
    public void setEstimatedIn(String estimatedIn) {
        this.estimatedIn = estimatedIn;
    }

    @JsonProperty("actual_in")
    public Object getActualIn() {
        return actualIn;
    }

    @JsonProperty("actual_in")
    public void setActualIn(Object actualIn) {
        this.actualIn = actualIn;
    }

    @JsonProperty("progress_percent")
    public Integer getProgressPercent() {
        return progressPercent;
    }

    @JsonProperty("progress_percent")
    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("aircraft_type")
    public String getAircraftType() {
        return aircraftType;
    }

    @JsonProperty("aircraft_type")
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    @JsonProperty("route_distance")
    public Integer getRouteDistance() {
        return routeDistance;
    }

    @JsonProperty("route_distance")
    public void setRouteDistance(Integer routeDistance) {
        this.routeDistance = routeDistance;
    }

    @JsonProperty("filed_airspeed")
    public Integer getFiledAirspeed() {
        return filedAirspeed;
    }

    @JsonProperty("filed_airspeed")
    public void setFiledAirspeed(Integer filedAirspeed) {
        this.filedAirspeed = filedAirspeed;
    }

    @JsonProperty("filed_altitude")
    public Integer getFiledAltitude() {
        return filedAltitude;
    }

    @JsonProperty("filed_altitude")
    public void setFiledAltitude(Integer filedAltitude) {
        this.filedAltitude = filedAltitude;
    }

    @JsonProperty("route")
    public String getRoute() {
        return route;
    }

    @JsonProperty("route")
    public void setRoute(String route) {
        this.route = route;
    }

    @JsonProperty("baggage_claim")
    public Object getBaggageClaim() {
        return baggageClaim;
    }

    @JsonProperty("baggage_claim")
    public void setBaggageClaim(Object baggageClaim) {
        this.baggageClaim = baggageClaim;
    }

    @JsonProperty("seats_cabin_business")
    public Object getSeatsCabinBusiness() {
        return seatsCabinBusiness;
    }

    @JsonProperty("seats_cabin_business")
    public void setSeatsCabinBusiness(Object seatsCabinBusiness) {
        this.seatsCabinBusiness = seatsCabinBusiness;
    }

    @JsonProperty("seats_cabin_coach")
    public Object getSeatsCabinCoach() {
        return seatsCabinCoach;
    }

    @JsonProperty("seats_cabin_coach")
    public void setSeatsCabinCoach(Object seatsCabinCoach) {
        this.seatsCabinCoach = seatsCabinCoach;
    }

    @JsonProperty("seats_cabin_first")
    public Integer getSeatsCabinFirst() {
        return seatsCabinFirst;
    }

    @JsonProperty("seats_cabin_first")
    public void setSeatsCabinFirst(Integer seatsCabinFirst) {
        this.seatsCabinFirst = seatsCabinFirst;
    }

    @JsonProperty("gate_origin")
    public String getGateOrigin() {
        return gateOrigin;
    }

    @JsonProperty("gate_origin")
    public void setGateOrigin(String gateOrigin) {
        this.gateOrigin = gateOrigin;
    }

    @JsonProperty("gate_destination")
    public String getGateDestination() {
        return gateDestination;
    }

    @JsonProperty("gate_destination")
    public void setGateDestination(String gateDestination) {
        this.gateDestination = gateDestination;
    }

    @JsonProperty("terminal_origin")
    public String getTerminalOrigin() {
        return terminalOrigin;
    }

    @JsonProperty("terminal_origin")
    public void setTerminalOrigin(String terminalOrigin) {
        this.terminalOrigin = terminalOrigin;
    }

    @JsonProperty("terminal_destination")
    public String getTerminalDestination() {
        return terminalDestination;
    }

    @JsonProperty("terminal_destination")
    public void setTerminalDestination(String terminalDestination) {
        this.terminalDestination = terminalDestination;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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

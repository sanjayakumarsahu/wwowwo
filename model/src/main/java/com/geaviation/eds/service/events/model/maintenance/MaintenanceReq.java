

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sanjaya.kumar.sahu
 *
 */
@XmlRootElement(name="MaintenanceReq")
public class MaintenanceReq {

	private List<String> esn;
	private String eventStartDate;
	private String eventEndDate;
	/**
	 * @return the esn
	 */
	@XmlElement(name = "esn")
	public List<String> getEsn() {
		return esn;
	}
	/**
	 * @param esn the esn to set
	 */
	public void setEsn(List<String> esn) {
		this.esn = esn;
	}
	/**
	 * @return the eventStartDate
	 */
	@XmlElement(name = "eventStartDate")
	public String getEventStartDate() {
		return eventStartDate;
	}
	/**
	 * @param eventStartDate the eventStartDate to set
	 */
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	/**
	 * @return the eventEndDate
	 */
	@XmlElement(name = "eventEndDate")
	public String getEventEndDate() {
		return eventEndDate;
	}
	/**
	 * @param eventEndDate the eventEndDate to set
	 */
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	
	
}


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MaintenanceData")
public class MaintenanceData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer maintActionSeqId;
	private String engineId ;
	private String maintenanceDate ;
	private String maintenanceSummary ;
	private String lastUpdateDate ;
	
	@XmlElement(name = "maintActionSeqId")
	public Integer getMaintActionSeqId() {
		return maintActionSeqId;
	}

	public void setMaintActionSeqId(Integer maintActionSeqId) {
		this.maintActionSeqId = maintActionSeqId;
	}

	@XmlElement(name = "engineId")
	public String getEngineId() {
		return engineId;
	}

	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}

	@XmlElement(name = "maintenanceDate")
	public String getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	@XmlElement(name = "maintenanceSummary")
	public String getMaintenanceSummary() {
		return maintenanceSummary;
	}

	public void setMaintenanceSummary(String maintenanceSummary) {
		this.maintenanceSummary = maintenanceSummary;
	}

	@XmlElement(name = "lastUpdateDate")
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public String formatMaintenanceDate() {
		return "DateTime";
	}
	
	public String formatLastUpdateDate() {
		return "DateTime";
	}

	
}

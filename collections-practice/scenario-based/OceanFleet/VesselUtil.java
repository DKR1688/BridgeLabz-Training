package OceanFleet;
import java.util.*;
public class VesselUtil {
	private List<Vessel> vesselList=new ArrayList<>();
	
	public List<Vessel> getVesselList(){
		return vesselList;
	}
	public void setVesselList(List<Vessel> vesselList) {
		this.vesselList=vesselList;
	}
	
	public void addVesselPerformance(Vessel vessel) {
		vesselList.add(vessel);
	}
	
	public Vessel getVesselById(String vesselId) {
		for(Vessel vessel: vesselList) {
			if(vessel.getVesselId().equals(vesselId)) {
				return vessel;
			}
		}
		return null;
	}
	
	public List<Vessel> getHighPerformanceVessels(){
		List<Vessel> ans=new ArrayList<>();
		double maxSpeed=0;
		
		for(Vessel vessel: vesselList) {
			if(vessel.getAverageSpeed()>maxSpeed) {
				maxSpeed=vessel.getAverageSpeed();
			}
		}
		
		for(Vessel vessel: vesselList) {
			if(vessel.getAverageSpeed()==maxSpeed) {
				ans.add(vessel);
			}
		}
		
		return ans;
	}
}

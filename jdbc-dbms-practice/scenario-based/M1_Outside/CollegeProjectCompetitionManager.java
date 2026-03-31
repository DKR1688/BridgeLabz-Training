package M1_Outside;

import java.util.*;
public class CollegeProjectCompetitionManager {
	public static List<ProjectTeam> teams=new ArrayList<>();
	
	public int registerTeam(String teamId, String section, String domain, String pName, int pScore) {
		for (ProjectTeam t : teams) {
            if (t.teamId.equals(teamId)) {
                return -1;
            }
        }
		teams.add(new ProjectTeam(teamId, section, domain, pName, pScore));
		return 1;
	}
	
	public int reviseScore(String teamId, int pScore) {
		for(ProjectTeam t:teams) {
			if(t.teamId.equals(teamId)) {
				t.pScore=pScore;
				System.out.println("REVISED "+teamId+" "+pScore);
				return 1;
			}
		}
		System.out.println("team is not available");
		return -1;
	}
	
	public List<ProjectTeam> filterByDomain(String domain){
		List<ProjectTeam> list=new ArrayList<>();
		for(ProjectTeam t:teams) {
			if(t.domain.equals(domain)) {
				list.add(t);
				t.display();
			}
		}
		
		if(list.isEmpty()) {
			System.out.println("Team is not available for the domain: "+domain);
		}
//		else {
//			for(ProjectTeam t:list) {
////				System.out.println(t);
//				t.display();
//			}
//		}
		return list;
	}
	
	public List<ProjectTeam> qualifyTeams(int cutoff){
		List<ProjectTeam> list=new ArrayList<>();
		for(ProjectTeam t:teams) {
			if(t.pScore>=cutoff) {
				list.add(t);
			}
		}
		
		if(list.isEmpty()) {
			System.out.println("No team qualified");
		}else {
			for(ProjectTeam t:list) {
//				System.out.println(t);
				t.display();
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		CollegeProjectCompetitionManager c=new CollegeProjectCompetitionManager();
		Scanner sc=new Scanner(System.in);
		int N=Integer.parseInt(sc.nextLine());
		
		List<String> inputs=new ArrayList<>();
		for(int i=0; i<N; i++) {
			inputs.add(sc.nextLine());
		}
		
		for(int i=0; i<N; i++) {
			String input=inputs.get(i);
			String[] parts=input.split(" ");
			
			String command=parts[0];
			switch(command) {
			case "REGISTER":
				c.registerTeam(parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
				break;
			case "REVISE":
				c.reviseScore(parts[1], Integer.parseInt(parts[2]));
				break;
			case "FILTERDOMAIN":
				c.filterByDomain(parts[1]);
				break;
			case "QUALIFY":
				c.qualifyTeams(Integer.parseInt(parts[1]));
				break;
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
		sc.close();
	}
}

class ProjectTeam{
	String teamId;
	String section;
	String domain;
	String pName;
	int pScore;
	
	public ProjectTeam(String teamId, String section, String domain, String pName, int pScore) {
		super();
		this.teamId = teamId;
		this.section = section;
		this.domain = domain;
		this.pName = pName;
		this.pScore = pScore;
	}
//	
//	@Override
//    public String toString() {
//        return teamId + " " + section + " " + domain + " " + pName + " " + pScore;
//    }
//	
	public void display() {
		System.out.println(teamId + " " + section + " " + domain + " " + pName + " " + pScore);
	}
}

import java.util.*;
public class JobResumeShortlistingSystem {
	List<Resume> resumes;
	Set<String> requiredSkills;
	
	JobResumeShortlistingSystem(Set<String> requiredSkills){
		this.resumes=new ArrayList<>();
		this.requiredSkills=requiredSkills;
	}
	
	public void addResume(Resume resume) throws InvalidResumeException {
		if(resume.getSkills()==null) {
			throw new InvalidResumeException("Resume must have skillss.");
		}
		resumes.add(resume);
		System.out.println("Resume added- "+resume.getName());
	}
	
	//calculaating skill match count
	public int getSkillMatchCount(Resume resume) {
		int count=0;
		for(String skill: resume.getSkills()) {
			if(requiredSkills.contains(skill)) {
				count++;
			}
		}
		return count;
	}
	
    //rank resumes based on skill matches
    public void rankResumes() {
        resumes.sort(new Comparator<Resume>() {
            @Override
            public int compare(Resume r1, Resume r2) {
                return Integer.compare(getSkillMatchCount(r2), getSkillMatchCount(r1));
            }
        });
    }
    
    public void showRankedResumes() {
        System.out.println("Ranked Resumes:");
        for (Resume r : resumes) {
            System.out.println(r.getName() + " | Matches- " + getSkillMatchCount(r));
        }
    }
    
	
    public static void main(String[] args) {
        Set<String> requiredSkills = new HashSet<>(Arrays.asList("Java", "SQL", "OOP"));

        JobResumeShortlistingSystem system = new JobResumeShortlistingSystem(requiredSkills);

        try {
            system.addResume(new Resume(1, "Deepak", new HashSet<>(Arrays.asList("Java", "Python", "SQL"))));
            system.addResume(new Resume(2, "Abhay", new HashSet<>(Arrays.asList("C++", "OOP"))));
            system.addResume(new Resume(3, "Rajput", new HashSet<>()));
        } catch (InvalidResumeException e) {
            System.out.println(e.getMessage());
        }

        system.rankResumes();
        system.showRankedResumes();
    }
}

class InvalidResumeException extends Exception {
    public InvalidResumeException(String message) {
        super(message);
    }
}

class Resume{
	private int id;
	private String name;
	private Set<String> skills;
	
	public Resume(int id, String name, Set<String> skills) {
        this.id=id;
        this.name=name;
        this.skills=skills;
    }
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Set<String> getSkills(){
		return skills;
	}
	
	@Override
	public String toString() {
		return "Resume {id - "+id+", name- "+name+", skills- "+skills+"}";
	}
}

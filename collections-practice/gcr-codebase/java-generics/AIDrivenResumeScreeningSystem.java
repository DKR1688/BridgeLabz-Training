import java.util.*;
public class AIDrivenResumeScreeningSystem {
	public static void main(String[] args) {
		Resume<SoftwareEngineer> software =new Resume<>(new SoftwareEngineer("Deepak"));
        Resume<DataScientist> data =new Resume<>(new DataScientist("Abahy"));
        Resume<ProductManager> product =new Resume<>(new ProductManager("Abhishek"));

        software.processResume(new SoftwareEngineer("Deepak"));
        data.processResume(new DataScientist("Abhay"));
        product.processResume(new ProductManager("Abhisheks"));
        System.out.println();
        
        List<JobRole> allResumes = new ArrayList<>();
        allResumes.add(new SoftwareEngineer("Alice"));
        allResumes.add(new DataScientist("Bob"));
        allResumes.add(new ProductManager("Charlie"));

        Screening.runPipeline(allResumes);
	}
}

//an abstract class JobRole for different roles
abstract class JobRole{
	String candidate;
	
	JobRole(String candidate){
		this.candidate =candidate;
	}
	
	abstract void checkResume();
}

class SoftwareEngineer extends JobRole {
    public SoftwareEngineer(String candidateName) {
        super(candidateName);
    }

    @Override
    public void checkResume() {
        System.out.println(candidate+" is screening for software engineer role.");
    }
}

class DataScientist extends JobRole {
    public DataScientist(String candidateName) {
        super(candidateName);
    }

    @Override
    public void checkResume() {
    	System.out.println(candidate+" is screening for data scientist role.");
    }
}

class ProductManager extends JobRole {
    public ProductManager(String candidateName) {
        super(candidateName);
    }

    @Override
    public void checkResume() {
        System.out.println(candidate+ " is screening for Product manager role.");
    }
}

//a generic class to process resumes dynamically.
class Resume<T extends JobRole>{
	T role;
	
	Resume(T role){
		this.role =role;
	}
	
	//generic method to process resume
    public <U extends JobRole> void processResume(U roles) {
        System.out.println("Processing resume...");
        roles.checkResume();
    }
}

//a wildcard method to handle multiple job roles in the screening pipeline.
class Screening{
	public static void runPipeline(List<? extends JobRole> resume) {
		System.out.println("Screening is started...");
		for(JobRole r: resume) {
			r.checkResume();
		}
	}
}
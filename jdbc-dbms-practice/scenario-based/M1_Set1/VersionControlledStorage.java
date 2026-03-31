package M1_Set1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VersionControlledStorage {
	Map<String, Map<String, FileVersion>> storage=new HashMap<>();
	Map<String, FileVersion> latestVersion=new HashMap<>();
	
	public void upload(String name, String version, int size) {
		storage.putIfAbsent(name, new HashMap<>());
		Map<String, FileVersion> versions=storage.get(name);
		
		if(!versions.containsKey(version)) {
			FileVersion fv=new FileVersion(version, size);
			versions.put(version, fv);
			latestVersion.put(name, fv);
		}
	}
	
	public void fetch(String name) {
		if(!storage.containsKey(name)) {
			System.out.println("File Not Found");
			return;
		}
		
		List<FileVersion> versions=new ArrayList<>(storage.get(name).values());
		versions.sort((a,b) -> {if(a.size!=b.size) return a.size-b.size;
			return a.version.compareTo(b.version);
		});
		
		for(FileVersion fv: versions) {
			System.out.println(name+" "+fv.version+" "+fv.size);
		}
	}
	
	public void latest(String name) {
		if(!latestVersion.containsKey(name)) {
			System.out.println("File Not Found");
			return;
		}
		FileVersion fv=latestVersion.get(name);
		System.out.println(name+" "+fv.version+" "+fv.size);
	}
	
	public void totalStorage(String name) {
		if(!storage.containsKey(name)) {
			System.out.println("File Not Found");
			return;
		}
		
		int sum=0;
		sum=storage.get(name).values().stream().mapToInt(fv -> fv.size).sum();
		System.out.println(name+" "+sum);
	}
	
	public static void main(String[] args) {
		VersionControlledStorage v=new VersionControlledStorage();
		Scanner sc=new Scanner(System.in);
		int N=sc.nextInt();
		sc.nextLine();
		
		List<String> list=new ArrayList<>();
		for(int i=0; i<N; i++) {
			list.add(sc.nextLine());
		}
		
		for(int i=0; i<N; i++) {
			String input=list.get(i);
			String[] parts=input.split(" ");
			
			if(parts[0].equals("UPLOAD")) {
				v.upload(parts[1], parts[2], Integer.parseInt(parts[3]));
			}else if(parts[0].equals("FETCH")) {
				v.fetch(parts[1]);
			}else if(parts[0].equals("LATEST")) {
				v.latest(parts[1]);
			}else if(parts[0].equals("TOTAL_STORAGE")) {
				v.totalStorage(parts[1]);
			}
		}
		sc.close();
	}
}

class FileVersion{
	String version;
	int size;
	
	FileVersion(String version, int size){
		this.version=version;
		this.size=size;
	}
}

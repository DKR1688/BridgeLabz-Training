package InsurancePolicyManagementSystem;
import java.time.LocalDate;
import java.util.*;
public class PolicyManagement {
	Set<Policy> hashSet=new HashSet<>();
	Set<Policy> linkedHash=new LinkedHashSet<>();
	Set<Policy> treeSet=new TreeSet<>();
	
	//method to add policies
	public void addPolicy(Policy policy) {
		hashSet.add(policy);
		linkedHash.add(policy);
		treeSet.add(policy);
	}
	
	//method to retrieve unique all policies
	public void retrievePolicy() {
		System.out.println("All policies in hashSet- "+"\n"+hashSet);
		System.out.println("All policies in linkedHashSet- "+"\n"+linkedHash);
		System.out.println("All policies in treeSet- "+"\n"+treeSet);
	}
	
	
	//policy expiring soon within 30 days and treeSet to maintain expiry date
	public void expiringSoon() {
		LocalDate today=LocalDate.now();
		LocalDate expiry= today.plusDays(30);
		System.out.println();
		System.out.println("Policiy expiring soon- ");
		for(Policy policy: treeSet) {
			if(!policy.expiryDate.isAfter(expiry)) {
				System.out.println(policy);
			}
		}
	}
	
	//policy with coverage type and hashSet maintain types
	public void coverageType(String type) {
		System.out.println();
		System.out.println("Policy with coverage type- ");
		for(Policy policy:hashSet) {
			if(policy.coverageType.equalsIgnoreCase(type)) {
				System.out.println(policy);
			}
		}
	}
	
	//checking policy duplicates based on policy number
    public void checkDuplicates(List<Policy> policies) {
        Set<String> seen=new HashSet<>();
        Set<String> duplicates=new HashSet<>();
        for (Policy policy: policies) {
            if (!seen.add(policy.number)) {
                duplicates.add(policy.number);
            }
        }
        System.out.println();
        System.out.println("Duplicate policy numbers are- "+duplicates);
    }
    
    //method to compare performance based on adding removing and searching
    public void performance() {
    	//number of test policies to compare
    	int n=100000;
        List<Policy> testPolicies=new ArrayList<>();
        for (int i=0; i<n; i++) {
            testPolicies.add(new Policy("Policy4" +i, "Holder" +i,
                    LocalDate.now().plusDays(i % 365), "Auto", 1000 + i));
        }

        //hash set
        System.out.println();
        long start=System.nanoTime();
        Set<Policy> hs = new HashSet<>(testPolicies);
        long end=System.nanoTime();
        System.out.println("HashSet add time- " + (end-start) +" ns");

        start=System.nanoTime();
        hs.contains(testPolicies.get(n/2));
        end=System.nanoTime();
        System.out.println("HashSet search time- " + (end-start) +" ns");

        start=System.nanoTime();
        hs.remove(testPolicies.get(n/2));
        end=System.nanoTime();
        System.out.println("HashSet remove time- " + (end-start) +" ns");

        //linked hash set
        System.out.println();
        start=System.nanoTime();
        Set<Policy> lhs = new LinkedHashSet<>(testPolicies);
        end=System.nanoTime();
        System.out.println("LinkedHashSet add time- " + (end-start) +" ns");

        start = System.nanoTime();
        lhs.contains(testPolicies.get(n/2));
        end = System.nanoTime();
        System.out.println("LinkedHashSet search time- " + (end-start) +" ns");

        start=System.nanoTime();
        lhs.remove(testPolicies.get(n/2));
        end=System.nanoTime();
        System.out.println("LinkedHashSet remove time- " + (end-start) +" ns");

        //tree set
        System.out.println();
        start=System.nanoTime();
        Set<Policy> ts = new TreeSet<>(testPolicies);
        end=System.nanoTime();
        System.out.println("TreeSet add time- " + (end-start) +" ns");

        start=System.nanoTime();
        ts.contains(testPolicies.get(n/2));
        end=System.nanoTime();
        System.out.println("TreeSet search time- " + (end-start) +" ns");

        start=System.nanoTime();
        ts.remove(testPolicies.get(n/2));
        end=System.nanoTime();
        System.out.println("TreeSet remove time- " + (end-start) +" ns");

    }
}

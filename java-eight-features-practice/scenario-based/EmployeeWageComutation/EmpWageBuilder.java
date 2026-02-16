package EmployeeWageComutation;
//UC-10  managing multiple companies

//UC-12 refactoring empWageBuilder to use ArrayList
import java.util.*;
public class EmpWageBuilder implements IComputeEmpWage {
//	CompanyEmpWage[] companyEmpWageArray;
//	int numOfCompany = 0;
//
//	public EmpWageBuilder(int n) {
//		companyEmpWageArray = new CompanyEmpWage[n];
//	}

	List<CompanyEmpWage> companyEmpWageList;
    Map<String, CompanyEmpWage> companyToEmpWageMap;
    public EmpWageBuilder() {
        companyEmpWageList = new ArrayList<>();
        companyToEmpWageMap = new HashMap<>();
    }

    @Override
	public void addCompanyEmpWage(String company, int wagePerHour, int numWorkingDays, int maxHoursPerMonth) {
		//companyEmpWageArray[numOfCompany++] = new CompanyEmpWage(company, wagePerHour, numWorkingDays, maxHoursPerMonth);
    	CompanyEmpWage companyEmpWage = new CompanyEmpWage(company, wagePerHour, numWorkingDays, maxHoursPerMonth);
        companyEmpWageList.add(companyEmpWage);
        companyToEmpWageMap.put(company, companyEmpWage);
	}

    @Override
	public void computeEmpWage() {
//		for (int i = 0; i < numOfCompany; i++) {
//			companyEmpWageArray[i].setTotalEmpWage(this.computeEmpWage(companyEmpWageArray[i]));
//			System.out.println(companyEmpWageArray[i]);
//		}
		for (CompanyEmpWage companyEmpWage : companyEmpWageList) {
            companyEmpWage.setTotalEmpWage(this.computeEmpWage(companyEmpWage));
            System.out.println(companyEmpWage);
        }
	}

	private int computeEmpWage(CompanyEmpWage companyEmpWage) {
		int totalEmpHrs = 0;
		int totalWorkingDays = 0;
		while (totalEmpHrs <= companyEmpWage.maxHoursPerMonth && totalWorkingDays < companyEmpWage.numWorkingDays) {
			totalWorkingDays++;
			int empCheck = (int) Math.floor(Math.random() * 3);
			int empHrs = 0;
			switch (empCheck) {
			case 1:
				empHrs = 4;
				break;
			case 2:
				empHrs = 8;
				break;
			default:
				empHrs = 0;
			}
			totalEmpHrs += empHrs;
		}
		return totalEmpHrs * companyEmpWage.wagePerHour;
	}
	
	@Override
    public int getTotalWage(String company) {
        return companyToEmpWageMap.get(company).totalEmpWage;
    }
}

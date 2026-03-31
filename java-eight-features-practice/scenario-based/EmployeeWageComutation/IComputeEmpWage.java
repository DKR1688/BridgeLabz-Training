package EmployeeWageComutation;
//UC-11 interface to emp wage
public interface IComputeEmpWage {
	void addCompanyEmpWage(String company, int wagePerHour, int numWorkingDays, int maxHoursPerMonth);
	void computeEmpWage();
	int getTotalWage(String company);
}

package EmployeeWageComutation;
//UC-9 total wage for each company
class CompanyEmpWage {
    String company;
    int wagePerHour;
    int numWorkingDays;
    int maxHoursPerMonth;
    int totalEmpWage;

    CompanyEmpWage(String company, int wagePerHour, int numWorkingDays, int maxHoursPerMonth) {
        this.company = company;
        this.wagePerHour = wagePerHour;
        this.numWorkingDays = numWorkingDays;
        this.maxHoursPerMonth = maxHoursPerMonth;
    }

    public void setTotalEmpWage(int totalEmpWage) {
        this.totalEmpWage=totalEmpWage;
    }

    @Override
    public String toString() {
        return "Total employee wage for company- "+company + " is " +totalEmpWage;
    }
}
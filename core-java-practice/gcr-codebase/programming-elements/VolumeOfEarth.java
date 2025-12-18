// a Program to compute the volume of Earth in km^3 and miles^3

public class VolumeOfEarth {
    public static void main(String[] args) {
        int radiusOfEarthInKm = 6378;

        // here we are calculating volume of earth in cubic kilometers
        double VolumeOfEarth = (4* 3.14 * radiusOfEarthInKm * radiusOfEarthInKm * radiusOfEarthInKm)/3;

        System.out.println("The volume of earth in cubic kilometers is "+ VolumeOfEarth+ " and in cubic miles is "+ VolumeOfEarth * 0.621371 * 0.621371 * 0.621371);
    }
}

import java.util.Scanner;
import java.lang.Math;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class CannonEquations {
    Double vi;
    Double m;
    Double x;
    Double tempC;
    Double tempK;
    Double rH;
    Double hpa;
    Double pa;
    Double dew;
    Double dCo;
    Double crossA;

    // if a variable is missing input null
    public CannonEquations(
            Double velocityinitial,
            Double mass,
            Double deltax,
            Double temperature,
            Double relativeHumidity,
            Double airPressure,
            Double dragCoeffecient,
            Double crossArea) {

        vi = velocityinitial;
        m = mass;
        x = deltax;
        tempC = temperature;
        tempK = temperature + 273.15;
        rH = relativeHumidity;
        hpa = airPressure;
        pa = airPressure * 100;
        dew = (Math.log(rH / 100)) + ((17.62 * tempC) / (243.12 + tempC));
        dCo = dragCoeffecient;
        crossA = crossArea;
    }

    public Double missingAirDensity() {
        Double p1 = (6.1078 * 10.0) * ((7.5 * tempC) / (tempC + 237.3));
        Double pv = p1 * rH;
        Double pd = hpa - pv;
        return ((pd / (287.058 * tempK)) + (pv / (461.495 * tempK)));
    }

    public Double missingTerminalVelocity(Double aD) {
        Double e = (2 * m * 9.81) / (aD * crossA * dCo);
        return Math.sqrt(e);
    }

    public Double missingDragForce(Double aD) {
        return dCo * crossA * ((aD * (vi * vi)) / 2);
    }

    public Double missingVelocityFinal(Double time, Double a) {
        return vi + (a * time);
    }

    public Double missingAccelerationY(Double aD, Double tV) {
        return -9.81 - ((dCo * crossA * aD * tV * tV) / (2 * m));
    }

    public Double missingU(Double u, Double tV, Double time, Double vix) {
        u = ((tV * tV) * vix) / ((tV * tV) + (9.81 * vix * time));
        return u;
    }

    public Double missingAccelerationX(Double u, Double aD) {
        return ((-1 * dCo) * crossA * aD * (u * u)) / (2 * m);
    }

    public Double missingviy(Double theta) {
        Double thetar = Math.toRadians(theta);
        Double thetas = Math.sin(thetar);
        return thetas * vi;
    }

    public Double missingvix(Double theta) {
        Double thetar = Math.toRadians(theta);
        Double thetac = Math.cos(thetar);
        Double thetam = Math.toDegrees(thetac);
        return thetac * vi;
    }

    public Double missingTime(Double tV, Double viy) {
        Double te = Math.atan(viy / tV);
        Double ter = Math.toDegrees(te);
        return (tV / 9.81) * ter;
    }

    public Double missingx(Double vix, Double time, Double tV) {
        Double rea = ((tV * tV) + 9.81 * vix * time) / (tV * tV);
        Double real = Math.log(rea);
        x = ((tV * tV) / 9.81) * real;
        return x;
    }

    public Double missingTheta() {
        Double thetr = (Math.asin((9.81 * x) / (vi * vi)));
        return 0.5 * (Math.toDegrees(thetr));
    }

    public static void writeFile(String real) {
        try {
            File data = new File("result.txt");
            Scanner reader = new Scanner(data);
            String str = "";
            while (reader.hasNextLine())
                str += reader.nextLine() + "\n";
            FileWriter f = new FileWriter("result.txt");
            f.write(str);
            f.write("\n" + "New Run:");
            f.write(real);
            f.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("failed to write to result.txt");
        }
    }

}
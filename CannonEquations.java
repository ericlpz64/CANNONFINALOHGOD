import java.util.Scanner;
import java.lang.Math;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class CannonEquations {
    Double vi;
    Double m;
    Double x;
    Double theta;
    Double tempC;
    Double tempK;
    Double rH;
    Double hpa;
    Double pa;
    Double dew;
    Double dCo;
    Double crossA;
    Double refa;

    // if a variable is missing input null
    public CannonEquations(
            Double velocityinitial,
            Double mass,
            Double deltax,
            Double thetaAngle,
            Double temperature,
            Double relativeHumidity,
            Double airPressure,
            Double dragCoeffecient,
            Double crossArea,
            Double referenceArea) {

        vi = velocityinitial;
        m = mass;
        x = deltax;
        theta = thetaAngle;
        tempC = temperature;
        tempK = temperature + 273.15;
        rH = relativeHumidity;
        hpa = airPressure;
        pa = airPressure * 100;
        dew = (Math.log(rH / 100)) + ((17.62 * tempC) / (243.12 + tempC));
        dCo = dragCoeffecient;
        crossA = crossArea;
        refA = referenceArea;
    }

    public Double misssingAirDensity(Double aD) {
        Double p1 = (6.1078 * 10) * ((7.5 * tempC) / (tempC + 237.3));
        Double pv = p1 * rH;
        Double pd = hpa - pv;
        aD = (pd / (287.058 * tempK)) + (pv / (461.495 * tempK));
        return aD;
    }

    public Double missingTerminalVelocity(Double crossA, Double tV, Double aD) {
        double e = (2 * m * -9.81) / (aD * crossA * dCo);
        tV = Math.sqrt(a);
        return tV;
    }

    public Double missingDragForce(Double aD, Double dF) {
        dF = dCo * refA * ((aD * (vi * vi)) / 2);
        return dF;
    }

    public Double missingVelocityFinal(Double vf, Double time) {
        vf = vi + (a * time);
        return vf;
    }

    public Double missingAccelerationY(Double aD, Double tV, Double aya) {
        aya = -9.81 - ((dCo * crossA * aD * tV * tV) / (2 * m));
        return aya;
    }

    public Double missingU(Double u, Double tV, Double time, Double vix) {
        u = ((tV * tV) * vix) / ((tV * tV) + (9.81 * vix * time));
        return u;
    }

    public Double missingaccelerationX(Double u, Double axa, Double crossA, Double aD) {
        axa = ((-1 * dCo) * crossA * aD * (u * u)) / (2 * m);
        return axa;
    }

    public void missingvy(Double viy) {
        Double thetar = Math.toRadians(theta);
        Double thetas = Math.sin(thetar);
        viy = thetas * vi;
    }

    public void missingvx(Double vix) {
        Double thetar = Math.toRadians(theta);
        Double thetac = Math.cos(thetar);
        vix = thetac * vi;
    }

    public Double missingTime(Double tV, Double time) {
        Double te = Math.atan(vi / tV);
        time = (tV / 9.81) * te;
        return time;
    }

    public Double missingx(Double vix, Double time, Double tV) {
        Double rea = ((tV * tV) + 9.81 * vix * time) / (tV * tV);
        Double real = Math.log(rea);
        x = ((tV * tV) / 9.81) * real;
        return x;
    }

    public Double missingTheta() {
        Double thetr = (Math.asin((9.81 * x) / (vi * vi)));
        theta = 0.5 * (Math.toDegrees(thetr));
        return theta;
    }

    public String toString(Double varReal) {
        if (varReal == null) {
            return "unknown";
        }
        if (varReal == vi) {
            return varReal + "㎧";
        }
        if (varReal == vf) {
            return varReal + "㎧";
        }
        if (varReal == a) {
            return varReal + "㎧²";
        }
        if (varReal == m) {
            return varReal + "kg";
        }
        if (varReal == x) {
            return varReal + "m";
        }
        if (varReal == time) {
            return varReal + "s";
        }
        if (varReal == theta) {
            return varReal + "°";
        }
        if (varReal == aD) {
            return varReal + "kg/m³";
        } else
            return "woops";
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
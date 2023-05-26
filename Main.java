import dev.rablet.bme280.model.BME280Data;
import java.util.Scanner;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        BME280Data bme280 = new BME280Data(20.0, 20.0, 1480.00);
        Double vi = 27.0;
        Double m = 0.007;
        Double deltax = null;
        Double xTest = null;
        Double theta = null;
        Double temp = bme280.getTemperature();;
        Double rH = bme280.getHumidity();
        Double hPa = bme280.getPressure();
        Double dCo = 0.67;
        Double crossA = 0.1963495408;
        Double tV = null;
        Double time = null;
        Double vix = null;
        Double viy = null;
        Double aD = null;
        Double aya = null;
        Double gloop = 2.0;

        Scanner sc = new Scanner(System.in);

        if (args.length >= 1 && !args[0].equals("test")) {
            System.out.print("Input Initial Velocity: ");
            vi = isNull(sc.next());
            System.out.print("Input Mass in grams: ");
            m = isNull(sc.next());
            if (m == null) {
                System.out.println("please measure the mass of this item in kg");
                if (m != null) {
                    m /= 1000;
                }
            }
            /*System.out.print("Input Temperature in Celcius: ");
            temp = isNull(sc.next());
            System.out.print("Input Relative Humidity: ");
            rH = isNull(sc.next());
            System.out.print("input Air Pressure in hPa: ");
            hPa = isNull(sc.next());
            */
            System.out.print("Input Drag Coeffecient: ");
            dCo = isNull(sc.next());
            System.out.print("Input Cross-Sectional Area: ");
            crossA = isNull(sc.next());
            System.out.print("Input 1 to solve for angle or Input 2 to solve for distance: ");
            gloop = isNull(sc.next());
        }

        if (gloop == 1.0) {
            System.out.print("Input Distance/Δx: ");
            deltax = isNull(sc.next());
        }
        if (gloop == 2.0) {
            System.out.print("Input Angle/θ: ");
            theta = isNull(sc.next());
        }

        CannonEquations Teracotta = new CannonEquations(
                vi, m, deltax, temp, rH, hPa, dCo, crossA);
    //vi, m, delatx, temp, rH, hPa, dCo, crossA

        while (gloop == 1.0) {
            aD = Teracotta.missingAirDensity();
            tV = Teracotta.missingTerminalVelocity(aD);
            
            vix = Teracotta.missingvix(19.9);
            viy = Teracotta.missingviy(19.9);
            time = Teracotta.missingTime(tV, viy);
            xTest = Teracotta.missingx(vix, time, tV);

            System.out.println("xTest: " + xTest);
            if (xTest < deltax) {
                System.out.println("This Cannon is not powerful enough to lauch this projectile to the desired range");
                break;
            }
            vix = Teracotta.missingvix(0.1);
            viy = Teracotta.missingviy(0.1);
            time = Teracotta.missingTime(tV, viy);
            xTest = Teracotta.missingx(vix, time, tV);
            System.out.println("test 2");
            if (xTest > deltax) {
                System.out.println("This Cannon is too powerful to lauch this projectile to the desired range");
                break;
            }

            vix = Teracotta.missingvix(89.9);
            viy = Teracotta.missingviy(89.9);
            time = Teracotta.missingTime(tV, viy);
            xTest = Teracotta.missingx(vix, time, tV);
            System.out.println("test 3");
            if (xTest > deltax) {
                System.out.println("This Cannon is too powerful to lauch this projectile to the desired range");
                break;
            }

            System.out.println("test 4");
            for (double i = 0.1; i < 90.0; i += 0.1) {
                theta = i;
                vix = Teracotta.missingvix(theta);
                viy = Teracotta.missingviy(theta);
                time = Teracotta.missingTime(tV, viy);
                xTest = Teracotta.missingx(vix, time, tV);
                System.out.println(xTest + ", " + (deltax - 0.34));
                if ((xTest >= deltax - 0.34) && (xTest <= deltax + 0.34)) {
                    System.out.println("found missing θ: " + theta + "°");
                    break;
                }

            }
            System.out.println("test 5");
            break;
        }
            

        if (gloop == 2.0) {
            aD = Teracotta.missingAirDensity();
            System.out.println(aD);
            vix = Teracotta.missingvix(theta);
            viy = Teracotta.missingviy(theta);
            tV = Teracotta.missingTerminalVelocity(aD);
            System.out.println(tV);
            time = Teracotta.missingTime(tV, viy);
            System.out.println(time);
            Teracotta.missingx(vix, time, tV);
            System.out.println("found missing Δx: " + Teracotta.x + "m");
        }

    }

    public static Double isNull(String value) {
        if (value.equals("null"))
            return null;

        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
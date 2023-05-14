import java.util.Scanner;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        Double viInput;
        Double mInput;
        Double deltaxInput = null;
        Double thetaInput = null;
        Double tempInput;
        Double rHInput;
        Double aPInput;
        Double dCo;
        Double crossAInput;
        Double refA;
        Double tV;
        Double time;
        Double vix;
        Double aD;

        Scanner sc = new Scanner(System.in);

        System.out.print("Input Initial Velocity: ");
        viInput = isNull(sc.next());
        System.out.print("Input Mass: ");
        mInput = isNull(sc.next());
        if (mInput == null) {
            System.out.println("please measure the mass of this item in kg");
        }
        System.out.print("Input Temperature: ");
        tempInput = isNull(sc.next());
        System.out.print("Input Relative Humidity: ");
        rHInput = isNull(sc.next());
        System.out.print("Input Air Pressure: ");
        aPInput = isNull(sc.next());
        System.out.print("Input Drag Coeffecient: ");
        dCoInput = isNull(sc.next());
        System.out.print("Input Cross-Sectional Area: ");
        crossAInput = isNull(sc.next());
        System.out.print("Input Reference Area: ");
        refAInput = isNull(sc.next());
        System.out.print("Input 1 to solve for angle or input 2 to solve for distance");
        if (sc.next() == "1") {
            System.out.println("Input Distance/Δx: ");
            deltaxInput = isNull(sc.next());
        }
        if (sc.next() == "2") {
            System.out.print("Input Angle/θ: ");
            thetaInput = isNull(sc.next());
        }
        
        CannonEquations Teracotta = new CannonEquations(
                viInput, mInput, deltaxInput , thetaInput, tempInput, rHInput, aPInput, dCo, crossAInput, refA);
        // Teracotta.CannonEquations(50.0, 45.0, null, null, 37.2, 1.4, 20, 30, aP
        // 15.0);//vi, vf, a, m, delatx, time, theta, temp, rH, aP

        if (Teracotta.theta == null &&
                Teracotta.x != null) {
            Teracotta.missingaya(aD, tV, aya);
            Teracotta.missingTheta();
            System.out.println("found missing θ: " + Teracotta.toString(Teracotta.theta));
        }

        if (Teracotta.theta != null &&
                Teracotta.x == null) {
            Teracotta.missingAirDensity(aD);
            Teracotta.missingvx(vix);
            Teracotta.missingTime(tV, time);
            Teracotta.missingTerminalVelocity(crossAInput, tV, aD);
            Teracotta.missingx(vix, time, tV);
            System.out.println("found missing Δx: " + Teracotta.toString(Teracotta.x));
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
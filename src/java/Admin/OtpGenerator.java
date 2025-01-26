
package Admin;


import java.util.Random;

public class OtpGenerator {
    public String generateOTP() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }
}

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    private static String encodeBASE64String(String startCondition){
        return Base64.getEncoder().encodeToString(startCondition.getBytes(StandardCharsets.US_ASCII));
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        System.out.println(encodeBASE64String(string));
    }
}

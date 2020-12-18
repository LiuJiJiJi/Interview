
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        System.out.println("==================Parking Lot Start=====================");
        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext()) {
                String inputValue = input.next();
                System.out.println("Input:" + inputValue);
            }
        } while (true);
    }

}

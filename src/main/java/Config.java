import java.io.*;
import java.util.Scanner;

public class Config {

    private String adress;
    private Integer delay;

    public Config() {
    }

    public Config(String adress, Integer value) {
        this.adress = adress;
        this.delay = value;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer value) {
        this.delay = value;
    }

    public void load() throws IOException {

        try {
            File myObj = new File(".\\src\\main\\java\\Config.txt");
            Scanner myReader = new Scanner(myObj);

            String adress = myReader.nextLine();
            String delay = myReader.nextLine();

            adress = adress.substring(7);
            delay = delay.substring(6);

            this.adress = adress;
            this.delay = Integer.parseInt(delay);

            System.out.println(this.adress);
            System.out.println(this.delay);

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

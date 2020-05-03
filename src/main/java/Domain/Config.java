package Domain;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

public class Config {

    private String adress;
    private Integer id;
    private Integer timeout;
    private Integer delay;

    public Config() {
    }

    public Config(String adress, Integer value) {
        this.adress = adress;
        this.timeout = value;
    }

    public Config(String adress, Integer id, Integer timeout, Integer delay) {
        this.adress = adress;
        this.id = id;
        this.timeout = timeout;
        this.delay = delay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getDelay() {
        return timeout;
    }

    public void setDelay(Integer value) {
        this.timeout = value;
    }

    private static String init () throws IOException {
        String CONFIG_FOLDER = "config/";
        Path currentRelativePath = java.nio.file.Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().normalize().toString()+"/";
        String configPath = absolutePath + CONFIG_FOLDER;
        try (InputStream input = new FileInputStream(configPath + "Config.txt")) {
            return configPath + "Config.txt";
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            throw new IOException("Fail to load a config!");
        }
    }

    public void load() throws IOException {

        try {

            System.out.println(init());

            File myObj = new File(init());

            Scanner myReader = new Scanner(myObj);

            String adress = myReader.nextLine();
            String id = myReader.nextLine();
            String delay = myReader.nextLine();
            String timeout = myReader.nextLine();

            adress = adress.substring(7);
            id = id.substring(3);
            delay = delay.substring(6);
            timeout = timeout.substring(8);

            this.adress = adress;
            this.id = Integer.parseInt(id);
            this.delay = Integer.parseInt(delay);
            this.timeout = Integer.parseInt(timeout);

            System.out.println(this.adress);
            System.out.println(this.id);
            System.out.println(this.delay);
            System.out.println(this.timeout);

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

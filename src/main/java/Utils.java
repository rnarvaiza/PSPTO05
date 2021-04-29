import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String RESOURCES_PATH = "src/main/resources/";

    /**
     * Solicita el nombre del usuario que va a utilizar la aplicación.
     * El login tiene una longitud de 10 caracteres y está compuesto únicamente por 8 letras minúsculas primero y dos caracteres al final.
     * @param s
     * @return
     */
    public static boolean loginValidator(String s){
        return s.matches("([a-z]{8}(([\\d]|[\\W]){2}))");
    }


    /**
     * Solicita la password del usuario: debe tener 8 caracteres de longitud, como mínimo, y contener una mayúscula, una minúscula y un dígito, al menos.
     */
    public static boolean passworValidator(String password){

        if(password.length()>=8){
            Pattern minus= Pattern.compile("[a-z]");
            Pattern mayus= Pattern.compile("[A-Z]");
            Pattern numero= Pattern.compile("[0-9]");
            Matcher hasMinus = minus.matcher(password);
            Matcher hasMayus = mayus.matcher(password);
            Matcher hasNumero = numero.matcher(password);
            return hasMinus.find() && hasNumero.find() && hasMayus.find();
        }
        else
            return false;
    }

    /**
     * Solicita el email del usuario: se debe comprobar que es correcto.
     * @param mail
     * @return
     */
    public static boolean mailValidator (String mail){
        return mail.matches("[-\\w\\.]+@\\w+\\.\\w+");
    }

    /**
     * Solicita al usuario el nombre del fichero en el que se guardarán los datos pedidos (nombre, password y email).
     * El nombre del fichero es, como máximo, de 8 caracteres y tiene una extensión de 3 caracteres.
     * @param filename
     * @return
     */
    public static boolean fileValidator(String filename){
        return filename.matches("([a-z]{1,8}\\.[a-z]{3})");
    }

    /**
     * StringBuilder to clearify main menu.
     * @param login
     * @param password
     * @param mail
     * @return
     */

    public static String buildString(String login, String password, String mail){
        return login + "\n" + password + "\n" + mail;
    }

    /**
     * Function designed to write a file with a given content.
     * @param fichero
     * @param cadena
     * @return
     * @throws IOException
     */

    public static boolean writeFile(File fichero, String cadena) throws IOException {

        boolean correcto = false;

        try
                (FileOutputStream fos = new FileOutputStream(fichero);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw))
        {
            bw.write(cadena);
            correcto = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return correcto;
    }

    /**
     * Method designed to create a file and get ready to write on it.
     * @param fileName
     * @param login
     * @param password
     * @param mail
     * @return
     * @throws IOException
     */

    public static boolean createFile(String fileName, String login, String password, String mail) throws IOException {
        File file = new File(RESOURCES_PATH,fileName);
        file.getParentFile().mkdirs();
        if(!file.exists()){
            file.createNewFile();
        }
        return writeFile(file, buildString(login, password, mail));
    }

    /**
     * Function designed to build a file and read it with a given name.
     * @param filename
     * @return
     */
    public static String leerFichero(String filename) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File miFichero= new File(RESOURCES_PATH, filename);
        return leer(miFichero);

    }

    /**
     * Function designed to read a given file.
     * @param file
     * @return
     */
    private static String leer(File file) {
        String linea;
        StringBuilder cadena = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis));
             )
        {
            while((linea= br.readLine()) != null){
                cadena.append(linea).append('\n');
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cadena.toString();
    }

    /**
     * Function to get log cleared before execution.
     * @param fileLocation
     */
    public static void flushLog(String fileLocation){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation))) {
            bw.write("");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

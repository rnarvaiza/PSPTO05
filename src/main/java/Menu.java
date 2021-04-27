import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Menu {

    public void initializeMenu(){

        String login = null;
        String password = null;
        String mail = null;
        String filename = null;
        String resume = null;
        Scanner sc = new Scanner(System.in);
        int counter;

        Logger logger = Logger.getLogger("RegExLog");
        FileHandler fh = null;
        SimpleFormatter sf = null;
        try {
            sf = new SimpleFormatter();
            Utils.flushLog(Utils.RESOURCES_PATH+"regex.log");
            fh = new FileHandler(Utils.RESOURCES_PATH+"regex.log", true);
            logger.addHandler(fh);
            logger.setUseParentHandlers(true);
            logger.setLevel(Level.FINE);
            fh.setFormatter(sf);

            System.out.println("Sistema de validación de seguridad. Por favor, siga las instrucciones que le aparezcan en pantalla.");

            logger.log(Level.FINE,
                    String.valueOf(Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of("Europe/Madrid"))) +
                            "Inicio de log");
            counter = 0;

            do{

                System.out.println("Por favor, introduzca un nombre de usuario con 8 caracteres y 2 números al final");
                login = sc.nextLine();
                if(counter>0){
                    logger.log(Level.FINE, "Intento erroneo de introducir nombre de usuario nº" + counter);
                }
                counter ++;
            }while (!Utils.loginValidator(login));
            logger.log(Level.FINE, "Intento satisfactorio en la introdución del nombre de usuario.");
            System.out.println("Login correcto\n");
            counter = 0;

            do{

                System.out.println("A continuación, introduzca una contraseña, con al menos 8 caracteres y al menos una mayúscula, una minúscula y un dígito");
                password = sc.nextLine();
                if(counter>0){
                    logger.log(Level.FINE, "Intento erroneo de introducir password nº" + counter);
                }
                counter ++;
            }while (!Utils.passworValidator(password));
            logger.log(Level.FINE, "Intento satisfactorio en la introdución de la password.");
            System.out.println("Password correcta\n");
            counter = 0;

            do{
                System.out.println("Es necesario que introduzcas un email: ");
                mail = sc.nextLine();
                if(counter>0){
                    logger.log(Level.FINE, "Intento erroneo de introducir mail nº" + counter);
                }
                counter ++;
            }while (!Utils.mailValidator(mail));
            logger.log(Level.FINE, "Intento satisfactorio en la introdución del email.");
            System.out.println("Email correcto\n");
            counter = 0;

            do{
                System.out.println("Por favor, introduce el nombre del fichero, máximo 8 caracteres con una extensión de 3 caracteres");
                filename = sc.nextLine();
                if(counter>0){
                    logger.log(Level.FINE, "Intento erroneo de introducir nombre de archivo nº" + counter);
                }
                counter ++;
            }while(!Utils.fileValidator(filename));
            logger.log(Level.FINE, "Intento satisfactorio en la introdución del nombre de archivo.");
            System.out.println("Nombre de archivo correcto\n");
            counter = 0;

            if(!Utils.createFile(filename, login, password, mail)){
                logger.log(Level.FINE, "ERROR: imposible crear el archivo contenedor de información.");
            }
            else{
                logger.log(Level.FINE, "Creado el archivo contenedor de información.");
            }

            resume = Utils.leerFichero(filename);
            if(!resume.isEmpty()){
                System.out.println(resume);
                logger.log(Level.FINE, "Leido el archivo contenedor de información.");
            }
            else{
                logger.log(Level.FINE, "ERROR: imposible leer el archivo contenedor de información.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fh != null){
                fh.close();
            }
        }




/*
        try {
            Utils.createFile("archivo.txt", "asdasd", "asdasfghfghfghd", "qweqweqweqw");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

 */
        /*
        String resume = null;
        resume = Utils.leerFichero("asd.txt");
        if(!resume.isEmpty()){
            //logger.log(Level.FINE, "ERROR: imposible leer el archivo contenedor de información.");
        }
        else{
            System.out.println(resume);
            //logger.log(Level.FINE, "Leido el archivo contenedor de información.");
        }

         */
    }


}

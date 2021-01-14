

import java.io.*;
import java.lang.*;
import java.util.*;

public class Esercizio3{
    public static void main(String[] args) {

        double c;
        int n;
        int boxNum = 0;
        String input = "";

        if (args.length != 1) {
            System.err.println("Utilizzo: java -cp . Esercizio3 <inputFile.in>");
            System.exit(1);

        }else {input = args[0];}

        try {
            Scanner scf = new Scanner(new File(input));
            scf.useLocale(Locale.US);

            c = scf.nextDouble();
            n = scf.nextInt();

            double[] obj = new double[n];

            for(int i = 0; i < n; i++) {
                obj[i] = scf.nextDouble();
                if (obj[i] > c){
                    System.err.println("Un oggetto ha peso maggiore della portata massima delle scatole");
                    System.exit(1);
                }
            }
            scf.close();

            //per memorizzare come gli oggetti verranno distribuiti, passo a creare un array che memorizza la capacità
            //inoccupata di ogni scatola da 1 a n. (Essendoci n oggetti, serviranno al massimo n scatole per trovare la
            //soluzione) In seguito, passo ad inserire ogni oggetto nella prima scatola in ordine cardinale che ha
            //una capacità residua tale da permetterne il contenimento.
            //Una volta trovata questa scatola, allora posso mandare un break per interrompere la ricerca per quel
            //determinato oggetto e passare all'oggetto successivo
            double[] spazio = new double[n];
            int j = 0;

            //se lo spazio rimanente in una scatola j riesce a contenere l'oggetto, il peso di quest'ultimo viene
            //rimosso dal peso totale disponibile per la j-esima scatola e il ciclo va in break per passare alla
            //prossima scatola
            //Se j è = al numero di scatole, ovvero se è arrivato fino alla fine delle scatole che stiamo già
            //analizzando senza trovare uno spazio per l'oggetto i-esimo, allora viene aggiunta un'altra scatola

            for(int i = 0; i < n; i++){
                for(j = 0; j < boxNum; j++){
                    if (spazio[j] >= obj[i]) {
                        spazio[j] = spazio[j] - obj[i];
                        break;
                    }
                }
                if (j == boxNum) {
                    spazio[boxNum] = c - obj[i];
                    boxNum++;
                }
            }

            System.out.println(boxNum + "\n");
            for(int i = 0; i < spazio.length; i++) {
                if (i < boxNum) {
                    System.out.println(c - spazio[i] + "\n");
                }else {break;}
            }

        }catch (FileNotFoundException e){
            System.out.println("File non Trovato" + e);
        }
    }
}
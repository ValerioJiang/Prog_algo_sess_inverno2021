/*
        * Se nel caso passasse questo primo controllo:
        *
        * 1. Si eliminerà la prima parte per la ricerca del primo 30;
        * 2. Ricerca lunga tutta la stringa text e memorizazzione in ArrayList di [30][e][lod[e]] con piccolo trucco su lode,
        * se non ultimo lode di text aggiungiamo "e" per una maggior facilità di lavorazione in counting();
        *
        *
        */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Es. per il funzionamento di counting:
 * 30e30elodelode
 * PseudoPseudoAlgoritmo:
 *
 *      Cercare prossimo 30
 *          Cercare prossimo e
 *              Cercare prossimo lode
 *                  if(arrayList[contatore] == "lode")
 *                      count++
 *
 * 30 | [30]e30elodelode count = 0
 * 30 -> e | [30][e]30elodelode count = 0
 * 30 -> e -> lode | [30][e]30e[lode]lode count = 1
 * 30 -> e -> lode | [30][e]30elode[lode] count = 2
 * 30 -> e -> lode | [30][e]30elode[lode] count = 2
 * 30 -> e  | [30]e30[e]lodelode count = 2
 * 30 -> e -> lode | [30]e30[e][lode]lode count = 2
 * 30 -> e -> lode | [30]e30[e]lode[lode] count = 3
 * 30 -> e  | [30]e30elod[e]lode count = 3
 * 30 -> e -> lode | [30]e30elod[e][lode] count = 3
 * .........
 * (ultimo caso)
 * 30 -> e -> lode | 30e[30]elod[e][lode] count = 8
 *
 * Dopo ragionamento mentale passiamo a scrittura codice
 *
 */
public class Esercizio1 {
    public static ArrayList<String> cleaning(String text){
        if (!text.contains("30")||!text.contains("e")||!text.contains("lode")){
            return null;
        }
        String original_substr = text.substring(text.indexOf("30"));
        String temp_substr = "";
        ArrayList<String> res = new ArrayList<>();
        int i = 0;
        while(i < original_substr.lastIndexOf("lode")+3){
            temp_substr = original_substr.substring(i);
            if(temp_substr.substring(0,2).equals("30")){
                res.add("30");
                i += 2;
            }
            else if(temp_substr.substring(0,1).equals("e")){
                res.add("e");
                i++;
            }
            else if(temp_substr.substring(0,4).equals("lode")){
                res.add("lode");
                res.add("e");
                i+=4;
            }
            else{
                i++;
            }
        }
        if(res.get(res.size()-1).equals("e")){
            res.remove(res.size()-1);
        }
        return res;
    }
    public static int counting(String text){
        ArrayList<String> cleaned_text = cleaning(text);
        if (cleaned_text == null){
            return 0;
        }
        int res = 0;
        for(int i = 0; i < cleaned_text.size(); i++){
            if(cleaned_text.get(i).equals("30")){
                for (int j = i+1; j < cleaned_text.size();j++){
                    if(cleaned_text.get(j).equals("e")){
                        for(int k = j+1; k < cleaned_text.size(); k++){
                            if (cleaned_text.get(k).equals("lode")){
                                res++;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length > 0) {
            Scanner scan = new Scanner(new File(args[0]));
            int times = scan.nextInt();
            ArrayList<String> not_cleaned = new ArrayList<>();
            while (scan.hasNext()){
                not_cleaned.add(scan.next());
            }
            for(String singleone : not_cleaned){
                System.out.println(counting(singleone));
            }
        }

    }
}



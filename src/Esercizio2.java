
import java.util.*;
import java.io.*;

public class Esercizio2{

    public static void main(String[] args){

        //dichiarazione variabili
        int k;
        int n;
        String fileName = "input.txt";

        if(args.length != 1){
            System.err.println("Utilizzo: java -cp . Es2 <fileName.txt>");
            System.exit(1);

        }else{
            fileName = args[0];
            System.out.println("Analisi di " + fileName + "...");
        }
        try{

            Scanner scf = new Scanner(new File(fileName));
            k = scf.nextInt();
            n = scf.nextInt();

            int[] arrayNodi = new int[n];

            //riempimento array
            int j = 0;
            do{
                arrayNodi[j] = scf.nextInt();
                j++;
            }while (scf.hasNext());
            scf.close();

            //reverse array
            int l = arrayNodi.length;
            int [] inv = new int[l];
            for (int i = 0; i < l; i++){
                inv[i] = arrayNodi[l - i - 1];
            }

            //creo la radice
            int radice = inv[0];
            Albero tree = new Albero(radice);

            //inserimento nodi nell'albero
            for(int i=1; i < inv.length; i++){
                tree.inserisci(inv[i]);
            }
            tree.ricerca(k);

        } catch (FileNotFoundException e){
            System.out.println("File non Trovato" + e);
        }
    }

    public static class Albero{

        int valore;
        Albero destro;
        Albero sinistro;


        //metodo per cercare i nodi all'interno dell'albero

        public Albero(int valore){
            this.valore = valore;
        }

        public void ricerca(int kappa){
            if(kappa == valore){
                if(destro != null){
                    destro.ricerca(kappa);
                }
                else{
                    System.out.println("Non presente");
                }
            }else if(kappa > valore){
                if(destro != null){
                    destro.ricerca(kappa);
                }else{
                    System.out.println("Non presente");
                }
            }else{
                System.out.println(valore);
            }
        }


        //metodo per inserire i nodi all'interno dell'albero

        public void inserisci(int valore){

            if(valore < this.valore){

                if(sinistro != null){
                    sinistro.inserisci(valore);
                }else{
                    sinistro= new Albero(valore);
                }
            }
            else{
                if(destro != null){
                    destro.inserisci(valore);
                }else{
                    destro = new Albero(valore);
                }
            }
        }


    }
}


import java.util.*;
import java.lang.*;
import java.io.*;

public class Esercizio5{
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("ERRORE: eseguire con: java -cp . Esercizio5 <tuofile> ");
            System.exit(1);
        }

        //subito dopo l'inizio dell'esecuzione, vengono presi in input il numero di fornitori e di nodi presenti nel
        //file di input e in base alla variabile n che memorizza il numero dei nodi, creiamo una matrice che
        //rispecchia in maniera precisa la struttura del file: nella prima colonna l'identificativo del fornitore, nella
        //seconda colonna la coordinata x e nella terza la coordinata y dei punti
        //il float tot ci servirà invece per calcolare la lunghezza totale di ogni fornitore
        try {
            Scanner scf = new Scanner(new File(args[0]));
            scf.useLocale(Locale.US);
            int providers = scf.nextInt();
            int n = scf.nextInt();
            float[][] nodes = new float[n][3];
            float totals = 0;

            //riempimento
            for (int i = 0; i < n; i++) {
                nodes[i][0] = scf.nextInt();
                nodes[i][1] = scf.nextFloat();
                nodes[i][2] = scf.nextFloat();
            }

            //per eseguire varie iterazioni per i vari fornitori, tutto il codice viene incluso in un grande ciclo for
            //in cui ogni fornitore corrisponde a un'iterazione del ciclo. All'inizio dell'iterazione viene eseguita una
            //ricerca di tutti i punti di ogni provider e viene calcolato quanti nodi sono posseduti da ogni provider,
            //per poter inizializzare un array (providerNodes) di dimensione esatta e non lasciare spazi inutilizzati
            for (int i = 0; i < providers; i++){
                int count = 0;
                for (int j = 0; j < n; j++) {
                    if (nodes[j][0] == i){count++;}
                }

                //qui viene eseguito un controllo per cui se il numero di nodi di un provider è minore di 1, allora
                //tutti i calcoli verranno evitati. Infatti, oltre al numero di nodi = 0, anche per un numero di nodi = 1
                //si avrà che la distanza minima è = 0
                if (count > 1) {
                    float[][] providerNodes = new float[count][2];
                    //riempimento di providerNodes
                    int w = 0;
                    for (int j = 0; j < n; j++) {
                        if (nodes[j][0] == i) {
                            providerNodes[w][0] = nodes[j][1];
                            providerNodes[w][1] = nodes[j][2];
                            w++;
                        }
                    }

                    //attraverso un semplice calcolo con il teorema di Pitagora, troviamo la distanza tra due nodi e la
                    //inseriamo in una matrice l, in cui l'intersezione tra una qualsiasi riga i e una qualsiasi colonna
                    //j definiscono nella loro intersezione la distanza tra il punto i-esimo e il punto j-esimo
                    float[][] lung = new float[count][count];
                    for (int k = 0; k < count; k++) {
                        for (int j = 0; j < count; j++) {
                            float val1 = Math.abs(providerNodes[k][0] - providerNodes[j][0]);
                            float val2 = Math.abs(providerNodes[k][1] - providerNodes[j][1]);
                            lung[k][j] = val1 + val2;
                        }
                    }
                    //si applica l'algoritmo di Prim per trovare i cammini minimi
                    boolean[] scelto = new boolean[lung.length];
                    Arrays.fill(scelto, false);
                    float inf = Integer.MAX_VALUE;
                    int conn = 0;

                    scelto[0] = true;
                    while(conn < scelto.length-1){
                        float min = inf;
                        int x = 0;
                        int y = 0;

                        //prende l'insieme dei nodi che hanno un corrispondente in scelto = true, in base a quei nodi si
                        //trova quale tra di essi possiede il minor costo per connettere altri nodi, al momento con scelto = false
                        //una volta trovato questo nodo, il totale delle connessioni viene aggiornato e il nodo appena collegato
                        //otterrà un corrispondente booleano = true, in modo che l'analisi successiva possa interpretarlo
                        //come potenziale connettore
                        for (int z = 0; z < scelto.length; z++){
                            if(scelto[z]){
                                for(int j = 0; j < scelto.length; j++){
                                    if(!scelto[j] && lung[z][j] != 0){
                                        if(min > lung[z][j]){
                                            min = lung[z][j];
                                            x = z;
                                            y = j;
                                        }
                                    }
                                }
                            }
                        }
                        totals+= min;
                        scelto[y] = true;
                        conn++;
                    }

                }
                System.out.println(totals);
                totals = 0;
            }

        }catch (FileNotFoundException e){
            System.out.println("File non Trovato" + e);
        }
    }
}
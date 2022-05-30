public class Graphe {
    protected  int nombreSommet;
    protected int nombreArcs;
    protected  int matriceAdjacence[][];
    protected  int matriceIncidence[][];

    public Graphe(int nombreSommet,int nombreArcs,int matriceAdjacence[][]){
        this.nombreSommet=nombreSommet;
        this.nombreArcs=nombreArcs;
        this.matriceAdjacence=matriceAdjacence;
        this.matriceIncidence=construireMatriceIncidence(this.matriceAdjacence);
    }
    public int getNombreArcs() {
        return nombreArcs;
    }

    public int getNombreSommet() {
        return nombreSommet;
    }
    //fonction qui retourne la matrice d'adjacence
    public int[][] getMatriceAdjacence() {
        return this.matriceAdjacence;
    }
    //fonction qui retourne la matrice d'adjacence
    public int[][] getMatriceIncidence() {
        return this.matriceIncidence;
    }



    private int[][] construireMatriceIncidence(int[][] matriceAdjacence) {
        int matrice[][]=new int[matriceAdjacence.length][matriceAdjacence.length];
        for (int i = 0; i < matriceAdjacence.length; i++) {
            for (int j = 0; j <matriceAdjacence[i].length ; j++) {
                if(matriceAdjacence[i][j]!=0){
                    matrice[i][j]=1;
                }else{
                    matrice[i][j]=0;
                }
            }
        }
        return matrice;
    }

    public String toString(){
        String s="\n*********************************************************************************************************************************" +
                "\n La matrice d'adjacence du graphe est : \n ";
        for (int i=0;i<this.matriceAdjacence.length;i++) {
            for (int j=0;j<this.matriceAdjacence[i].length;j++) {
                s+=this.matriceAdjacence[i][j]+" ";
            }
            s+="\n";
        }
        s+="*****************************************************************************************************************************************" +
                "\n La matrice d'incidence du graphe est: \n";
        for (int i=0;i<this.matriceIncidence.length;i++) {
            for (int j=0;j<this.matriceIncidence[i].length;j++) {
                s+=this.matriceIncidence[i][j]+" ";
            }
            s+="\n";
        }
        s+="******************************************************************************************************************************************";

        return s;
    }

    public void plusCourtChemin_s(int ligneSommetInitiale/*valeur du sommet initial*/,int matriceAdjacence[][]/*graphe*/){
        //Le sommet à initialiser doit etre mis a zero et la valeur doit être comprise entre 0 et la taille de la matrice
        if(ligneSommetInitiale>matriceAdjacence.length){
            System.out.println("Le sommet renseigné n'exite pas dans le graphe!");
            return;
        }else{
            ligneSommetInitiale=ligneSommetInitiale-1;

            //colonneSommet=colonneSommet-1;
            //tableau dinitialisation des sommet du graphe
            int vecteurInitialisation[]=remplirVecteurInitialisation(matriceAdjacence.length,ligneSommetInitiale);
            int matriceIncidencePlusCourtChemin[][]=remplirMatriceIncidence();
            //boucle pour le nombre de sommet -1 fois
            for (int k = 0; k < matriceAdjacence.length-1; k++) {
                //boucle pour les sommet en ligne de la matrice
                for (int i = 0; i < matriceAdjacence.length; i++) {
                    //boucle pour les colonnes de la matrice
                    for (int j = 0; j <matriceAdjacence[i].length ; j++) {
                        //verifier sil ya un arcs entre la ligne i et la colonne j
                        if(matriceAdjacence[i][j]!=0 && vecteurInitialisation[i]<Integer.MAX_VALUE){
                            //verifier si on peut faire un relachement
                            if(vecteurInitialisation[j]>vecteurInitialisation[i]+matriceAdjacence[i][j]){
                                //petit probleme ici pour le vecteur dinitialisation
                                            vecteurInitialisation[j]=vecteurInitialisation[i]+matriceAdjacence[i][j];
                                            //Mettre à jour la valeur du nouveau predecesseur
                                            matriceIncidencePlusCourtChemin[i][j]=1;
                                            //modifier le prédecesseurs de j a i car le nouveau predecesseurs de j est devenu i maintenant
                                            //pour cela je parcourir la ligne i et mettre à jour le tableau dincidence afin que a la sotie je puisse avoir les plus court chemin dans ce tableau
                                            for (int l = 0; l < matriceIncidencePlusCourtChemin[i].length; l++) {
                                                //verifier que le nouveau predecesseur mis à jour ne soit pas modifer
                                                if(l!=i){
                                                    matriceIncidencePlusCourtChemin[l][j]=0;
                                                }
                                            }
                            }else{

                            }
                        }else{

                        }
                    }
                }
            }

            //fin de l'algorithme et affichage des resultats
            afficherPlusCourtChemin(matriceIncidencePlusCourtChemin,vecteurInitialisation);
            //verification de la presence de circuit
            checkCircuitPoidsNegatif(matriceIncidencePlusCourtChemin,matriceAdjacence,vecteurInitialisation);

        }



    }
    /*Verifier si larbre contient un circuit de poid negatif return true si oui sinon retourne false*/
    public boolean checkCircuitPoidsNegatif(int matriceIncidencePlusCourtChemin[][],int matriceIncidence[][],int vecteurInitialisation[]){
        System.out.println("\n******************************************************************************\n Vérification de circuit de poids negatif\n");
        for (int i = 0; i <matriceIncidencePlusCourtChemin.length ; i++) {
            for (int j = 0; j <matriceIncidencePlusCourtChemin[i].length ; j++) {
                if(matriceIncidencePlusCourtChemin[i][j]==1){
                    if(vecteurInitialisation[j]>vecteurInitialisation[i]+matriceIncidence[i][j]) {
                        int x=i+1;
                        int y=j+1;
                        System.out.println("Il ya un circuit de poids négatif dans le graphe dont les sommet "+x+" et "+y+" font parti\n");
                        return true/*Dit que le graphe contient un circuit de poids negatif*/;
                    }
                }
            }
        }
        System.out.println("Il nya pas de circuit de poids négatif dans le graphe\n");
        return false/*Veut dire que le graphe ne contient pas de circuit de poids negatif*/;
    }

    //fonction qui rempli la matrice d'incidence
    private int[][] remplirMatriceIncidence() {
        int matrice[][]=new int[this.matriceAdjacence.length][this.matriceAdjacence.length];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j <matrice[i].length ; j++) {
                matrice[i][j]=0;
            }
        }
        return matrice;
    }

    //fonction qui rempli la matrice d'initialisation
    private int[] remplirVecteurInitialisation(int taille,int ligneSommet) {
        int matrice[]=new int[taille];
        for (int i = 0; i < taille; i++) {
            if(i==ligneSommet)
                matrice[i]=0;
            else
                matrice[i]=Integer.MAX_VALUE; /*Considerer linfinie comme la valeur maximale des entier*/
        }

        return matrice;
    }
    //fonction qui affiche le plus court chemin
    private void afficherPlusCourtChemin(int matrice[][],int vecteur[]){
        String s="\n*************************************************************************************\n";
        s+="La matrice du plus court chemin est :\n";
        //affichage de la matrice d'incidence du plus court chemin
        for (int i = 0; i <matrice.length ; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                s+=matrice[i][j]+" ";
            }
            s+="\n";
        }
        //affichage du vecteur dinitialisation
        s+="\n********************************************************************************************\n";
        for (int i = 0; i <vecteur.length ; i++) {
            s+=vecteur[i]+" ";
        }
        s+="\n**************************fin de l'algorithme***************************************************";
        System.out.println(s);

    }

}

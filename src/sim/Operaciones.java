
package sim;

import java.text.DecimalFormat;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class Operaciones {
    //Atributos de la clase
    private double[][] matriz;
    private int cantidad;
    private int fil;
    private int column;
    private double gradoA;
    private double alfa;
    NormalDistribution distNorm = new NormalDistribution();
    DecimalFormat df = new DecimalFormat("0.000");

    public Operaciones() {
    }
    
    //Prueba de la Media
    //Método principal
    public void PruebaDeMedia(){
        
        //Calculamos el promedio con el metodo: Media()
        System.out.println("calculo media = " + df.format(Media()));
        
        //Calculamos la Z con la clase NormalDistribution de la libreria Commons Math
        double ZdistNorm=(distNorm.inverseCumulativeProbability(alfa/2)); 
        
        System.out.println("Z en la tabla es igual a "+df.format(ZdistNorm));
        
        //Calculamos el limite inferior
        double limiteinferior=(0.5)+(ZdistNorm)*(1/Math.sqrt(12*cantidad)); 
        
        System.out.println("el limite inferior = " + df.format(limiteinferior));
        
        //Calculamos el limite superior
        double limitesuperior=(0.5)-(ZdistNorm)*(1/Math.sqrt(12*cantidad));
        
        System.out.println("el limite superior es = " + df.format(limitesuperior));
        
        //Comprobamos si se acepta o se rechaza
        if (Media() > limiteinferior && Media() < limitesuperior){
            System.out.println("\n No se rechaza el conjunto \n");
        }else{
            System.out.println("\n Se rechazan los numeros \n");
        }
    }
    
    //Cálculo de la media o promedio
    public double Media(){
        double sum=0;
        for (int i=0; i<fil; i++){
            for (int j=0; j<column; j++){
                sum += matriz[i][j];
            }
        }
        return Math.round((sum/cantidad)*10000d)/10000d;
    }
    
    //Prueba de la varianza
    //Método principal
    public void PruebaVarianza(double graLib){
        
        //Constructor de la clase ChiSquareDistribution de la libreria Commons Math
        ChiSquaredDistribution cs = new ChiSquaredDistribution(graLib);
        
        //Calculamos la varianza
        double calculovarianza=varianza();
        
        System.out.println("el valor de la varianza es = " + df.format(calculovarianza));
        
        //Calculamos el limite inferior
        double limiteinferior =cs.inverseCumulativeProbability(alfa/2)/(12*(cantidad-1)); 
        
        System.out.println("chi cuadrado del limite inferior es " + df.format(limiteinferior));
        
        //Calculamos limite inferior
        double limitesuperior = cs.inverseCumulativeProbability(1-(alfa/2))/(12*(cantidad-1)); 
        
        System.out.println("chi cuadrado del limite superior es " + df.format(limitesuperior));
        
        //Comprobamos si se acepta o se rechaza
        if(calculovarianza > limiteinferior && calculovarianza < limitesuperior){
            System.out.println("\n No se rechaza el conjunto de numeros \n");
        }else{
            System.out.println("\n Se rechaza el conjunto de numeros \n");
        }
    }
    
    //Cálculo de la varianza
    public double varianza(){
        double cont=0;
        for (int i=0; i<fil; i++){
            for (int j=0; j<column; j++){
                cont += Math.pow(Media()-matriz[i][j], 2);
            }
        }
        return Math.round((cont/(50-1))*10000d)/10000d;
    }
    
    //Prueba de Chi-cuadrada
    public void PruebaChiCuadrada(){

        //Calculamos alfa
        double alfa = 1-gradoA/100;
        
        //Calculamos m
        double m = (int)Math.sqrt(cantidad);
        
        //Creamos tabla de Chi-cuadrada
        double[][] TablaChicuadrado = new double[(int)m][5];
        
        //Calculamos y llenamos los valores de la tabla chi-cuadrada
        ValoresTablaChiCuadrado(m, TablaChicuadrado);
        
        //Se imprime la tabla Chi-cuadrada
        imprimirTablaChiCuadrado(m, TablaChicuadrado);
        
        //Calculamos chi-cuadrada
        double ChiCuadrado = Chicuadrado(m, TablaChicuadrado) ;
        System.out.println("chi cuadrada calculado = " + df.format(ChiCuadrado));
        
        //Calculamos el valor de Chi-cuadrada teórico con la clase ChiSquareDistribution de la librería Commons Math
        ChiSquaredDistribution chi = new ChiSquaredDistribution(m-1);
        double ChiCuadradoTeorico =chi.inverseCumulativeProbability(1-alfa);
        System.out.println("valor teórico de las tablas = " + df.format(ChiCuadradoTeorico));
        
        //Comprobamos si se acepta o se rechaza
        if(ChiCuadrado < ChiCuadradoTeorico){
            System.out.println("\n No se rechaza el conjunto de numeros \n");
        }else{
            System.out.println("\n Se rechaza \n");
        }
    }
    
    //Cálculo y llenado de la tabla Chi-Cuadrada
    public void ValoresTablaChiCuadrado(double m, double[][] TablaChiCuadrado){
        double separacion = Math.round((1/m) * 100d) / 100d;
        
        //Llenado del intervalo que abre. 1 Columna
        double valor = 0.0;
        for(int i = 0; i < m; i++){
            TablaChiCuadrado[i][0] = valor;
            valor += separacion;
        }
        
        //Llenado del intervalo que cierra. 2 Columna
        valor = separacion;
        for(int i = 0; i < m-1; i++){
            TablaChiCuadrado[i][1] = valor;
            valor += separacion;
        }
        TablaChiCuadrado[(int)m-1][1] = 1.0;
        
        //Llenado de la frecuencia observada. 3 Columna
        for (int cont = 0; cont < m; cont++){
            for (int i = 0; i < fil; i++){
                for (int j = 0; j < column; j++){
                    if (matriz[i][j]>TablaChiCuadrado[cont][0] && matriz[i][j]<TablaChiCuadrado[cont][1]){
                        TablaChiCuadrado[cont][2]+=1;
                    }
                }
            } 
        }
        
        //Llenado de la frecuencia esperada. 4 Columna
        for(int i = 0; i < m; i++){
            TablaChiCuadrado[i][3] = cantidad/m;
        }
        
        //Llenado de el valor Chi-cuadrada. 5 Columna
        for(int i = 0; i < m; i++){
            TablaChiCuadrado[i][4] =((Math.pow(TablaChiCuadrado[i][3]-TablaChiCuadrado[i][2], 2))/TablaChiCuadrado[i][3]) ;
        }
    }
    
    //Cálculo del Chi-cuadrado total
    public double Chicuadrado(double m, double[][] TablaChicuadrado){
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += TablaChicuadrado[i][4];
        }
        return sum;
    }
    
    //Impresión de la tabla Chi-cuadrada
    public void imprimirTablaChiCuadrado( double m, double[][] TablaChiCuadrado){
        for(int i=0; i<m; i++){
            for (int j=0; j<5; j++){
                System.out.print("\t"+ df.format(TablaChiCuadrado[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Prueba Kolmogorov-Smirnov
    public void PruebaKolmogorovSmirnov(double Dtabla, double acept){
        
        //Transformamos la matriz en un vector
        double[] vectnumpseudo = vector();
        
        //Ordenamos de menor a mayor
        double[] vectnumpseudoOrd = ordenamiento(vectnumpseudo);
        
        //Creamos la tabla Kolmogorov-Smirnov
        double[][] TablaKolmogorovSmirnov = new double[cantidad][6];
        
        //Calculamos y llenamos la tabla Kolmogorov-Smirnov
        valoresTablaKolmogorovSmirnov(vectnumpseudoOrd, TablaKolmogorovSmirnov);
        
        //Se imprime la tabla Kolmogorov-Smirnov
        imprimirTablaKolmogorovSmirnov(TablaKolmogorovSmirnov);
        
        //Calculamos el valor crítico D más
        double Dmax = valorCriticoDmas(cantidad, TablaKolmogorovSmirnov);
        
        System.out.println("D mas = " + Dmax);
        
        //Calculamos el valor crítico D menos
        double Dmin = valorCriticoDmenos(cantidad, TablaKolmogorovSmirnov);
        
        System.out.println("D menos = " + Dmin);
        
        //Calculamos el mayor de los anteriores D
        double Dneto = Dneto(Dmax, Dmin);
        
        System.out.println("D neto = " + Dneto);
        
        //Imprimimos el valor D tabla que ingresamos manualmente desde el
        //constructor de la clase Sim porque no se consiguió una libreria efectiva
        System.out.println("Dtabla = " + Dtabla);
        
        //Comprobamos si se acepta o se rechaza
        if(Dtabla > Dneto){
            System.out.println("\n No se rechaza el conjunto de numeros \n");
        }else{
            System.out.println("\n Se rechaza el conjunto de numeros \n");
        }
    }
    
    //Método para convertir matríz en vector
    public double[] vector(){
        double[] vector = new double[cantidad];
        int sum=-1;
        for (int i=0; i<fil; i++){
            for (int j=0; j<column; j++){
                sum += 1;
                vector[sum]=matriz[i][j];
            }
        }
        
        return vector;
    }
    
    //Método para ordenar de menor a mayor
    public double[] ordenamiento(double[] vectnumpseudo){
        double aux;
        for (int i = 1; i < cantidad; i++) {
            for (int j = 0; j < cantidad-i; j++) {
                if (vectnumpseudo[j] > vectnumpseudo[j+1]) {
                    aux = vectnumpseudo[j];
                    vectnumpseudo[j] = vectnumpseudo[j+1];
                    vectnumpseudo[j+1] = aux;
                }
            }
        }
        
        return vectnumpseudo;
    }
    
    //Cálculo y llenado de la tabla Kolmogorov-Smirnov
    public void valoresTablaKolmogorovSmirnov(double[] vecnumpseudoOrd, double[][] TableKmg){
        for (int i = 0; i < cantidad; i++) {
            TableKmg[i][0] = i+1;
            TableKmg[i][1] = vecnumpseudoOrd[i];
            TableKmg[i][2] = TableKmg[i][0]/cantidad;
            TableKmg[i][3] = (TableKmg[i][0]-1)/cantidad;
            TableKmg[i][4] = Math.round(((TableKmg[i][0]/cantidad-TableKmg[i][1])*100d))/100d;
            TableKmg[i][5] = Math.round(((TableKmg[i][1]-((TableKmg[i][0]-1)/cantidad))*100d))/100d;
        }
    }
    
    //Cálculo del valor crítico D más
    public double valorCriticoDmas(int n, double[][] TableKmg){
        double max=0;
        for (int i = 0; i < n; i++) {
            
            if (TableKmg[i][4]>max) {
                max=TableKmg[i][4];
            }
        }
        return max; 
    }
    
    //Calculo del valor crítico Dmenos
    public double valorCriticoDmenos(int n, double[][] TableKmg){
        double max=0;
        for (int i = 0; i < n; i++) {
            if (TableKmg[i][5]>max) {
                max=TableKmg[i][5];
            }
        }
        return max; 
    }
    
    //Cálculo del mayor de los D anteriores 
    public double Dneto(double criticoDmax, double criticoDmin){
        if (criticoDmax > criticoDmin) {
            return criticoDmax;
        }else{
            return criticoDmin;
        }
    }
    
    //Impresión de la tabla Kolmogorov-Smirnov
    public void imprimirTablaKolmogorovSmirnov(double[][] TableKmg){
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print("\t"+TableKmg[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Prueba de independencia
    //Método principal
    public void PruebaIndependencia(){
        
        //Reutilizamos el método vector que transforma una matríz a vector
        double[] vector = vector();
        imprimirVector(vector);
        
        //Creamos la tabla de las corridas que se llenan con unos y ceros
        int[] TablaCorridas = new int[cantidad-1];
        TablaCorridas(vector, TablaCorridas);
        imprimirTablaCorridas(cantidad, TablaCorridas);
        
        //Calculamos el número de corridas
        int calculoCorridas = calculoCorridas(TablaCorridas);
        System.out.println("numero de corridas = " + calculoCorridas);
        
        //Calculamos el valor esperado
        double valorEsperado = ((double)2*cantidad-1)/3;
        System.out.println("valor esperado = " + valorEsperado);
        
        //Calculamos la varianza de la corrida
        double varianzaCorrida =(16*(double)cantidad-29)/90;
        System.out.println("varianza del numero de corridas = " + df.format(varianzaCorrida));
        
        //Calculamos el Z estadistico
        double Zestadistico = ((double)calculoCorridas - valorEsperado)/Math.sqrt(varianzaCorrida);
        if (Zestadistico < 0) {
            Zestadistico *= -1; 
        }
        System.out.println("Z estadistico = " + df.format(Zestadistico));
        
        //Calculamos el Z de la tabla con la clase NormalDistribution de la librería Commons Math
        double Ztabla=Math.round((distNorm.inverseCumulativeProbability(alfa/2))*100d)/100d; 
        System.out.println("El valor de Z en la tabla es igual a "+ Ztabla);
        
        if (Zestadistico > Ztabla && Zestadistico < Ztabla*-1){
            System.out.println("\n Los números se pueden usar en una prueba de simulación \n");
        }else{
            System.out.println("\n Se rechazan los números para una prueba de simulación \n");
        }
    }
    
    //Llenado de la tabla de corridas con unos y ceros
    public void TablaCorridas(double[] vector, int[] TablaCorridas){
        for (int i = 1; i < cantidad; i++) {
            if (vector[i] <= vector[i-1]) {
                TablaCorridas[i-1] = 0;
            }else{
                TablaCorridas[i-1] = 1;
            }
        }
    }
    
    //Cálculo del número de corridas
    public int calculoCorridas(int[] TablaCorridas){
        int corridas = 1;
        for (int i = 0; i < cantidad-2; i++) {
            if (TablaCorridas[i] != TablaCorridas[i+1]) {
                corridas += 1;
            }
        }
        return corridas;
    }
    
    //Impresión del vector
    public void imprimirVector(double[] vector){
        System.out.println();
        for (int i = 0; i < cantidad; i++) {
            System.out.print("\t " + vector[i]);
        }
        System.out.println();
    }
    
    //Impresión de la tabla de corridas
    public void imprimirTablaCorridas(int cantidad, int[] TablaCorridas){
        System.out.println();
        for (int i = 0; i < cantidad-1; i++) {
            System.out.print("\t" + TablaCorridas[i]);
        }
        System.out.println();
        System.out.println();
    }
    
    public double[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public int getFil() {
        return fil;
    }

    public void setFil(int fil) {
        this.fil = fil;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getGradoA() {
        return gradoA;
    }

    public void setGradoA(double gradoA) {
        this.gradoA = gradoA;
    }
    
    public double getAlfa(){
        return alfa = Math.round((1 - this.gradoA / 100)*100d)/100d;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }
    
}

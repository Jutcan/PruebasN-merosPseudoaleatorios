
package sim;
public class Sim {
    public static void main(String[] args) {
        
        //Llenado manual de la matríz
        double[][] Matriz={{0.8797,0.3884,0.6289,0.8750,0.5999,0.8589,0.9996,0.2415,0.3808,0.9606},
                              {0.9848,0.3469,0.7977,0.5844,0.8147,0.6431,0.7387,0.5613,0.0318,0.7401},
                              {0.4557,0.1592,0.8536,0.8846,0.3410,0.1492,0.8681,0.5291,0.3188,0.5992},
                              {0.9170,0.2204,0.5991,0.5461,0.5739,0.3254,0.0856,0.2258,0.4603,0.5027},
                              {0.8376,0.6235,0.3681,0.2088,0.1525,0.2006,0.4720,0.4272,0.6360,0.0954}};
        
        //Constructor de la clase operaciones
        Operaciones op=new Operaciones();
        op.setCantidad(50);         //Llenado manual del valor cantidad
        op.setFil(5);               //Llenado manual del valor filas
        op.setColumn(10);           //Llenado manual del valor columnas
        op.setGradoA(95.0);         //Llenado manual del valor grados de aceptación
        op.getAlfa();               //Cálculo del alfa
        op.setMatriz(Matriz);       //Llenado de la matriz
        
        //Prueba de la media
        System.out.println("\n---------------Prueba de la media-----------------\n");
        op.PruebaDeMedia();
        
        //Prueba de la varianza calculando el grado de libertad para la prueba Chi-cuadrada
        System.out.println("\n--------------Prueba de la varianza---------------\n");
        op.PruebaVarianza((double)op.getCantidad()-1);
        
        //Prueba Chi-Cuadrada
        System.out.println("\n------------Prueba de Chi-cuadrada----------------\n");
        op.PruebaChiCuadrada();
        
        //Prueba Kolmogorov (Valor de D en la tabla Kolmogorv para alfa=0.10,n=50 ; Grado de aceptacion del 90%) 
        System.out.println("\n----------Prueba de Kolmogorov-Smirnov------------\n");
        op.PruebaKolmogorovSmirnov(0.16, 90);
        
        //Prueba de independencia
        System.out.println("\n------------Prueba de independencia---------------\n");
        op.PruebaIndependencia();
    }
    
}

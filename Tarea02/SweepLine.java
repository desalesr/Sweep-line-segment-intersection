import java.util.*;

import javax.swing.text.Segment;

import java.io.*;

public class SweepLine {


    public static AVLTree state = new AVLTree(); //Linea de barrido.
    public static ALVTree events = new AVLTree(); //Eventos encontrados
	public static TreeSet<Punto> intersection = new TreeSet<>(); //Es igual que un Set solo que mantiene ordenada la informacion.

    //Metodo que implementa el algoritmo de SweepLinea
    //Se pueden ayudar imprimiendo la linea y la interseccion encontrada y que punto estan procesando en cada paso
    //Asi saben si estan ordenando bien o mal o como va su algoritmo. 
	public static void sweepLine(ArrayList<Punto> pts) {
		/*
            Descomentar esto para ver si contruyen bien sus puntos y segmentos 
            y el main ya imprime el arbol tambien. Esto solo sirve para saber que van
            bien antes de implementar el algoritmo. 
            for(Punto p: pts)
                state.insert(p.segment);*/
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        ArrayList<Punto> pts = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            String[] linea = sc.nextLine().split(" ");
            int x1 = Integer.parseInt(linea[0].trim());
            int y1 = Integer.parseInt(linea[1].trim());
            int x2 = Integer.parseInt(linea[2].trim());
            int y2 = Integer.parseInt(linea[3].trim());
            /*
                Implementar la construccion de los puntos y de los segmentos mientras se
                van obteniendo los datos, deben de construir bien los puntos y segmentos, 
                ligarlos y etiquetarlos bien.
            */
            Punto p1 = new Punto(x1, y1); /*Cada par de puntos son un segmento, sólo hay que nombrarlos bien*/
            Punto p2 = new Punto(x2, y2);

            Segment segment = null;
            if(p1.compareTo(p2) <= 0){ /*Construcción del segmento con orden en las etiquetas*/
                segment = new Segment(p1,p2);
            } else {
                segment = new Segment(p2,p1);
            }
            /*Asociamos los segmentos a cada punto*/ 
            p1.setSegment(segment); 
            p2.setSegment(segment);
            
        }
        Collections.sort(pts);
        for(Punto p: pts) {
        	System.out.print("Punto (" + p.x + "," + p.y + ") -- segmento : ");
        	p.segment.imprime();
        	System.out.println();
        }
        
        sweepLine(pts);
        Segment.print(state.getRoot());
        
        System.out.println("Puntos de interseccion");
        for(Punto p: intersection) {
        	p.imprime();
        	System.out.println();
        }
	}
}	
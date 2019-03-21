import java.util.*;

import javax.swing.text.Segment;

import java.io.*;

public class SweepLine {


    public static AVLTree stateLine = new AVLTree(); //Linea de barrido.
    public static PriorityQueue<Punto> events = new PriorityQueue<>(); //eventos
    public static TreeSet<Punto> intersection = new TreeSet<>(); //Intersecciones encontradas -por comodidad-
    
    //Metodo que implementa el algoritmo de SweepLinea
    //Se pueden ayudar imprimiendo la linea y la interseccion encontrada y que punto estan procesando en cada paso
    //Asi saben si estan ordenando bien o mal o como va su algoritmo. 
	public static void sweepLine(PriorityQueue<Punto> events) {

        for(Punto event: events)
            stateLine.insert(event.segment); //Comentar abajo para probar sin algoritmo
        /*while(! pts.isEmpty())
            handleEvent(events.push());
    }
    //Método privado auxiliar para tratar los eventos
    private static void handleEvent(Punto event) {

    }*/

    }
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        PriorityQueue<Punto> points = new PriorityQueue<>();
        for(int i = 0; i < n; i++) { /*Creamos los puntos*/
            String[] linea = sc.nextLine().split(" ");
            double x1 = Double.parseDouble(linea[0].trim());
            double y1 = Double.parseDouble(linea[1].trim());
            double x2 = Double.parseDouble(linea[2].trim());
            double y2 = Double.parseDouble(linea[3].trim());
            /*
                Implementar la construccion de los puntos y de los segmentos mientras se
                van obteniendo los datos, deben de construir bien los puntos y segmentos, 
                ligarlos y etiquetarlos bien.
            */
            /*Aquí llevamos los endpoints para un segmento*/ 
            Punto p1 = new Punto(x1, y1);
            Punto p2 = new Punto(x2, y2);
            Segmento newSegment;
            if(p1.compareTo(p2)<= 0) { /*Lo etiquetamos en orden*/
                newSegment = new Segmento(p1,p2);
                p2.isFirst = false;
            }
             else { 
                newSegment = new Segmento(p2,p1);
                p1.isFirst = false;
             }
            p1.setSegment(newSegment);  
            p2.setSegment(newSegment);
            events.add(p1);
            events.add(p2);

        } /*Termina construcción de puntos y segmentos*/ 

        //Collections.sort(pts); /*Ordenamos los puntos de manera descendente*/ 
        for(Punto p: events) {
        	System.out.print("Punto (" + p.x + "," + p.y + ") -- segmento : ");
        	p.segment.imprime();
        	System.out.println();
        }

        sweepLine(events);
        Segmento.print(stateLine.getRoot());
        System.out.println("Puntos de interseccion");
        for(Punto p: intersection) {
        	p.imprime();
        	System.out.println();
        }
	}
}	
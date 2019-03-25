import java.util.*;

import javax.swing.text.Segment;

import java.io.*;

public class SweepLine {


    public static AVLTree stateLine = new AVLTree(); //Linea de barrido.
    public static TreeSet<Punto> intersection = new TreeSet<>(); //Intersecciones encontradas 
    
    //Metodo que implementa el algoritmo de SweepLinea
    //Se pueden ayudar imprimiendo la linea y la interseccion encontrada y que punto estan procesando en cada paso
    //Asi saben si estan ordenando bien o mal o como va su algoritmo. 
	public static void sweepLine(PriorityQueue<Punto> events) {
        for(Punto event : events)
            stateLine.insert(event.segment); /*Agregamos el segmento al árbol*/
        while(! events.isEmpty()){ /*Mientras tengamos eventos a procesar */
            Punto point = events.remove(); /*Obtenemos el primer punto*/
            if(point.isIntersection) { /*Si se trata de un punto de intersección*/
                intersection.add(point); /*Reportammos que se trata de una intersección */

                /*Obtenemos los segmentos que generan dicha intersección (de donde salió el punto) */
                Segmento s1 = point.intersected1;
                Segmento s2 = point.intersected2;

                /*Recuperamos los nodos en el AVL de los segmentos para realizar los cálculos con los vecinos */
                AVLNode nodeS1 = stateLine.search(s1);
                AVLNode nodeS2 = stateLine.search(s2);

                /*Recuperamos los segmentos vecinos de los nodos de los segmentos s1 y s2*/
                //Segmento leftNeighbourS1 = (Segmento)stateLine.leftNeighbour(nodeS1);
                Segmento rightNeighbourS1 = (Segmento)stateLine.rightNeighbour(nodeS1);
                Segmento leftNeighbourS2 = (Segmento)stateLine.leftNeighbour(nodeS2);
                //Segmento rightNeighbourS2 = (Segmento)stateLine.rightNeighbour(nodeS2);
                
                /*Pasamos a verificar si existe una intersección con los vecinos de los segmentos y el punto. 
                En esta parte simulamos el swap, que en realidad no existe, sólo es una comparación cruzada*/
                Punto inter = null; /*Ocuparemos la variable para todos los casos */
                
                if(leftNeighbourS2 != null && s1.intersection(leftNeighbourS2)) 
                    inter = s1.getIntersection(leftNeighbourS2);
                    if (inter != null)
                        events.add(inter);
                    
                if(rightNeighbourS1 != null && s2.intersection(rightNeighbourS1))
                    inter = s2.getIntersection(rightNeighbourS1);
                    if(inter != null)
                        events.add(inter);
            }

            if(point.isFirst) {/*Si es un evento de entrada */
                Segmento segmentPoint = point.segment; /*Recuperamos su segmento */
                stateLine.insert(segmentPoint); /*Lo insertamos en el árbol (nuestra línea de estado) */
                
                /*Recuperamos vecinos del segmento que se encuentran en el árbol */
                Segmento leftNeighbour = (Segmento)stateLine.leftNeighbour(segmentPoint);/*Izquierdo */
                Segmento rightNeighbour = (Segmento)stateLine.rightNeighbour(segmentPoint);/*Derecho */

                /*Calculamos puntos de intersección con dichos vecinos si existen
                Agregamos dichas intersecciones si existen*/
                Punto inter = null;
                if(leftNeighbour != null)
                    inter  = segmentPoint.getIntersection(leftNeighbour);
                if(inter != null) 
                    events.add(inter);
                if(rightNeighbour != null)
                    inter = segmentPoint.getIntersection(rightNeighbour);
                if(inter != null)
                    events.add(inter);
            }

            /*En este caso, se trata de un punto de salida de uno de los segmentos */

            /*Recuperamos el nodo que tiene al punto */
            AVLNode pointNode = stateLine.search(point.segment);
            /*Recuperamos vecinos del segmento que se encuentran en el árbol */
            Segmento leftNeighbour = (Segmento)stateLine.leftNeighbour(pointNode); 
            Segmento rightNeighbour = (Segmento)stateLine.rightNeighbour(pointNode);/*Derecho */
            stateLine.delete(pointNode); /*Eliminamos el punto del árbol -línea de estado- */

            /*Vemos si existe alguna intersección entre los vecinos -si existen- */
            if(leftNeighbour != null && rightNeighbour != null && leftNeighbour.getIntersection(rightNeighbour) != null)
                events.add(leftNeighbour.getIntersection(rightNeighbour));
        }
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
            } else { 
                newSegment = new Segmento(p2,p1);
                p1.isFirst = false;
             }
            p1.setSegment(newSegment);  
            p2.setSegment(newSegment);
            points.add(p1);
            points.add(p2);

        } /*Termina construcción de puntos y segmentos*/ 

        //Collections.sort(pts); /*Ordenamos los puntos de manera descendente YA NO NECESARIO POR EL USO DE UNA COLA DE PRIORIDADES*/ 
        for(Punto p: points) {
        	System.out.print("Punto (" + p.x + "," + p.y + ") -- segmento : ");
        	p.segment.imprime();
        	System.out.println();
        }

        sweepLine(points);
        Segmento.print(stateLine.getRoot());
        System.out.println("Puntos de interseccion");
        for(Punto p: intersection) {
        	p.imprime();
        	System.out.println();
        }
	}
}	
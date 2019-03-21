public class Punto implements Comparable<Punto> {

	public double x;	//Coordenada x del Punto
	public double y;	//Coordenada y del punto
	public boolean isFirst; // True si es el punto de entrada o False si es el de salida.
	public boolean isIntersection; //True si se trata de un punto de intersección
	public Segmento segment; //Segmento asociado al punto  


	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
		this.isFirst = true; //Valor por defecto pero se va a modificar cuando creemos su segmento asociado.
		this.isIntersection = false; //Valor por default. Puede que sea intersección pero se debe comprobar. 
		this.segment = null; 
	}

	public boolean equals(Punto p) {
		return this.x == p.x && this.y == p.y;
	}

	public double crossProduct(Punto p) {
		return this.x * p.y - this.y * p.x;
	}

	public Punto difference(Punto p) {
		return new Punto(this.x-p.x, this.y-p.y);
	}  

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	/*Método que asocia un segmento a un punto.*/
	public void setSegment(Segmento segment) {
		this.segment = segment;
	}

	/*Método que regresa el segmento asociado al punto */
	public Segmento getSegment(){
		return this.segment;
	}

	//Metodo para ordenar los puntos
	//Necesario para que los eventos esten ordenados. 
	//Deben ser coherentes con su orden, es decir si el orden que quieren para sus eventos es de arriba hacia abajo
	//entonces los segmentos deben estar ordenados de derecha a izauierda, es decir si ordenan los puntos por su coordenada 
	//y entonces los segmentos por su coordenada x y viceversa.
	public int compareTo(Punto p) { 
		/*En nuestra implementación, el barrido de línea se realizará de arriba hacia abajo, por lo que el orden en los puntos se realizará por medio de
		las coordenadas y, ésto es, el más alto es el punto menor. En caso de tener coordenada y igual, consideremos que el de menor coordenada x será
		el menor, pero este caso lo descartamos*/ 
		if (this.equals(p))
			return 0;
		
		if (this.y == p.y) /*Este caso es considerado degenerado.*/
			return (this.x < p.x)? -1 :	1; /*El más a la izquierda es el menor */
		
		return (this.y > p.y)? -1 : 1; /*Esta es la razón de orden descartando los casos degenerados */
	}

	//Metodo necesario para determinar el sentido de 3 puntos. necesario para saber si 2 segmentos se intersectan.
	//Elegir un representante para direccion y facilita mas las cosas.
	//0 para colineales, 1 para derecha y -1 para izquierda 
	//Se toma como referencia el punto que llama al método para determinar la dirección
	public int turn(Punto p2, Punto p3) {
		double turn = (((p2.getX() - this.getX()) * (p3.getY()- this.getY())) - (((p2.getY() - this.getY())) * ((p3.getX() - this.getX()))));
		if (turn == 0){
			return 0;
		} else if(turn > 0){
			return -1 ;
		}
		return 1;
	} 

	//auxiliar para imprimir un punto.
	public void imprime() {
		System.out.print("(" + this.x + "," + this.y + ")");
	}

}
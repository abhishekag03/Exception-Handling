import java.io.*;
import java.util.*;

class knight implements Comparable<knight>{
	String name;
	int x;
	int y;
	Stack<Object> stack;
	boolean dead;

	knight(String s, int a , int b){
		name = s;
		x = a;
		y = b;
		stack = new Stack<Object>();
		dead = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Stack<Object> getStack() {
		return stack;
	}

	public void setStack(Stack<Object> stack) {
		this.stack = stack;
	}
	public int compareTo(knight k) {
		return this.name.compareTo(k.name);

	}

	public static void knightfunc(knight[] knightarr, int i,int qx, int qy, int M, PrintWriter w) throws NonCoordinateException, StackEmptyException, OverLapException, QueenFoundException{
//		Object obj = knightarr[i].stack.pop();
		
		if (knightarr[i].stack.size()==0) {
			knightarr[i].dead = true;
			throw new StackEmptyException("StackEmptyException: Stack Empty exception ");	
		}
		Object obj = knightarr[i].stack.pop();
		if (!(obj instanceof Coordinate)) {
			throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception " + obj );
		}
		if (obj instanceof Coordinate) {
			Coordinate cord = (Coordinate)obj;
			for (int j=0 ; j<M ;j++) {
				if (j!=i && knightarr[j].getX()==cord.x && knightarr[j].getY()==cord.y) {
					knightarr[j].dead=true;
					knightarr[i].x=cord.x;
					knightarr[i].y=cord.y;
					throw new OverLapException("OverLapException: Knights Overlap Exception " + knightarr[j].getName());

				}
			}
			knightarr[i].x=cord.x;
			knightarr[i].y=cord.y;
			if (cord.x==qx && cord.y==qy) {
//				queen=true;
				throw new QueenFoundException("QueenFoundException: Queen has been Found. Abort!");
				
			}
			w.println("No exception: "+ cord.x + " " +cord.y);
		}

	}
}



class NonCoordinateException extends Exception{
	public NonCoordinateException(String message) {
		super(message);
	}
}

class StackEmptyException extends Exception{
	public StackEmptyException(String message) {
		super(message);
	}
}

class OverLapException extends Exception{
	public OverLapException(String message) {
		super(message);
	}
}

class QueenFoundException extends Exception{
	public QueenFoundException(String message) {
		super(message);
	}
}
class Coordinate{
	int x;
	int y;
	Coordinate(int a, int b){
		x=a;
		y=b;
	}
}

public class lab6 {
	public static int M;
	public static int qx;
	public static int qy;
	public static boolean queen=false;
	public static void iteration(int iter,int iterations,knight[] knightarr,PrintWriter w) throws  IOException, NonCoordinateException, StackEmptyException, OverLapException, QueenFoundException {

//		PrintWriter w = new PrintWriter("./src/out.txt", "UTF-8");

		for (int i=0; i<M ; i++) {
			if (knightarr[i].dead) {
				continue;
			}
			w.println(iter+ " " + knightarr[i].name + " " + knightarr[i].x +  " " + knightarr[i].y);
			knight knightobj = knightarr[i];
			
			try {
				//				knightfunc(knight k,knight[] knightarr, int i)
				knight.knightfunc(knightarr,i,qx,qy,M,w);
			}
			catch(StackEmptyException e) {
				w.println(e.getMessage());
			}
			catch(NonCoordinateException e) {
				w.println(e.getMessage());
			}
			catch(OverLapException e) {
				w.println(e.getMessage());
			}
			catch(QueenFoundException e) {
				w.println(e.getMessage());
				queen=true;
				break;
			}
			//			if (obj==null) {
			//				knightarr[i].dead = true;
			//				throw new StackEmptyException("StackEmptyException: Stack Empty exception ");	
			//			}
			//
			//			if (!(obj instanceof Coordinate)) {
			//				throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception " + obj);
			//			}
//			if (obj instanceof Coordinate) {
//				Coordinate cord = (Coordinate)obj;
//				for (int j=0 ; j<M ;j++) {
//					if (j!=i && knightarr[j].getX()==cord.x && knightarr[j].getY()==cord.y) {
//						knightarr[j].dead=true;
//						knightarr[i].x=cord.x;
//						knightarr[i].y=cord.y;
//						throw new OverLapException("OverLapException: Knights Overlap Exception " + knightarr[j].getName());
//
//					}
//				}
//				knightarr[i].x=cord.x;
//				knightarr[i].y=cord.y;
//				if (cord.x==qx && cord.y==qy) {
//					queen=true;
//					throw new QueenFoundException("QueenFoundException: Queen has been Found. Abort!");
//				}
//			}
		}
//		w.close();
	}



	public static void main(String[] args) throws IOException, NonCoordinateException, StackEmptyException, OverLapException, QueenFoundException {
		// TODO Auto-generated method stub
		Reader.init(System.in);
		int N = Reader.nextInt();
		M=N;
		int iterations = Reader.nextInt();
		int queen_x = Reader.nextInt();
		int queen_y = Reader.nextInt();
		int itr=1;
		qx=queen_x;
		qy=queen_y;
		Stack<Object> mystack = new Stack<Object>();
		//		HashMap<String,knight> myhash = new HashMap<String,knight>();
		String[] name_array = new String[N];
		knight[] knightarr = new knight[N];
		for (int i=0 ; i<N ; i++) {
			InputStream input = new FileInputStream("./src/" + (int)(i+1) + ".txt"); 
			//			Scanner scanned = new Scanner(new File("./src/" + i + ".txt"));
			//			String type = br.readLine();
			Reader.init(input);

			String name = Reader.next();
			name_array[i]=name;
			//			myhash.put(name, new Stack<Object>());
			int x = Reader.nextInt();
			int y = Reader.nextInt();
			int m = Reader.nextInt();
			knightarr[i] = new knight(name,x,y);
			for (int j = 0 ; j < m ; j++ ) {
				String s = Reader.next();
				if (s.equals("String")) {
					String str = Reader.next();
					//					myhash.get(name).push(str);
					knightarr[i].stack.push(str);
				}
				else if (s.equals("Coordinate")) {
					int x1 = Reader.nextInt();
					int y1 = Reader.nextInt();
					Coordinate cord = new Coordinate(x1, y1);
					knightarr[i].stack.push(cord);
					//					myhash.get(name).push(cord);
				}
				else if (s.equals("Float")) {
					float x1 = Reader.nextFloat();
					//					int y1 = Reader.nextInt();
					//					Coordinate cord = new Coordinate(x1, y1);
					knightarr[i].stack.push(x1);
					//					myhash.get(name).push(cord);
				}
				else {
					int integer = Reader.nextInt();
					//					myhash.get(name).push(integer);
					knightarr[i].stack.push(integer);
				}
			}
		}
		PrintWriter w = new PrintWriter("./src/out.txt", "UTF-8");
		Arrays.sort(knightarr);	
		//		boolean queen = false;
		while (!queen && itr<=iterations) {
//			System.out.print(itr+ " ");
			//			for (int i=0;i<M;i++) {

				iteration(itr,iterations,knightarr,w);
			
			//			catch(StackEmptyException e) {
			//				System.out.println(e.getMessage());
			//			}
			//			catch(NonCoordinateException e) {
			//				System.out.println(e.getMessage());
			//			}
//			catch(OverLapException e) {
//				System.out.println(e.getMessage());
//			}
//			catch(QueenFoundException e) {
//				System.out.println(e.getMessage());
//			}
			
			//			System.out.println("iterations "); 
			itr++;

			//			}
		}
		//	while (!queen && iterations>0 ) {
		//		for (int i=0; i<N ; i++) {
		//			try {
		//				Object obj = knightarr[i].stack.pop();
		//				if (obj==null) {
		//					knightarr[i].dead = true;
		//					throw new StackEmptyException(" Stack Empty exception ");	
		//				}
		//				
		//				if (!(obj instanceof Coordinate)) {
		//					throw new NonCoordinateException("Not a coordinate Exception" + obj);
		//				}
		//				else {
		//					Coordinate cord = (Coordinate)obj;
		//					for (int j=0 ; j<N ;j++) {
		//						if (j!=i && knightarr[j].getX()==cord.x && knightarr[j].getY()==cord.y) {
		//							     knightarr[j].dead=true;
		//							     throw new OverLapException(" Knights Overlap Exception " + knightarr[j].getName());
		//							
		//						}
		//					}
		//					knightarr[i].x=cord.x;
		//					knightarr[i].y=cord.y;
		//					if (cord.x==queen_x && cord.y==queen_y) {
		//						queen=true;
		//						throw new QueenFoundException(" Queen has been Found. Abort!");
		//					}
		//				}
		//			}
		//			catch(StackEmptyException e){
		//				System.out.println(e.getMessage() );
		//			}
		//		}
		//		
		//			iterations--;
		//	}
		w.close();
	}
	
}
class Reader {
	static BufferedReader reader;
	static StringTokenizer tokenizer;
	/** call this method to initialize reader for InputStream */
	static void init(InputStream input) {
		reader = new BufferedReader(
				new InputStreamReader(input) );
		tokenizer = new StringTokenizer("");
	}

	/** get next word */
	static String next() throws IOException {
		while ( ! tokenizer.hasMoreTokens() ) {
			//TODO add check for if necessary
			tokenizer = new StringTokenizer(
					reader.readLine() );
		}
		return tokenizer.nextToken();
	}

	static int nextInt() throws IOException {
		return Integer.parseInt( next() );
	}
	static float nextFloat() throws IOException {
		return Float.parseFloat( next() );
	}
	static double nextDouble() throws IOException {
		return Double.parseDouble( next() );
	}
}

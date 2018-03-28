
class B extends A {
	@Override
	public void print() {
		System.out.println("From B");
	}
	
}

class C extends A {
	public void print1() {
		System.out.println("From C");
	}
	
}

interface F1 {
	int pint = 0;
	abstract void print2();
}

interface F2 {
	public void print1();
	//public void print2();
}
class D extends C implements F1, F2 {

	public void print1() {
		// TODO Auto-generated method stub
		System.out.println("From print1");
	}

	public void print2() {
		// TODO Auto-generated method stub
		System.out.println("From print2");
	}
	
}
public class A {
	public void print() {
		System.out.println("From A");
	}
	
	public static void main(String[] args) {
		Object obj = null;
		System.out.println("obj = " + obj);
		int ret = (int) obj;
		System.out.println("obj val = " + ret);
		
		A a1 = new B();
		a1.print();
		
		D d = new D();
		d.print1();
		d.print1();
		d.print2();
	}
}

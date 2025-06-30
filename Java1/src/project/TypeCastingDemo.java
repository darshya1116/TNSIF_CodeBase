package project;

public class TypeCastingDemo {
	public static void main(String[] args) {
		
		//widening // implicit type casting
		
		float f = 22.14f;
		double d = f;
		System.out.println(d);
		
		//narrowing //explicit type casting
		double f1 = 10.52f;
		
		long ll =(long) f1;
		System.out.println(ll);
		
		long l2 = 9313472506L;
		
		int il2=(int) l2;
		System.out.println(il2);
		
		short a=130;
		
		byte b1 =(byte) a;
		System.out.println(b1);
		
		//byte -128 to 127
		//130 - 128=2
		
	}

}

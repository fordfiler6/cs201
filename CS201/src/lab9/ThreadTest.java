package lab9;

public class ThreadTest { 

static volatile Long globalSharedInteger = new Long(0); 

static volatile Long staticExternalInteger = new Long(0); 

	 
public static volatile int precId = 1; 

 public static synchronized void addToStaticExtInt(int val) 
 { 

	 staticExternalInteger += val; 

 } 

	 

public static void main(String[] args) { 

	 SharedItem sharedObject = new SharedItem(); 

	 sharedObject.number = 0; 

	 Long sharedInteger = new Long(0); 

	 LeThread thread1 = new LeThread(1, globalSharedInteger, sharedObject); 

	 LeThread thread2 = new LeThread(2, globalSharedInteger, sharedObject); 

	 // thread1.setPriority(Thread.MIN_PRIORITY); 

	 // thread2.setPriority(Thread.MIN_PRIORITY); 

	 thread1.start(); 

	 thread2.start(); 

	 

	 /* 

	 * while (true) { System.out.print(0); } 

	 */ 

	 

while (true) 
{ 

	 System.out.println("Static: " + LeThread.staticSharedNumber); 

	 System.out.println("Passed: " + globalSharedInteger); 

	 System.out.println("Object: " + sharedObject.number); 

	 System.out.println("External Static: " + staticExternalInteger); 

} 

	 

	 } 

	}

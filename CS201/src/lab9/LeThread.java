package lab9;

class LeThread extends Thread { 

	 private int ID; 

	 private Long passedNumber; 

	 private SharedItem so; 

	 

	 public static volatile long staticSharedNumber = 0; 

	 

public LeThread(int id, Long passedSharedNumber, SharedItem sharedObject) { 

	 ID = id; 

	 passedNumber = passedSharedNumber; 

	 so = sharedObject; 

} 

	 

public void run() {

	 /*

	 * while (true) { System.out.print(ID); } 

	 */ 

	 while (true)
	 { 

		 if (ID == 1 ) 
		 { 
	
			 if(ThreadTest.precId ==1)
			 {
				 staticSharedNumber--; 
		
				 passedNumber--; 
			
				 so.number--; 
			
				 ThreadTest.addToStaticExtInt(-1); 
				 try {
					 ThreadTest.precId = 2;
					sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }

		 } 

		 if (ID == 2) 
		 { 
		 if(ThreadTest.precId ==2)
		 {
			 staticSharedNumber++; 
		
			 passedNumber++; 
		
			 so.number++; 
		
			 ThreadTest.addToStaticExtInt(1); 
			 
			 try 
			 {
				 ThreadTest.precId = 1;
				sleep(1);
			 } 
			 catch (InterruptedException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	
		 } 

	 } 

	 } 

}

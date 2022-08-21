package tcpclient;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
public class TCPClient{
private static InetAddress host;  //IP of server 
private static final int PORT=1234; // The port between client & server
public static void main(String args[]) {
try {
	 host = InetAddress.getLocalHost();
	accessServer(); // Method to make client access the server
} catch (IOException e) {
	System.out.println("Host not Found");
	System.exit(1);
}
}


	private static void accessServer()  {
		System.out.println("Connected...");
		Socket link=null;//step1
		try {
			link = new Socket ("192.168.1.3",PORT);//step1 : request to connect with server
			Scanner userEntry = new Scanner(System.in);//
				InputStream input=link.getInputStream();//step2 create input from client 
				InputStreamReader isr=new InputStreamReader(input);
				BufferedReader br=new BufferedReader(isr);
				PrintWriter output = new PrintWriter(link.getOutputStream()); // create output from user
				String ciphertext; 
                                int k1,k2,k3,pl; 

			do {                                
				System.out.print("Enter Key1: "); // Now enters The keys
				k1 = userEntry.nextInt();
                                System.out.print("Enter Key2: ");
				k2 = userEntry.nextInt();
                                System.out.print("Enter Key3: ");
				k3 = userEntry.nextInt();
                                System.out.print("Enter Plain Text: "); // Enter the plain text
				pl = userEntry.nextInt();
                                ciphertext = (Arrays.toString(SDesE(k1,k2,k3,pl))); // Call method SDes Encryption
				output.println(ciphertext); // Send the ciphertext to the server As a string
				output.flush();// Cleaning 
                                ciphertext=userEntry.next();
				}while (!ciphertext.equalsIgnoreCase("close")); // To close the connection between the server and client by type close
				userEntry.close();//step4 : close the system in 
				br.close();//step4 
			       output.close();
		} catch (IOException e) {
			System.out.println("No response form server" ); // if theres no response from server 
		}			
      try {
    	  System.out.println("Close Connection...");
    	 
    	  link.close();//step4 : close the socket of client   
	} 
      catch (IOException e) {
		System.out.println("Unable to connect");
		System.exit(1); // close the program if cant close the socket. 

	}			
	}
        
        
        
         public static int[] SDesE(int key1, int key2, int key3, int plain) {
     // defined attribute
        int k1[] = new int[10];
        int ky1=key1 ;
        int k2[] = new int[10];
        int ky2= key2;
        int k3[] = new int[10];
        int ky3 =key3 ;

        int st;
        int arr4[] = new int[8];
        int arr5[] = new int[8];
        int arr6[] = new int[8];
        int arr7[] = new int[8];
        int arr8[] = new int[8];
        int arr9[] = new int[8];

        int arr[] = new int[8];
        Scanner s = new Scanner(System.in);
        // enter the key 1,2,3 and plain text 
       
       
        for (int i = 9; i >= 0; i--) {
            k1[i] = ky1 % 10;
            ky1 /= 10;
        }

      
    
        for (int i = 9; i >= 0; i--) {
            k2[i] = ky2 % 10;
            ky2 /= 10;
        }
  
        for (int i = 9; i >= 0; i--) {
            k3[i] = ky3 % 10;
            ky3 /= 10;

        }
        st = plain;
        for (int i = 7; i >= 0; i--) {
            arr[i] = st % 10;
            st = st / 10;

        }
        /*/////////////////////////////////////////////////////////////*/
        // make equation E(k3,D(k2,E(k1,p) triple sdes
        arr4 = enc(arr, k1);
//        System.out.println("the step 1 result");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr4[i]);
//        }
//        System.out.println();
//        System.out.println("................................................................");

        arr5 = dec(arr4, k2);
//        System.out.println("the step 2 result ");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr5[i]);
//        }
//       11
        // the final result store in arr6 and return it to attribute cipher text 
        arr6 = enc(arr5, k3);
        System.out.println("The ciphertext  and the final result ");
        for (int i = 0; i < 8; i++) {
            System.out.print(arr6[i]);
        }
        System.out.println();
        System.out.println("................................................................");
        return arr6;
    }
         //this method to make ip to plain text 

    public static int ip(int arr[], int i) {
        if (i == 0) {
            return (arr[1]);     /*                transposition       */ 
        }
        if (i == 1) {
            return (arr[5]);     
        }
        if (i == 2) {
            return (arr[2]);
        }
        if (i == 3) {
            return (arr[0]);      
        }
        if (i == 4) {
            return (arr[3]);
        }
        if (i == 5) {
            return (arr[7]);
        }
        if (i == 6) {
            return (arr[4]);
        } else {
            return (arr[6]);      /*                transposition       */ 
        }

    }
      // this method make expanding to right part after the plain text exit ip method 
    public static int ex(int r0[], int i) {
        if (i == 0) {
            return (r0[3]);
        }
        if (i == 1) {
            return (r0[0]);
        }
        if (i == 2) {
            return (r0[1]);
        }
        if (i == 3) {
            return (r0[2]);
        }
        if (i == 4) {
            return (r0[1]);
        }
        if (i == 5) {
            return (r0[2]);
        }
        if (i == 6) {
            return (r0[3]);
        } else {
            return (r0[0]);
        }

    }
// this method to convert to binarry this use in sbox
    public static String binaary(int s) {
        if (s == 0) {
            return ("00");
        }
        if (s == 1) {
            return ("01");
        }
        if (s == 2) {
            return ("10");
        } else {
            return ("11");
        }

    }
   // this method use to control operations or u can say fk 
    public static int[] fun(int arr1[], int key[]) {
        // defined attribute 

        int arr2[] = new int[8];
        int l0[] = new int[4];
        int r0[] = new int[4];
        int l1[] = new int[4];
        int r1[] = new int[4];
        int re0[] = new int[8];
        int re1[] = new int[8];
        int s0[][] = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
        int s1[][] = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};
        int s0c = 0;
        int s0r = 0;
        int s1c = 0;
        int s1r = 0;
        int s0e = 0;
        int s1e = 0;
        int sbox[] = new int[4];
        int a3=0;

        /**
         * *******************************************************************
         */
//        System.out.println("after ip");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr1[i]);
//
//        }
        /**
         * ********************************************************************
         */
//        System.out.println();
//        System.out.println("the left part");
        // this opration use for devide the plain text to two part left and right 
        for (int i = 0; i < 4; i++) {
            l0[i] = arr1[i];

        }
        /**
         * *******************************************************************
         */
        for (int i = 0; i < 4; i++) {
            r0[i] = arr1[i + 4];

        }
        /**
         * *****************************************************************
         */
//       11
        /**
         * ******************************************************************
         */
//        System.out.println();
//
//        System.out.println("the right part");
//        for (int i = 0; i < 4; i++) {
//
//            System.out.print(r0[i]);
//        }
        /*.........................................................................*/
       // make an attribute to sotre the right part to it 
        for (int i = 0; i < 4; i++) {
            l1[i] = r0[i];

        }
        /**
         * *********************************************************************
         */
//
//        System.out.println();
//        System.out.println(" the expantion");
       // call method ex ro make expantion to right part 
        for (int i = 0; i < 8; i++) {
            re0[i] = ex(r0, i);
        }
//        for (int i = 0; i < 8; i++) {
//            System.out.print(re0[i]);
//        }
        /**
         * ********************************************************************
         */
//        System.out.println();
//        System.out.println("key xor re0");
        // make xor right part with key  the output of key gen
        for (int i = 0; i < 8; i++) {
            re1[i] = key[i] ^ re0[i];
        }

//        for (int i = 0; i < 8; i++) {
//            System.out.print(re1[i]);
//        }
        /**
         * ****************************************************************
         */
        // the sbox ..................................................
        if (re1[0] == 1 && re1[3] == 0) {           // sbox check s0 row 
            s0r = 2;
        }
        if (re1[0] == 1 && re1[3] == 1) {
            s0r = 3;
        }
        if (re1[0] == 0 && re1[3] == 1) {
            s0r = 1;
        }
        if (re1[0] == 0 && re1[3] == 0) {
            s0r = 0;
        }
        /**
         * *****************************************
         */
        if (re1[1] == 1 && re1[2] == 0) {        // sbox check  s0 column 
            s0c = 2;
        }
        if (re1[1] == 1 && re1[2] == 1) {
            s0c = 3;
        }
        if (re1[1] == 0 && re1[2] == 1) {
            s0c = 1;
        }
        if (re1[1] == 0 && re1[2] == 0) {
            s0c = 0;
        }
        /**
         * ***********************************************
         */
        if (re1[4] == 1 && re1[7] == 0) {     // sbox check s1 row 
            s1r = 2;
        }
        if (re1[4] == 1 && re1[7] == 1) {
            s1r = 3;
        }
        if (re1[4] == 0 && re1[7] == 1) {
            s1r = 1;
        }
        if (re1[4] == 0 && re1[7] == 0) {
            s1r = 0;
        }
        /**
         * ***********************************************************************
         */
        if (re1[5] == 1 && re1[6] == 0) {    // sbox check s1 column 
            s1c = 2;
        }
        if (re1[5] == 1 && re1[6] == 1) {
            s1c = 3;
        }
        if (re1[5] == 0 && re1[6] == 1) {
            s1c = 1;
        }
        if (re1[5] == 0 && re1[6] == 0) {
            s1c = 0;
        }
        /**
         * *****************************************************************
         */
        s0e = s0[s0r][s0c]; // store sbox resul 
        s1e = s1[s1r][s1c];
//        System.out.println();
//        System.out.println("sbox number");
//        System.out.println(s0e);
//        System.out.println(s1e);

        /**
         * ******************************************************************
         */
        String src = new String();
        String src1 = new String();
        String src2 = new String();
        String zero = new String();
        // convert result to binarry 
        src1 = binaary(s0e);

        src2 = binaary(s1e);

        src = src1 + src2;
        // sbox final result 
        for (int i = 0; i < 4; i++) {
            sbox[i] = Character.getNumericValue(src.charAt(i));
        }

//        System.out.println("after sbox");
//        for (int i = 0; i < 4; i++) {
//            System.out.print(sbox[i]);
//        }
//        System.out.println();
        /**
         * ********************************************************
         */
         // sbox xor the orginal left part 
         a3=sbox[3];
           
           sbox[3]=sbox[1];
           sbox[1]=sbox[2];
           sbox[2]=a3;
        for (int i = 0; i < 4; i++) {
            r1[i] = sbox[i] ^ l0[i];
        }
        /**
         * **********************************************************
         */

        /**
         * ***********************************************************
         */
//        System.out.println("11 equle=");
//        for (int i = 0; i < 4; i++) {
//            System.out.print(r1[i]);
//        }
//        System.out.println();
        /**
         * ***********************************************************
         */
        // save result in arr2 
        for (int i = 0; i < 4; i++) {
            arr2[i] = l1[i];
        }
        for (int i = 0; i < 4; i++) {
            arr2[i + 4] = r1[i];
        }
        return (arr2);

    }
        //this method to make ip invers 
    public static int ipin(int arr[], int i) {
        if (i == 0) {
            return (arr[3]);
        }
        if (i == 1) {
            return (arr[0]);
        }
        if (i == 2) {
            return (arr[2]);
        }
        if (i == 3) {
            return (arr[4]);
        }
        if (i == 4) {
            return (arr[6]);
        }
        if (i == 5) {
            return (arr[1]);
        }
        if (i == 6) {
            return (arr[7]);
        } else {
            return (arr[5]);
        }

    }
        // this method to make encryption to the plain text 
    public static int[] enc(int arr[], int ke[]) {

        int arr1[] = new int[8];
        int key[] = new int[8];
        int key2[] = new int[8];

        int arr2[] = new int[8];
        int arr3[] = new int[8];
        int arr3l[] = new int[4];
        int arr3r[] = new int[4];
        int arr4[] = new int[8];
        int keys[] = new int[16];

        /**
         * *******************************************************************
         */
        /**
         * ********************************************************************
         */
        // this call key genarator 
        keys = gen(ke);
        // return array with 2 key each keay 8 bit 
        for (int i = 0; i < 8; i++) {
            key[i] = keys[i];
            key2[i] = keys[i + 8];

        }
        for (int i = 0; i < 8; i++) {
            arr1[i] = ip(arr, i);

        }
          // call fk to make opration to the plain text (1st section  ) of sdes 
        arr2 = fun(arr1, key);
//        System.out.println("the array after SW");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr2[i]);
//        }
//        System.out.println();
//        System.out.println("plain text itr2");
      // call fk1 again 
        arr3 = fun(arr2, key2);
        for (int i = 0; i < 4; i++) {
            arr3l[i] = arr3[i];
            arr3r[i] = arr3[i + 4];
        }
        for (int i = 0; i < 4; i++) {
            arr3[i] = arr3r[i];
            arr3[i + 4] = arr3l[i];
        }
//        System.out.println("before ip invers");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr3[i]);
//        }
//        System.out.println();
       // make ip invers to the final result 
        for (int i = 0; i < 8; i++) {
            arr4[i] = ipin(arr3, i);

        }
        return arr4;

    }
     // this method to make key generator to make two kye each key 8 bit 
    public static int[] gen(int a[]) {
        Scanner s = new Scanner(System.in);

        int keys[] = new int[16];
        int ai[] = new int[10];
        int k1[] = new int[8];
        int k2[] = new int[8];
        int ail[] = new int[5];
        int air[] = new int[5];
        int ailr[] = new int[5];
        int airr[] = new int[5];

//        System.out.println(" ur key");
//        for (int i = 0; i < 10; i++) {
//            System.out.print(a[i]);
//
//        }
//        System.out.println();
       // call p10 method to make transposition
        for (int i = 0; i < 10; i++) {

            ai[i] = p10(a, i);

        }

//        System.out.println("the key after p10");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print(ai[i]);
//
//        }
//        System.out.println();

        for (int i = 0; i < 5; i++) {
            ail[i] = ai[i];
            air[i] = ai[i + 5];

        }
        //make rotate to the key with value 1
        ailr[4] = ail[0];
        airr[4] = air[0];
        for (int i = 0; i < 4; i++) {
            ailr[i] = ail[i + 1];
            airr[i] = air[i + 1];

        }
        for (int i = 0; i < 5; i++) {
            ai[i] = ailr[i];
            ai[i + 5] = airr[i];

        }
//        System.out.println("the key after rotate");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print(ai[i]);
//
//        }
//        System.out.println();
        // call p8 to make transposition and substitution 
         // store key to k1 array
        for (int i = 0; i < 8; i++) {

            k1[i] = p8(ai, i);

        }
//        System.out.println("the key one ");
//
//        for (int i = 0; i < 8; i++) {
//
//            System.out.print(k1[i]);
//
//        }
//        System.out.println();
        /**
         * ********************************************************************
         */

        for (int i = 0; i < 5; i++) {
            ail[i] = ai[i];
            air[i] = ai[i + 5];

        }
        ailr[4] = ail[0];
        airr[4] = air[0];
        for (int i = 0; i < 4; i++) {
            ailr[i] = ail[i + 1];
            airr[i] = air[i + 1];

        }
        for (int i = 0; i < 5; i++) {
            ai[i] = ailr[i];
            ai[i + 5] = airr[i];

        }
        // the key2 after rotate 1..............................................
        
        
//        System.out.println("the key2 after rotate1");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print(ai[i]);
//
//        }
//        System.out.println();

        for (int i = 0; i < 5; i++) {
            ail[i] = ai[i];
            air[i] = ai[i + 5];

        }
        ailr[4] = ail[0];
        airr[4] = air[0];
        for (int i = 0; i < 4; i++) {
            ailr[i] = ail[i + 1];
            airr[i] = air[i + 1];

        }
        for (int i = 0; i < 5; i++) {
            ai[i] = ailr[i];
            ai[i + 5] = airr[i];

        }
        // the key2 after rotate two ............................................
        
        
        
//        System.out.println("the key2 after rotate2");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print(ai[i]);

     //   }
       // System.out.println();
        for (int i = 0; i < 8; i++) {

            k2[i] = p8(ai, i);

        }
//        System.out.println("the key two ");
//
//        for (int i = 0; i < 8; i++) {
//
//            System.out.print(k2[i]);
//
//        }
//        System.out.println();

        for (int i = 0; i < 8; i++) {
            keys[i] = k1[i];
            keys[i + 8] = k2[i];

        }

        return keys;

    }
// p10 method to make transposition 
    public static int p10(int a[], int i) {
        if (i == 0) {
            return (a[2]);

        }
        if (i == 1) {
            return (a[4]);

        }
        if (i == 2) {
            return a[1];

        }
        if (i == 3) {
            return (a[6]);

        }
        if (i == 4) {
            return (a[3]);

        }
        if (i == 5) {
            return (a[9]);

        }
        if (i == 6) {
            return (a[0]);

        }
        if (i == 7) {
            return (a[8]);

        }
        if (i == 8) {
            return (a[7]);

        } else {
            return (a[5]);
        }

    }
    // p8 method to make transposition and substitution

    public static int p8(int a[], int i) {
        if (i == 0) {
            return (a[5]);
        }
        if (i == 1) {
            return (a[2]);
        }
        if (i == 2) {
            return (a[6]);
        }
        if (i == 3) {
            return (a[3]);
        }
        if (i == 4) {
            return (a[7]);
        }
        if (i == 5) {
            return (a[4]);
        }
        if (i == 6) {
            return (a[9]);
        } else {
            return (a[8]);
        }

    }
    // the decryption method the same encryption but the order of key is different

    public static int[] dec(int arr[], int ke[]) {

        int arr1[] = new int[8];
        int key[] = new int[8];
        int key2[] = new int[8];

        int arr2[] = new int[8];
        int arr3[] = new int[8];
        int arr3l[] = new int[4];
        int arr3r[] = new int[4];
        int arr4[] = new int[8];
        int keys[] = new int[16];

        /**
         * *******************************************************************
         */
        /**
         * ********************************************************************
         */
        keys = gen(ke);
        for (int i = 0; i < 8; i++) {
            key[i] = keys[i];
            key2[i] = keys[i + 8];

        }
        for (int i = 0; i < 8; i++) {
            arr1[i] = ip(arr, i);

        }

        arr2 = fun(arr1, key2);
//        System.out.println("the array after SW");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr2[i]);
//        }
//        System.out.println();
//        System.out.println("plain text itr2");

        arr3 = fun(arr2, key);
        for (int i = 0; i < 4; i++) {
            arr3l[i] = arr3[i];
            arr3r[i] = arr3[i + 4];
        }
        for (int i = 0; i < 4; i++) {
            arr3[i] = arr3r[i];
            arr3[i + 4] = arr3l[i];
        }
//        System.out.println("before ip invers");
//        for (int i = 0; i < 8; i++) {
//            System.out.print(arr3[i]);
//        }
//        System.out.println();

        for (int i = 0; i < 8; i++) {
            arr4[i] = ipin(arr3, i);

        }
        return arr4;

    }


}
	
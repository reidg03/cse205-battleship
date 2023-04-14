import java.util.ArrayList;
//GUI is abstract and has static fields and methods so it can be called from any class and not instantiated
//almost like it inherits Image class but not really
public abstract class GUI {
	
	private static ArrayList<String[]> imgs = new ArrayList<>();
	private static ArrayList<Integer> locX = new ArrayList<>();
	private static ArrayList<Integer> locY = new ArrayList<>();
	private static int w = 100;
	private static int h = 30;
	private static int numMessageLayers = 0;
	public static Board leftBoard;
	public static Board rightBoard;
	public static boolean won = false;
	
	//for debugging list of sub images
	public static void debugPrintImgList() {
		System.out.println("IMAGE LIST -----------------------");
		for (int j=0; j<imgs.size();j++) {
			System.out.println(j + ": ");
			for (int i=0;i<imgs.get(j).length;i++)
				System.out.println(imgs.get(j)[i].toString());
			System.out.println("\nEND OF LIST -----------------------\n\n");
		}
	}
	
	//return width and height of GUI
	public static int getWidth() {return w;}
	public static int getHeight() {return h;}
	
	//creates box image and adds to GUI
	public static void setup() {
		String s1 = "";
		for (int i=0;i<w-2;i++) {
			s1 += "-";
		}
		String[] s2 = new String[h-2];
		for(int i=0;i<h-2;i++) {
			s2[i] = "|";
		}
		addImg("+",0,0);
		addImg("+",0,h-1);
		addImg("+",w-1,0);
		addImg("+",w-1,h-1);
		addImg(s1,1,0);
		addImg(s1,1,h-1);
		addImg(s2,0,1);
		addImg(s2,w-1,1);
	}
	
	//clears messages (directions, instructions, etc)
	public static void clearMessages() {
		for (int i=0;i<numMessageLayers;i++) removeLast();
		numMessageLayers = 0;
	}
	
	//display message at bottom of screen
	public static void bottomMessage(String s1) {
		clearMessages();
		numMessageLayers++;
		addImg(s1,4,h-3);
		
		GUI.print();
	}

	//display fulscreen message
	public static void fullscreenMessage(String s3) {
		clearMessages();
		numMessageLayers += 2;
		String[] s1 = new String[h-2];
		String s2 = "";
		for (int i=0;i<w-2;i++) {
			s2+=" ";
		}
		for (int i=0;i<h-2;i++) {
			s1[i] = s2;
		}
		addImg(s1,1,1);
		addImg(s3,w/2-s3.length()/2,h/2);
		
		GUI.print();
	}
	public static void fullscreenMessage(String[] s3) {
		clearMessages();
		numMessageLayers += 2;
		String[] s1 = new String[h-2];
		String s2 = "";
		for (int i=0;i<w-2;i++) {
			s2+=" ";
		}
		for (int i=0;i<h-2;i++) {
			s1[i] = s2;
		}
		int max=0;
		for(int i=0;i<s3.length;i++) {
			if (s3[i].length()>max) max=s3[i].length();
		}
		
		addImg(s1,1,1);
		addImg(s3,w/2-max/2,h/2-s3.length/2);
		
		GUI.print();
	}
	
	//add text image to GUI at coords
	public static int addImg(String[] img1, int x, int y) {
		imgs.add(img1);
		locX.add(x);
		locY.add(y);
		return imgs.size()-1;
	}
	public static int addImg(String img1, int x, int y) {
		String[] tempStr = new String[1];
		tempStr[0] = img1;
		imgs.add(tempStr);
		locX.add(x);
		locY.add(y);
		return imgs.size()-1;
	}
	
	//return character at coords in GUI
	public static String charAt(int x, int y) {
		return getImage().substring(y*w+y+x, y*w+y+x+1);
		//coords start at 0,0
	}
	
	//remove subimage
	public static String[] removeAt(int index) {
		String[] removed = imgs.get(index);
		imgs.remove(index);
		locX.remove(index);
		locY.remove(index);
		return removed;
	}
	
	//remove last subimage
	public static String[] removeLast() {
		String[] removed = imgs.get(imgs.size()-1);
		int last = imgs.size()-1;
		imgs.remove(last);
		locX.remove(last);
		locY.remove(last);
		return removed;
		
	}
	
	//used to add subimages to composite image
	private static String insert(String small, String big, int x) {
		return big.substring(0,x) + small + big.substring(x+small.length());
	}
	
	public static int getImageListSize() {
		return imgs.size();
	}
	
	//return string of composite image
	public static String getImage() {
		//create default empty image
		String[] tempImgSlices = new String[h];
		for (int j=0;j<h;j++) {	
			tempImgSlices[j]="";
			for (int i=0;i<w;i++) {
				tempImgSlices[j]+=" ";
			}
		}
		
		//go through each active board and add together onto big image
		if (leftBoard!=null) {
		int bLX = 5;
		int bLY = 5;
		String[] s0 = leftBoard.toString().split("\\n");	
		for (int j=0;j<s0.length;j++) { //for each part of the sub image
			tempImgSlices[bLY+j] = insert(s0[j], tempImgSlices[bLY+j], bLX);
		}}
		if (rightBoard!=null) {
		int bRX = 72;
		int bRY = 5;
		String[] s2 = rightBoard.toString().split("\\n");	
		for (int j=0;j<s2.length;j++) { //for each part of the sub image
			tempImgSlices[bRY+j] = insert(s2[j], tempImgSlices[bRY+j], bRX);
		}}
		
		//go through each image and add together onto big image
		for (int i=0;i<imgs.size();i++) { //for each sub image
			int x = locX.get(i);
			int y = locY.get(i);
			String[] s1 = imgs.get(i);
						
			for (int j=0;j<s1.length;j++) { //for each part of the sub image
				tempImgSlices[y+j] = insert(s1[j], tempImgSlices[y+j], x);
			}
		}
		
		//print full image
		String tempImg = "";
		for (String el : tempImgSlices) {
			tempImg+=el;
			tempImg+="\n";
		}
		tempImg=tempImg.substring(0,tempImg.length()-1);
		return tempImg;
	}
	
	//print composite text image of GUI
	public static void print() {
		//clear last image
		for (int i=0;i<50;i++) {
			System.out.println();
		}
		System.out.println(getImage());
		System.out.print("| Enter input: ");
	}
	
	
}

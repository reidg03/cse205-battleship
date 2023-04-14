import java.util.ArrayList;
//create text based images. add text strings or string arrays at coords,
//which get layered and combined into 1 composite image

public class Image {
	
	private ArrayList<String[]> imgs = new ArrayList<>();
	private ArrayList<Integer> locX = new ArrayList<>();
	private ArrayList<Integer> locY = new ArrayList<>();
	private int w;
	private int h;
	
	//constructors
	public Image(int w, int h) {
		this.w=w;
		this.h=h;
	}
	public Image() {
		this(50,100);
	}
	
	//add text image at coords
	public int addImg(String[] img1, int x, int y) {
		imgs.add(img1);
		locX.add(x);
		locY.add(y);
		return imgs.size()-1;
	}
	public int addImg(String img1, int x, int y) {
		String[] tempStr = new String[1];
		tempStr[0] = img1;
		imgs.add(tempStr);
		locX.add(x);
		locY.add(y);
		return imgs.size()-1;
	}
	
	//character at coords of composite image
	public String charAt(int x, int y) {
		return getImage().substring(y*w+y+x, y*w+y+x+1);
		//coords start at 0,0
	}
	
	//remove string image from list of images
	public String[] removeAt(int index) {
		String[] removed = imgs.get(index);
		imgs.remove(index);
		locX.remove(index);
		locY.remove(index);
		return removed;
	}
	
	//remove last string image
	public String[] removeLast() {
		String[] removed = imgs.get(imgs.size()-1);
		int last = imgs.size()-1;
		imgs.remove(last);
		locX.remove(last);
		locY.remove(last);
		return removed;
		
	}
	
	//used to place sub images into final one
	private String insert(String small, String big, int x) {
		return big.substring(0,x) + small + big.substring(x+small.length());
	}
	
	public int getImageListSize() {
		return imgs.size();
	}
	
	//return final composite image as string
	public String getImage() {

		//create default empty image
		String[] tempImgSlices = new String[h];
		for (int j=0;j<h;j++) {	
			tempImgSlices[j]="";
			for (int i=0;i<w;i++) {
				tempImgSlices[j]+="#";
			}
		}
		
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
	
	//print final image with space above
	public void print() {
		//clear last image
		for (int i=0;i<50;i++) {
			System.out.println();
		}
		System.out.print(getImage());
	}
	
	
}

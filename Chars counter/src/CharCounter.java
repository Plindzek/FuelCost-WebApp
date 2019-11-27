import java.util.Arrays;

public class CharCounter {
    public static void main(String[] args) {
	String a = "";
	System.out.println("Cz�stotliwo�� wyst�powania znak�w w ci�gu: " + a);

	char[] chars = new char[a.length()];
	a.getChars(0, a.length(), chars, 0);
	Arrays.sort(chars);
	
	boolean areTheSame;
	int count = 1;

	for (int i = 0; i < chars.length; i++) {

	    switch (chars.length - i) {
	    case 1:
		areTheSame = (chars[i] == chars[i - 1]);
		if (areTheSame) {

		    System.out.println(chars[i] + " wyst�puje " + count + " razy.");
		} else {
		    System.out.println(chars[i] + " wyst�puje 1 raz.");
		}
		break;

	    default:
		areTheSame = (chars[i] == chars[i + 1]);

		if (areTheSame)
		    count++;
		else {
		    System.out.println(chars[i] + " wyst�puje " + count + " razy.");
		    count = 1;
		}
	    }
	}
    }
}

package cab.game.rain.graphics;

public class Font {
	
	private static SpriteSheet font = new SpriteSheet("/path/file.format", 16);//need a font sheet
	private static Sprite[] characters = Sprite.split(font);
	
	private static String charIndex = //
			"ABCDEFGHIJLM" + //
			"NOPQRSTUVWXYZ" + //
			"abcdefghijklm" + //
			"nopqrstuvwxyz" + //
			"0123456789.,'" + //
			";:!@#$%&*()-+";
	
	public Font(){
		
	}
	
	public void render(int x, int y, String text, Screen screen){
		render(x, y, 0, 0, text, screen);
	}
	
	public void render(int x, int y, int color, String text, Screen screen){
		render(x, y, 0, color, text, screen);
	}
	
	public void render(int x, int y, int spacing, int color, String text, Screen screen){
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i<text.length(); i++){
			xOffset += 16 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if(currentChar == 'g' || currentChar == 'y'){yOffset = 4;}
			if(currentChar == '\n'){
				xOffset = 0;
				line ++;
				}
			//>>>>>>System.out.print("Characters: " + currentChar + " ");//for trouble shooting
			int index = charIndex.indexOf(currentChar);
			//>>>>>>System.out.print("Index of each character: " + index + " ");//for trouble shooting
			if(index == 1){continue;}
			screen.renderTextCharacter(x + xOffset, y + line * 20 + yOffset, characters[index], color, false);
		}
		//>>>>>>>System.out.println();//skips down to next line after for loop in done
	}

}



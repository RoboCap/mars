package pl.robocap.mars.kartograf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * LEGO Martian (cartography scenario) map rendering simple App
 */
public class App extends JPanel {

	private static final int MAP_HEIGHT = 600;
	private static final int MAP_WIDTH = 600;
	private static final long serialVersionUID = 4933019862541937022L;
	
	private final List<List<Color>> legoMap;

	public App(List<List<Color>> legoMap) {
		this.legoMap = legoMap;
	}

	public void paint(Graphics g) {
		Image img = drawMap();
		g.drawImage(img, 20, 20, this);
	}

	private Image drawMap() {
		BufferedImage bufferedImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();

		int factorY = MAP_WIDTH / legoMap.get(0).size();
		int factorX = MAP_HEIGHT / legoMap.size();
		
		for (int i = 0; i < legoMap.size(); i++) 
			for (int j = 0; j < legoMap.get(i).size(); j++) {
				Color color = legoMap.get(i).get(j);
				g.setColor(color);
				g.fillRect(i * factorX + 1, j * factorY + 1, factorX, factorY);
			}

		return bufferedImage;
	}

	public static void main(String[] args) throws IOException {
		List<List<Color>> legoMap = new LegoMapReader().readLegoMap(FileSystems.getDefault().getPath(args[0]));
		JFrame frame = new JFrame();
		frame.getContentPane().add(new App(legoMap));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAP_WIDTH + 50, MAP_HEIGHT + 50);
		frame.setVisible(true);
	}
}

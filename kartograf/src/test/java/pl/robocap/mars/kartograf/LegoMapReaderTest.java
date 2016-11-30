package pl.robocap.mars.kartograf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Color;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class LegoMapReaderTest {

	private LegoMapReader mapReader = new LegoMapReader();

	@Test
	public void readSimpleMap() throws Exception {
		List<List<Color>> readLegoMap = mapReader.readLegoMap(Paths
				.get(ClassLoader.getSystemResource("simple.map").toURI()));
		assertFalse(readLegoMap.isEmpty());
		assertEquals(4, readLegoMap.size());
		assertAll("colors",
				() -> assertEquals(Color.RED, readLegoMap.get(0).get(0)),
				() -> assertEquals(Color.RED, readLegoMap.get(0).get(1)),
				() -> assertEquals(Color.RED, readLegoMap.get(1).get(0)),
				() -> assertEquals(Color.RED, readLegoMap.get(1).get(1)),
				() -> assertEquals(Color.GREEN, readLegoMap.get(2).get(0)),
				() -> assertEquals(Color.GREEN, readLegoMap.get(2).get(1)),
				() -> assertEquals(Color.BLUE, readLegoMap.get(3).get(0)),
				() -> assertEquals(Color.BLUE, readLegoMap.get(3).get(1)));				
	}
}

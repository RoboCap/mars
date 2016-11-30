package pl.robocap.mars.kartograf;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LegoMapReader {

	private static final String STOP = "Stop";
	private static final String ZYCIE = "Zycie";
	private static final String LOD = "Lod";
	private static final String SKALA = "Skala";

	private static Collector<String, List<List<String>>, List<List<String>>> splitBySeparator(
			Predicate<String> sep) {
		return Collector.of(
				() -> new ArrayList<List<String>>(Arrays
						.asList(new ArrayList<>())), (l, elem) -> {
					if (sep.test(elem)) {
						l.add(new ArrayList<>());
					} else
						l.get(l.size() - 1).add(elem);
				}, (l1, l2) -> {
					l1.get(l1.size() - 1).addAll(l2.remove(0));
					l1.addAll(l2);
					return l1;
				});
	}

	public List<List<Color>> readLegoMap(Path path) throws IOException {
		List<List<String>> rawMap = readRawMap(path);
		List<List<String>> normalizedMap = normalize(rawMap);

		return convertToColorMap(normalizedMap);
	}

	private List<List<Color>> convertToColorMap(List<List<String>> normalizedMap) {
		return normalizedMap.stream().parallel()
				.map(l -> l.stream().parallel().map(s -> {
					switch (s) {
					case SKALA:
						return Color.RED;
					case LOD:
						return Color.BLUE;
					case ZYCIE:
						return Color.GREEN;
					default:
						return Color.WHITE;
					}
				}).collect(Collectors.toList())).collect(Collectors.toList());
	}

	private List<List<String>> normalize(List<List<String>> rawMap) {
		List<List<String>> notEmptyMap = rawMap.stream().parallel()
				.filter(l -> !l.isEmpty()).collect(Collectors.toList());
		int minPathLength = notEmptyMap.stream().parallel()
				.min((l1, l2) -> l1.size() - l2.size()).get().size();
		return notEmptyMap
				.stream()
				.parallel()
				.map(l -> l.stream().parallel().limit(minPathLength)
						.collect(Collectors.toList()))
				.collect(Collectors.toList());
	}

	private List<List<String>> readRawMap(Path path) throws IOException {
		return Files.lines(path).parallel()
				.collect(splitBySeparator(e -> e.equals(STOP)));
	}

}

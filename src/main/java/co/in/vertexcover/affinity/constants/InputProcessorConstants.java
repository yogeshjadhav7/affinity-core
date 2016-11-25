package co.in.vertexcover.affinity.constants;

import java.util.regex.Pattern;

public class InputProcessorConstants {

	final public static String INPUT_DELIMITER = ",";
	final public static Pattern INPUT_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
}

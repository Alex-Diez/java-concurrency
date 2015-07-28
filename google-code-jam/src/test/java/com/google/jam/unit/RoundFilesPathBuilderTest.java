package com.google.jam.unit;

import java.nio.file.Path;

import com.google.jam.RoundPathBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RoundFilesPathBuilderTest {

	public static final String PATH_TO_TEST_FILE = "src/test/files/A-small-test.in";

	@Test
	public void testBuildTestPath()
			throws Exception {
		final RoundPathBuilder pathBuilder = new RoundPathBuilder("test", 'A', "small", "test");
		final Path path = pathBuilder.build();
		assertThat(true, is(path.endsWith(PATH_TO_TEST_FILE)));
	}
}

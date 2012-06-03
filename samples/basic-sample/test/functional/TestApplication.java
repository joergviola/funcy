package functional;

import org.junit.Test;

import pages.IndexPage;

import funcy.FunctionalTest;

public class TestApplication extends FunctionalTest {

	@Test
	public void testIndex() {
		IndexPage indexPage = new IndexPage();
		indexPage = indexPage.save("Perform Test");
		indexPage = indexPage.clickReload();
	}
}

package funcy.pages;

import play.mvc.Result;
import funcy.Form;
import funcy.Page;

public class IndexPage extends Page {

	public IndexPage() {
		this(Page.get("/"));
	}

	public IndexPage(Result result) {
		super(result, "/");
	}

	public IndexPage save(String msg) {
		Form form = form(0);
		form.set("test", msg);
		return new IndexPage(form.submitName("Save"));
	}

	public IndexPage clickReload() {
		return new IndexPage(clickName("Reload"));
	}
}

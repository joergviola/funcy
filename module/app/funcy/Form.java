package funcy;

import java.util.HashMap;

import junit.framework.Assert;

import org.jsoup.nodes.Element;

import play.mvc.Result;

public class Form {

	public final Tag formTag;
	private final HashMap<String, String> params = new HashMap<String, String>();
	private final HashMap<String, Tag> inputs = new HashMap<String, Tag>();

	public Form(Tag formTag) {
		this.formTag = formTag;
		getParams();
	}

	private void getParams() {
		for (Tag input : formTag.getElementsByTag("input")) {
			String name = input.attr("name");
			String value = input.attr("value");
			String type = input.attr("type");
			if (!"submit".equals(type))
				addInput(input, name, value);
		}
		for (Tag input : formTag.getElementsByTag("textarea")) {
			String name = input.attr("name");
			String value = input.text();
			addInput(input, name, value);
		}
		for (Tag input : formTag.getElementsByTag("select")) {
			String name = input.attr("name");
			String value = input.text();
			addInput(input, name, value);
		}
	}

	public void addInput(Tag input, String name, String value) {
		params.put(name, value);
		inputs.put(name, input);
	}

	public void set(String name, String value) {
		if (!params.containsKey(name))
			Assert.fail("Attempt to set unknown input field " + name + "="
					+ value);
		params.put(name, value);
	}

	public Result submitName(String value) {
		Tag submit = formTag.getElementsByTag("input").attr("type", "submit")
				.attr("value", value).first();
		if (submit != null) {
			String name = submit.attr("name");
			params.put(name, value);
		} else {
			TagList buttons = formTag.getElementsByTag("button").text(value);
			Assert.assertEquals("No button found in form with text " + value,
					1, buttons.size());
		}
		Tag form = formTag.getElementsByTag("form").first();
		String uri = form.attr("action");
		String method = form.attr("method");
		return submitTo(method, uri);
	}

	public void set(String name, String[] values) {
		params.put(name, values[0]);
	}

	public void assertFieldError(String name, String message) {
		Tag element = inputs.get(name);
		Tag span = element.parent();
		Assert.assertTrue("Field in error is not marked with error CSS class",
				span.attr("class").indexOf("error") > -1);
		Tag errorMsg = span.children().last();
		Assert.assertTrue(
				"Message for Field in error is not marked with error CSS class",
				errorMsg.attr("class").indexOf("error") > -1);
		Assert.assertTrue("Wrong Message for Field in error", errorMsg.text()
				.contains(message));
	}

	public Result submitTo(String method, String uri) {
		if ("GET".equalsIgnoreCase(method))
			return Page.get(uri, params);
		else
			return Page.post(uri, params);
	}

	@Override
	public String toString() {
		return formTag.toString();
	}
}

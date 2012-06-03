package funcy;

import static org.fest.assertions.Assertions.assertThat;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Element;

import play.i18n.Lang;
import play.mvc.Result;
import play.templates.ScalaTemplateCompiler.Template;

public class Tag {

	private final Element element;

	public Tag(Element element) {
		this.element = element;
	}

	public String attr(String name) {
		return element.attr(name);
	}

	public String text() {
		return element.text();
	}

	public String html() {
		return element.html();
	}

	public TagList getElementsByTag(String tag) {
		return new TagList(element.getElementsByTag(tag));
	}

	public TagList getElementsByClass(String string) {
		return new TagList(element.getElementsByAttributeValue("class", "box"));
	}

	public Tag parent() {
		return new Tag(element.parent());
	}

	public TagList children() {
		return new TagList(element.children());
	}

	public Result click() {
		return Page.get(attr("href"));
	}

	public void contains(String tag, String body, int size) {
		Assert.assertEquals("Bad tag count <" + tag + "> by body " + body,
				size, getElementsByTag(tag).text(body).size());
	}

	public String clickLinkName(String name, int i) {
		TagList list = getElementsByTag("a").text(name);
		if (i >= list.size())
			return null;
		Tag link = list.get(i);
		String href = link.attr("href");
		return href;
	}

	public String clickButtonName(String name, int i) {
		TagList list = getElementsByTag("button").text(name);
		if (i >= list.size())
			return null;
		Tag button = list.get(i);
		String href = button.attr("onClick");
		Pattern pattern = Pattern.compile(".*location.href='([^']*)'.*");
		Matcher matcher = pattern.matcher(href);
		matcher.find();
		return matcher.group(1);
	}

	public Result clickName(String name) {
		return clickName(name, 0);
	}

	public Result clickName(String name, int i) {
		String href = clickLinkName(name, i);
		if (href == null) {
			href = clickButtonName(name, i);
		}
		Assert.assertNotNull("Link not found by name: " + name, href);
		return Page.get(href);
	}

	public Form form(int no) {
		TagList forms = getElementsByTag("form");
		return new Form(forms.get(no));
	}

	public void contains(String string) {
		Assert.assertFalse("content does not include '" + string + "'", html()
				.indexOf(string) == -1);
	}

	public void containsMessage(String code) {
		String string = play.i18n.Messages.get(Lang.forCode("de"), code);
		string = StringEscapeUtils.escapeHtml(string);
		Assert.assertFalse("content does not include message '" + code + "'",
				html().indexOf(string) == -1);
	}

	public void contains(String tag, String body) {
		assertThat(getElementsByTag(tag).text(body)).isNotEmpty();
	}

	@Override
	public String toString() {
		return html();
	}
}

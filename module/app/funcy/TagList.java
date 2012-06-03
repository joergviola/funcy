package funcy;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TagList implements Iterable<Tag> {

	private final List<Tag> list;

	public TagList() {
		this.list = new ArrayList<Tag>();
	}

	public TagList(Elements list) {
		this();
		for (Element element : list) {
			add(new Tag(element));
		}
	}

	@Override
	public Iterator<Tag> iterator() {
		return list.iterator();
	}

	public TagList attr(String name, String value) {
		TagList filtered = new TagList();
		for (Tag tag : list) {
			String attr = tag.attr(name);
			if (value.equals(attr))
				filtered.add(tag);
		}
		return filtered;
	}

	private void add(Tag tag) {
		this.list.add(tag);
	}

	public TagList text(String text) {
		TagList filtered = new TagList();
		for (Tag tag : list) {
			if (text.equals(tag.text()))
				filtered.add(tag);
		}
		return filtered;
	}

	public Tag first() {
		if (list.size() == 0)
			return null;
		else
			return list.get(0);
	}

	public Tag get(int no) {
		return list.get(no);
	}

	public int size() {
		return list.size();
	}

	public Tag last() {
		if (list.size() == 0)
			return null;
		else
			return list.get(list.size() - 1);
	}

}

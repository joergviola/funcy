package funcy;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.header;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.routeAndCall;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.Test;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import play.i18n.Lang;
import play.mvc.Http.HeaderNames;
import play.mvc.Result;
import play.test.FakeRequest;

public class Page extends Tag {

	public static String uri;
	private static String cookies;

	static private boolean log = "true".equals(System.getProperty("log"));
	public String location;

	public Page(Result result, String uriPattern) {
		super(Jsoup.parse(contentAsString(result)));
		Assert.assertTrue("result for page was null: " + uriPattern,
				result != null);
		assertMatches("Page URI", uri, uriPattern);
		this.location = uri;
	}

	public String changeURI(String uri) {
		int slash = location.lastIndexOf('/');
		return location.substring(0, slash) + "/" + uri;
	}

	private void assertMatches(String msg, String s, String pattern) {
		if (!s.matches(pattern))
			Assert.fail(msg + ": " + s + " does not match " + pattern);
	}

	public static Result get(String uri) {
		if (log)
			System.out.println("GET " + uri);
		Page.uri = uri;
		Result result = process(fakeRequest(GET, uri));
		return result;
	}

	private static Result process(FakeRequest fakeRequest) {
		if (cookies != null)
			fakeRequest = fakeRequest.withHeader(HeaderNames.COOKIE, cookies);
		Result result = routeAndCall(fakeRequest);
		String c = header(HeaderNames.SET_COOKIE, result);
		if (StringUtils.isNotEmpty(c))
			cookies = c;
		// System.out.println(headers(result));
		String location = redirectLocation(result);
		if (location != null) {
			result = get(location);
		}
		return result;
	}

	public static Result get(String uri, Map<String, String> params) {
		StringBuilder b = new StringBuilder(uri);
		String sep = "?";
		for (Entry entry : params.entrySet()) {
			b.append(sep);
			sep = "&";
			b.append(entry.getKey());
			b.append("=");
			b.append(entry.getValue());
		}
		return get(b.toString());
	}

	public static Result post(String uri, HashMap<String, String> params) {
		if (log)
			System.out.println("POST " + uri + " " + params.toString());
		Page.uri = uri;
		Result result = process(fakeRequest(POST, uri).withFormUrlEncodedBody(
				params));
		return result;
	}

	public static void clearSession() {
		cookies = null;
	}

}

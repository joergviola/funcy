funcy
=====

Page Driven Functional Test for Play 2.0. 

Introduction
------------

Functional Test in Play 2.0 are the best way to test web applications:

* In standard web applications, you simply do not have really complex business logic. There is no point in using unit tests.
* Selenium tests are hard to setup (even with Plays support), require explizit fixtures and they are very slow - too slow to be run before each commit.

Example: Booking a ticket is fairly easy: Simply write a row into a database.
Whats the point of writing a unit test for that?

Building the booking form, receiving the request, validating user input, creating the ticket,
sending the confirmation mail and displaying the confirmation page, on the other hand, is quite a
complex process.

But testing it through selenium is hard and , if you run into the trap of recording your test cases,
very prone to changes of the underlying software.

Using Page Driven Play 2.0 Functional Tests provided by funcy, you can
* write your tests as simple unit tests
* check results by accessing resulting web page doms or the database directly
* run them very fast against an in-memory database.

Consider this example:

	@Test
	public void testBooking() {
		IndexPage indexPage = new IndexPage();
		EventPage eventPage = indexPage.clickEvent("Sidney Opera");
		BookingPage bookingPage = eventPage.book("2012/05/07");
		ConfirmationPage confirmationPage = bookingPage.book();

		List<Booking> bookings = Booking.all();
		Assert.assertEquals("#bookings", 1, bookings.size());
	}

Page
----

Subclass `Page` for each page of your application. Consider the following example:

	package pages;
	
	import play.mvc.Result;
	import funcy.Form;
	import funcy.Page;
	
	public class IndexPage extends Page {
	
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
	
Typically a `Page` is constructed from a `play.mvc.Result`. The super-constructor expects 
a regexp additionally. The current URL is matched against this URL to assert whether
the test is on the right page.

Your `Page` class should exhibit a public method for each action a user can perform on 
the page. That is the core of the Page Driver pattern: Create an abstract model of the page 
under test so that changes do not affect the test at all or can be represented by a change
of the Page Drivers public contract, which is obvioulsy under good control.

`Page` has a lot of methods available to perform actions (most notably, `clickName()`)
and to access it contents (e.g. `form()`, `getElementsByTag()`). 

The latter are inherited from `Tag`, which provides access to the `Page`'s DOM. 
JSoup is used for HTML parsing. Using `Tag`, you can search for DOM elements in a flexible
way. A good example is the implementation of the link part of `clickName()`:

	public String clickLinkName(String name, int i) {
		TagList list = getElementsByTag("a").text(name);
		if (i >= list.size())
			return null;
		Tag link = list.get(i);
		String href = link.attr("href");
		return href;
	}

FunctionalTest
--------------

Let your test classes inherit `FunctionalTest`. This way, a `fakeApplication` is started, 
provisioned with `application.conf` and stopped automatically.

In your test methods, use the `Page`s and their public API:

	@Test
	public void testIndex() {
		IndexPage indexPage = new IndexPage(Page.get("/"));
		indexPage = indexPage.save("Perform Test");
		indexPage = indexPage.clickReload();
	}

And remember, Play! 2.0 Functional Tests give you full access to the database!	
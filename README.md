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

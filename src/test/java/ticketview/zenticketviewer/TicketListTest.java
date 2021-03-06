package ticketview.zenticketviewer;

import static org.junit.Assert.*;
import org.junit.Test;

import ticketview.zenticketviewer.controller.TicketList;

/*Unit tests for TicketList class methods */
public class TicketListTest {

	//Tested Method: listTickets()
	@Test
	//Valid parameters -> SUCCESS
	public void listTicketsWithValidParameters() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1, 25);
		//Verification
		assertEquals("SUCCESS",result);
		assertEquals(ticketList.ticketArray.length, 25);
	}
	
	@Test 
	//perPage = total count -> SUCCESS
	public void listTicketsWithPerPageMaximum() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1, 101);
		//Verification
		assertEquals("SUCCESS",result);
		assertEquals(ticketList.ticketArray.length, 25);
	}
	
	@Test
	//PerPage = exceed total count -> SUCCESS
	public void listTicketsWithPerPageExceedTotalTickets() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1, 103);
		//Verification
		assertEquals("SUCCESS",result);
		assertEquals(ticketList.ticketArray.length, 25);
	}
	
//	@Test
//	/*
//	 * Page = 4, perPage = exceed the actual number of pages exist in that page -> SUCCESS 
//	 * While zapping trough the tickets (30 in total) the last page (page 2) display only 5 record 
//	 */
//	public void listTicketsWithPerPageExceedLimit() {
//		TicketList ticketList = new TicketList();
//		//Execution
//		ticketList.listTickets(1, 25);
//		String result = ticketList.listTickets(2, 25);
//		//Verification
//		assertEquals("SUCCESS",result);
//		assertEquals(ticketList.ticketArray.length, 5);
//	}
	
	@Test
	//Page = 0 -> ERROR
	public void listTicketsWithPage0() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(0, 25);
		//Verification
		assertEquals("ERROR: page number must be > 0",result);
	}
	
	@Test
	//Page = -1 -> ERROR
	public void listTicketsWithPageMinus() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(-1, 25);
		//Verification
		assertEquals("ERROR: page number must be > 0",result);
	}
	
	@Test
	//PerPage = 0 -> ERROR
	public void listTicketsWithPerPage0() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1, 0);
		//Verification
		assertEquals("ERROR: number of records on a per-request must be > 0",result);
	}
	
	@Test
	//PerPage = -1 -> ERROR
	public void listTicketsWithPerPageMinus() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1, -1);
		//Verification
		assertEquals("ERROR: number of records on a per-request must be > 0",result);
	}
	
	@Test
	//No records found  -> ERROR
	public void listTicketsWithPageNotExist() {
		TicketList ticketList = new TicketList();
		//Execution
		String result = ticketList.listTickets(1234, 2);
		//Verification
		assertEquals("Error: no records found",result);
	}
	
	@Test
	/*
	 * No records found -> ERROR Happy test
	 * When the user navigate beyond random page (page 8888) the pages has no records 
	 */
	public void listTicketsWithoutRecords() {
		TicketList ticketList = new TicketList();
		//Execution
		ticketList.listTickets(1, 25);
		ticketList.listTickets(2, 5);
	
		String result = ticketList.listTickets(8888, 25);
		//Verification
		assertEquals("Error: no records found",result);
	}
}

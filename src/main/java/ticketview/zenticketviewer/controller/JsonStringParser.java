package ticketview.zenticketviewer.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ticketview.zenticketviewer.model.Ticket;

/*This class responsible for parsing a given Json content and return the object parsed with data */
public class JsonStringParser {
	
	//===== Variables =====
	public static final int MAX = 25;
	public static String errorMessage;
	
	//===== Public Methods =====
	/**
	* This method parse the JSON content of a single ticket.
	* @param jsonContent - content in JSON.
	* @return Ticket - Ticket object (contain the tickets properties).
	* 		 null - in case of parsing failure
	*/
	public Ticket parseSingleTicket(String jsonContent)
	{
		JSONParser jsonParser = new JSONParser();
		
		try 
		{
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonContent);
			//Handle a structure into the json object
			JSONObject ticketStructure = (JSONObject) jsonObject.get("ticket");
			//No ticket exist in the response
			if(ticketStructure.isEmpty())
			{
				errorMessage = "Error: no record found";
				return null;
			}
			return parseTicketStructure(ticketStructure);
		} 
		catch (Exception e) 
		{
			errorMessage = "Internal Error: internal error occurs while processing your request";
			return null;	
		}
	}
	
	/**
	* This method parse the JSON content of ticket list.
	* The method limit the list size to 25 per page.
	* @param jsonContent - content in JSON.
	* @return TicketList - TicketList object (contain the ticket array and navigation flag)
	* 		null - in case of parsing failure.
	*/
	public TicketList parseTicketsList(String jsonContent)
	{
		TicketList ticketList = new TicketList();
		JSONParser jsonParser = new JSONParser();
		
		try
		{
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonContent);
			//Get tickets array from json object
			JSONArray ticketArray = (JSONArray)  jsonObject.get("tickets");
			//No tickets exist in the response
			if(ticketArray.isEmpty())
			{
				errorMessage = "Error: no records found";
				return null;
			}
			//Page contain list size < 25
			if(ticketArray.size()<MAX)
				ticketList.ticketArray = new Ticket[ticketArray.size()];
			else
				ticketList.ticketArray = new Ticket[MAX];
			//Take tickets elements from array, limit to 25 items per page
			for(int i=0; i<ticketArray.size() && i<MAX; i++)
			{
				//Parse single ticket within the array
				JSONObject ticketStructure = (JSONObject)ticketArray.get(i);
				Ticket t = parseTicketStructure(ticketStructure);
				if (t == null)
				{
					errorMessage = "Error: no records found";
					return null;
				}
				ticketList.ticketArray[i]  = t;
			}
			parseNavigationFlags(jsonObject,ticketList);
			
			return ticketList;
		}
		catch (Exception e)
		{
			errorMessage = "Internal Error: internal error occurs while processing your request";
			return null;
		}
	}
	
	//===== Private Methods =====
	/**
	* This method parse the JSON object of a single ticket structure.
	* @param jsonContent - content in JSON.
	* @return Ticket - Ticket object (contain the tickets properties).
	* 		 null - in case of parsing failure
	*/
	private Ticket parseTicketStructure(JSONObject ticketStructure)
	{
		JSONArray jsonArray;
		Ticket ticket = new Ticket();
		
		if(ticketStructure == null)
			return null;
		
		ticket.setId((long)ticketStructure.get("id"));
		
		//Handle a structure into the ticket object
		ticket.setCreatedAt((String)ticketStructure.get("created_at"));
		ticket.setType((String)ticketStructure.get("type"));
		ticket.setSubject((String)ticketStructure.get("subject"));
		ticket.setDescription((String) ticketStructure.get("description"));
		ticket.setPriority((String) ticketStructure.get("priority"));
		ticket.setStatus((String) ticketStructure.get("status"));
		ticket.setRecipient((String) ticketStructure.get("recipient"));
		ticket.setRequesterId((long) ticketStructure.get("requester_id"));
		if(ticketStructure.get("submitter_id") != null)
			ticket.setSubmitterId((long) ticketStructure.get("submitter_id"));
		if (ticketStructure.get("assignee_id")!= null)
			ticket.setAssigneeId((long) ticketStructure.get("assignee_id"));
		
		return ticket;
	}
	
	/**
	* This method parse the navigation flag in tickets list structure.
	* @param jsonObject - jsonContent - content in JSON.
	* 		ticketList - a given ticket list.
	*/
	private void parseNavigationFlags(JSONObject jsonObject, TicketList ticketList)
	{
		String nextPageUrl = (String) jsonObject.get("next_page");
		String previousPageUrl = (String) jsonObject.get("previous_page");
		
		//Copy to TicketList object
		if(nextPageUrl == null)
			ticketList.hasNext = false;
		else
			ticketList.hasNext = true;
		if(previousPageUrl == null)
			ticketList.hasPrevious = false;
		else
			ticketList.hasPrevious = true;
	}
}
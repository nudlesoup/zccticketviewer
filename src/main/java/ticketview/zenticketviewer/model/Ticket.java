package ticketview.zenticketviewer.model;

import java.lang.Object;
import java.util.ArrayList;

import ticketview.zenticketviewer.controller.HttpRequests;
import ticketview.zenticketviewer.controller.JsonStringParser;

/*This class represents a single ticket in the system and provide an API compatible with Ticket */
public class Ticket {
	
	//===== Variables =====
	public static final String ENDPOINT = "/api/v2/tickets/";
	private long id; //ID is assigned automatically when creating ticket (READ ONLY)
    private String type; //The type of this ticket, i.e. "problem", "incident", "question" or "task"
    private String subject; //The value of the subject field for this ticket
    private String description; //The first comment on the ticket (READ ONLY)
    private String priority; //Priority, defines the urgency with which the ticket should be addressed: "urgent", "high", "normal", "low"
    private String status; //The state of the ticket, "new", "open", "pending", "hold", "solved", "closed"
    private String recipient; //The original recipient e-mail address of the ticket
    private long requesterId; //The user who requested this ticket (MANDATORY)
    private long submitterId; //The user who submitted the ticket; The submitter always becomes the author of the first comment on the ticket.
    private long assigneeId; //	What agent is currently assigned to the ticket
    private String createdAt; //When this record was created (READ ONLY)
   
    //===== Public Methods =====
	/**
	* This method display a number of ticket properties (Subject, CreatedAt, Description, RequesterId, submitterId,assigneeId)
	* by creating request using Zendesk API, call send GET http request, call JSON parser and display the details of a single ticket.
	* @param id - the id of the requested ticket.
	* @return String - "SUCCESS" or relevant error message.
	* @exception id<=0.
	*/
    public String showTicket(int id)
    {
    	if(id<=0) {
			return "ERROR: ID must be > 0";}
    	
    	System.out.println("\nProcessing your request, please wait...");
    	HttpRequests httpRequest = new HttpRequests();
    	String url = ENDPOINT + id + ".json";
    	String response = httpRequest.sendGet(url, null);
    	//Request success - 200 or 300 range
    	if(HttpRequests.responseCode> 199 && HttpRequests.responseCode < 400)
    	{
    		//Parse json response and display ticket
			JsonStringParser jsonParser = new JsonStringParser();
			Ticket ticket = jsonParser.parseSingleTicket(response);
			if(ticket == null)
				return JsonStringParser.errorMessage;
			
			ticket.displayTicketInformation();
			return "SUCCESS";
    	}
    	
    	return httpRequest.printErrorMessage(HttpRequests.responseCode);
    }
    
    //===== Private Methods =====
    /**
	* This method print the Ticket details to the user.
	*/
    private void displayTicketInformation()
    {
    	//Display title
    	System.out.format("\n %66s \n", "** Displaying ticket number " + this.getId() + " **");
    	System.out.println(" ------------------------------------------------------------------------------------------------------------------------------------");
    	//Subject align left, createdAt align right
    	System.out.format("| Subject: %-82s  Created at: %-25s |\n", this.getSubject(), this.getCreatedAt());
    	System.out.format("|%-132s|\n| Description: %-116s  |\n", " ","");
    	//Handle Description content
    	int length=this.getDescription().length();
    	//Description is short, and fit 1 row
    	if(length <= 130)
    		System.out.format("| %-130s |\n", this.getDescription());
    	//Description is too long, need text wrapping
    	ArrayList<String> descWrapped = WrapTextFullWords (this.getDescription(), 130);
    	for (int i=0; i< descWrapped.size(); i++)
    	{
    		System.out.format("| %-130s |\n", descWrapped.get(i));
    	}

    	System.out.format("|%-132s|\n| Requester id: %-28s  Submitter id: %-33s  Assignee id: %-24s |\n", "", this.getRequesterId(), this.getSubmitterId(), this.getAssigneeId());
    	System.out.println(" ------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    /**
	* This method wrap the text showing full words, used to wrap the description content.
	*/
    private ArrayList<String> WrapTextFullWords (String str, int maxLength)
    {
    	ArrayList<String> result = new ArrayList<String>();
    	
    	//Remove new line within the description
    	str = str.replace("\n", "");
    	String[] wordsArray = str.split(" ");
    	String line = "";
    	//Wrap text
    	for(int i=0; i<wordsArray.length; i++)
    	{
    		//Words can be added to the line
    		if(line.length() + wordsArray[i].length() <= maxLength-1)
    			line = line.concat(wordsArray[i] + " ");
    		//Line reached max length
    		else
    		{
    			i--; //Skip the word that not inserted to the line
    			result.add(line);
    			line = "";
    		}
    	}
    	//Add the last line
    	result.add(line); 
    	
    	return result;
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(long requesterId) {
		this.requesterId = requesterId;
	}

	public long getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(long submitterId) {
		this.submitterId = submitterId;
	}

	public long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(long assigneeId) {
		this.assigneeId = assigneeId;
	}
}
    





import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class assign4
 */
@WebServlet("/assign4")
public class assign4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public assign4() {
        
    }

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();
	ArrayList<Integer> row = new ArrayList<Integer>();
	ArrayList<Integer> column = new ArrayList<Integer>();
	ArrayList<Integer> score = new ArrayList<Integer>();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//read text file that stores questions and answers from previous assignment:
			//read text file from CSLAB Ubuntu server /cslab/home/bnh5et/public_html/projectName

		//TODO change file so it's question on one line, corresponding answer on the next
		
		URL url = new URL("http://plato.cs.virginia.edu/~bnh5et/HW/data/data.txt");
		Scanner scanner = new Scanner(url.openStream());
		
		//count number of lines in the data doc
		int count = 0;
		while (scanner.hasNext())
		{
			count++;
			scanner.next();			
		}
		
		Scanner reader = new Scanner(url.openStream());
		//account for there existing both questions and answers
		count = count / 2;
		//add the questions and answers to question and answer ArrayLists
		for (int i = 0; i < count; i++)
		{
			//if (!reader.nextLine().equals("Question: "))
			questions.add(reader.nextLine());
			answers.add(reader.nextLine());
		}
		
		//now we have all the questions and answers saved
	
		//display html form that has: 
			//title and team members' names
			//list of questions and answers
			//two fields where user can specify row and column
			//specify score (display this in the given row and column)
			//button that goes back to PHP data entry form from assignment 3 on the CSLAB server
			//"Create Game button"
		
		response.getWriter().append("Served at: ").append(request.getContextPath());	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//this is for the submit button
		
		try 
		{
			PrintWriter writer = new PrintWriter("submission.txt", "UTF-8");
			// TODO dummy strings for question, answer, row, column, score
			writer.println("Question" + "; " + "Answer" + "; " + "row" + "; " + "column" + "; " + "score" );
			writer.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Could not write to file");
		}
		
		//process form data submission
	    //take the list of questions, answers, rows, columns, scores
		//write these to a text file on your machine
			//can use comma or semicolon to separate each piece of data
		//http://www.cs.virginia.edu/~up3f/cs4640/inclass/simpleform.java
		
		//create jeopardy game and display it on screen
			//include button that allows user to go back to edit the question
			//just want a grid where each cell shows the score
			//can do a formhandler:  http://www.cs.virginia.edu/~up3f/cs4640/examples/servlet/formHandler.java
		try 
		{
			
			//figure out how many rows and columns we need
			int maxrows = 0;
			int maxcols = 0;
			for (int i = 0; i < row.size(); i++)
			{
				if (row.get(i) > maxrows)
				{
					maxrows = row.get(i);
				}
			}
			
			for (int i = 0; i < column.size(); i++)
			{
				if (column.get(i) > maxcols)
				{
					maxcols = column.get(i);
				}
			}
			
			
			
			response.setContentType("text/html");
			PrintWriter jeopardyWriter = response.getWriter();
		
			String str;
			Enumeration input = request.getParameterNames();
		
			jeopardyWriter.println("<html>");
			//TODO and so on, to create rest of jeopardy grid
			
			while (input.hasMoreElements())
			{
				str = (String) input.nextElement();
				if (!str.equalsIgnoreCase("submit"))
				{
					for (int i = 0; i < maxrows; i++)
					{
						//print right number of rows
						//jeopardyWriter.println("<tr>");
						//then, print right number of columns and add score
					}
					//add the things from input to the grid, only if it's not submit
					//we want the score in the grid, that is all
				}
			}
			//closing html tags to jeopardyWriter
			jeopardyWriter.println("</html>");
			jeopardyWriter.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Could not create jeopardy grid");
		}
		doGet(request, response);
	}

}

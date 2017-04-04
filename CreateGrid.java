import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class createGrid
 */
@WebServlet("/createGrid")
public class CreateGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();

	private static String LogoutServlet = "http://localhost:8080/Jeopardy/logout";
	private static String LoginServlet = "http://localhost:8080/Jeopardy/login";

	private String user;

	int gameID = 1;
	boolean updated = false;
	String[] row;
	String[] column;
	String[] score;
	int maxrows;
	int maxcols;
	int[][] scores;
	int maxGameID = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		user = (String) session.getAttribute("UserID");

		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);
		
		if(session.getAttribute("updated") != null){
			updated = (boolean) session.getAttribute("updated");

		}

		if (updated) {
			gameID = (Integer) session.getAttribute("GameNum");
			System.out.println("Updating: " + gameID);
		}

		//Making Questions and Answers Arrays
		URL url = new URL("http://plato.cs.virginia.edu/~bnh5et/HW/data/data.txt");
		Scanner scanner = new Scanner(url.openStream());

		int count = 0;
		while (scanner.hasNext()) {
			count++;
			scanner.next();
		}

		questions.clear();
		answers.clear();

		Scanner reader = new Scanner(url.openStream());
		count = count / 3;
		for (int i = 0; i < count; i++) {
			if (reader.hasNextLine()) {
				reader.nextLine();
				questions.add(reader.nextLine());
				answers.add(reader.nextLine());
			}
		}

		//Print Questions
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printQuestions(out);
	}

	public void printQuestions(PrintWriter out) {

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Jeopardy</title>");

		out.println("<style type=\"text/css\">");
		out.println("body {");
		out.println("	font-family: Verdana, Geneva, sans-serif;");
		out.println("	background-color: SlateGrey;");
		out.println("}");
		out.println("div {");
		out.println("	box-shadow: 0 0 5px;");
		out.println("	background-color: white;");
		out.println("	padding: 20px;");
		out.println("	margin: 40px;");
		out.println("	border-radius: 15px;");
		out.println("}");
		out.println("input { ");
		out.println("width: 15px; ");
		out.println("}");
		out.println("input.form, a {");
		out.println("  width: 100px;");
		out.println("  display: inline-block;");
		out.println("  background-color: maroon;");
		out.println("  border: 1px solid maroon;");
		out.println("  color: white;");
		out.println("  text-decoration: none;");
		out.println("  font-size: 12px;");
		out.println("  border-radius: 5px;");
		out.println("  padding: 5px;  		");
		out.println("} ");
		out.println("input.form:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("input[type=submit] {");
		out.println("	width: 50px;");
		out.println("	display: inline-block;");
		out.println("	background-color: maroon;");
		out.println("	border: 1px solid maroon;");
		out.println("	color: white;");
		out.println("	text-decoration: none;");
		out.println("	font-size: 12px;");
		out.println("	border-radius: 5px;");
		out.println("	padding: 5px;");
		out.println("}");
		out.println("input[type=submit]:hover {");
		out.println("	cursor: pointer;");
		out.println("}");
		out.println("");
		out.println("a:hover {");
		out.println("  cursor: pointer;");
		out.println("} ");
		out.println("#multChoice, #trueFalse, #shortAns { ");
		out.println("  display: none;");
		out.println("} ");
		out.println("button {");
		out.println("  border: 2px solid maroon;");
		out.println("  background-color: maroon;");
		out.println("  font-family: Verdana, Geneva, sans-serif;");
		out.println("  color: white;");
		out.println("  font-size: 15px;");
		out.println("  border-radius: 5px;");
		out.println("} ");
		out.println("button:hover {");
		out.println("  cursor: pointer;");
		out.println("  box-shadow: ");
		out.println("}");
		out.println("button:focus {");
		out.println("  outline: 0;");
		out.println("}");
		out.println("");
		out.println("");
		out.println("table {");
		out.println("  border-collapse: collapse;");
		out.println("  table-layout: fixed;");
		out.println("");
		out.println("}");
		out.println("table, th, td {");
		out.println("  border: 1px solid black;");
		out.println("}");
		out.println("td { ");
		out.println("  margin: 0 10px;");
		out.println("}");
		out.println("th {");
		out.println("  font-weight: bold;");
		out.println("  margin: 5px;");
		out.println("}");
		out.println("div.logout {");
		out.println("	padding: 0px;");
		out.println("	margin: -8px;");
		out.println("	border-radius: 0px;");
		out.println("	font-size: 12px;");
		out.println("}");
		out.println("table.logout {");
		out.println("	align: right;");
		out.println("	margin-right: 0;");
		out.println("	margin-left: auto;");
		out.println("	border: none;");
		out.println("}");
		out.println("table.logout td{");
		out.println("	border: none;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		out.println("	<div class=\"logout\">");
		out.println("		<table class=\"logout\">");
		out.println("			<tr>");
		out.println("				<td><p>Welcome, " + user + "!</p></td>");
		out.println("				<td></td>");
		out.println("				<td>");
		out.println("					<form class=\"logout\" action=\"" + LogoutServlet + "\" method=\"post\">");
		out.println("						<input style=\"width: 100px;\" type=\"submit\" value=\"Logout\"></input>");
		out.println("					</form>");
		out.println("				</td>");
		out.println("			</tr>");
		out.println("		</table>");
		out.println("	</div>");

		out.println("<center>");
		out.println("	<div id=\"mainpage\">");
		out.println("		<h1>Question Selector</h1>	");
		out.println("		<h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("		<p>List of questions and answers:  </p> ");
		out.println("    <form method=\"post\">");
		out.println("        <table style=\"text-align: center;\">");
		out.println("          <tr>");
		out.println("            <th style=\"width: 300px;\">");
		out.println("              Question/Answer ");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Row  ");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Column");
		out.println("            </th>");
		out.println("            <th style=\"width: 70px;\">");
		out.println("              Score");
		out.println("            </th>");
		out.println("          </tr>");

		for (int i = 0; i < questions.size(); i++) {
			out.println("          <tr>");
			out.println("            <td>");
			out.println("              <p style=\"font-weight: bold; padding: 0;\">Question:<p>");
			out.print(questions.get(i));
			out.println("              <p style=\"font-weight: bold; padding: 0;\">Answer:<p> ");
			out.print(answers.get(i));
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"rowNum");
			out.print(i);
			out.println("\" type=\"text\" name=\"row\">  ");
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"colNum");
			out.print(i);
			out.println("\" type=\"text\" name=\"col\">  ");
			out.println("            </td>");
			out.println("            <td>");
			out.println("              <input id=\"score");
			out.print(i);
			out.println("\" type=\"text\" name=\"score\">  ");
			out.println("            </td>");
		}

		out.println("        </table>");
		out.println("        <br/>");
		out.println(
				"      <a class=\"form\" style=\"width: 100px;\" href=\"http://plato.cs.virginia.edu/~bnh5et/HW/jeopardy.php\">Add More Q/A</a>");
		out.println(
				"      <input class=\"form\" type=\"submit\" name=\"submit\" value=\"Create Game\" style=\"width: 100px;\">");
		out.println("    </form>");
		out.println("  </div>");
		out.println("</center>");
		out.println("</body>");
		out.println("");
		out.println("</html>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get data from post
		row = request.getParameterValues("row");
		column = request.getParameterValues("col");
		score = request.getParameterValues("score");

		if (updated) {
			updateGame();
		} else {
			createGame();
		}

		// Calculate Max Rows and Max Cols
		maxrows = 0;
		maxcols = 0;
		for (int i = 0; i < row.length; i++) {
			int rowInt = Integer.parseInt(row[i]);
			if (rowInt > maxrows) {
				maxrows = rowInt;
			}
		}

		for (int i = 0; i < column.length; i++) {
			int colInt = Integer.parseInt(column[i]);
			if (colInt > maxcols) {
				maxcols = colInt;
			}
		}
		
		//Set Scores Array
		scores = new int[maxrows][maxcols];

		for (int i = 0; i < row.length; i++) {
			int rowNum = Integer.parseInt(row[i]) - 1;
			int colNum = Integer.parseInt(column[i]) - 1;
			int scoreNum = Integer.parseInt(score[i]);
			scores[rowNum][colNum] = scoreNum;
		}

		// Display the Jeopardy Grid
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printBoard(out);
	}

	public void printBoard(PrintWriter out) {
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Jeopardy</title>");
		out.println("");
		out.println("  <style>");
		out.println("    body {");
		out.println("      font-family: Verdana, Geneva, sans-serif;");
		out.println("      background-color: SlateGrey;");
		out.println("    }");
		out.println("    div {");
		out.println("      box-shadow: 0 0 5px;");
		out.println("      background-color: white;");
		out.println("      padding: 20px;");
		out.println("      margin: 40px;");
		out.println("      border-radius: 15px;");
		out.println("    }");
		out.println("    a:hover {");
		out.println("      background-color: yellow; ");
		out.println("    } ");
		out.println("    input {");
		out.println("      width: 100px;       ");
		out.println("    }  ");
		out.println("    #multChoice, #trueFalse, #shortAns { ");
		out.println("      display: none;");
		out.println("    } ");
		out.println("    button {");
		out.println("      border: 2px solid maroon;");
		out.println("      background-color: maroon;");
		out.println("      font-family: Verdana, Geneva, sans-serif;");
		out.println("      color: white;");
		out.println("      font-size: 15px;");
		out.println("      border-radius: 5px;");
		out.println("    } ");
		out.println("    button:hover {");
		out.println("      cursor: pointer;");
		out.println("      box-shadow: ");
		out.println("    }");
		out.println("    button:focus {");
		out.println("      outline: 0;");
		out.println("    }");
		out.println("    table, th, td {");
		out.println("      border: 1px solid black;  ");
		out.println("      color: white;");
		out.println("      background-color: maroon;");
		out.println("    }");
		out.println("    table {");
		out.println("      table-layout: fixed;");
		out.println("      border-collapse: collapse;");
		out.println("    }");
		out.println("    td {");
		out.println("      width: 100px;");
		out.println("      height: 60px;");
		out.println("      text-align: center;");
		out.println("    }");
		out.println("  </style>");
		out.println("");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("  <div id=\"mainpage\">");
		out.println("    <h1>Jeopardy!</h1>  ");
		out.println("    <h4>Briana Hart and Samantha Pitcher</h4>");
		out.println("        <table>");

		for (int i = 0; i < maxrows; i++) {
			out.println("            <tr>");
			for (int j = 0; j < maxcols; j++) {
				out.println("              <td>");
				if (scores[i][j] != 0) {
					out.println(scores[i][j]);
				}
				out.println("              </td>");
			}
			out.println("            </tr>");
		}
		out.println("        </table>");
		out.println("        ");
		out.println("    <br/>");
		out.println("    <br/>");
		out.println("  </div>");
		out.println("      <input type=\"reset\" name=\"reset\" value=\"Back\" onclick=\"window.history.back()\">");
		out.println("    </form>");
		out.println("  </div>");
		out.println("</center>");
		out.println("</body>");
		out.println("");
		out.println("</html>");
	}

	public void createGame() {
		File file = new File("/Users/brianahart/Documents/submission.txt");
		Scanner sc;
		String nl = "";

		try {
			sc = new Scanner(file);
			while (sc.hasNext()) {
				nl = sc.nextLine();
				String[] parsedLine = nl.split(";");
				if(Integer.parseInt(parsedLine[0]) >= maxGameID){
					System.out.println("Game ID: " + Integer.parseInt(parsedLine[0]));
					maxGameID = Integer.parseInt(parsedLine[0]) + 1;
					System.out.println("Max Game ID: " + maxGameID);
				}
			}
			sc.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if(maxGameID > 0){
			gameID = maxGameID;
		}
		
		try {
			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt", true);

			for (int i = 0; i < questions.size(); i++) {
				fw.write(gameID + ";" + user + ";");
				fw.write(questions.get(i) + ";" + answers.get(i) + ";" + row[i] + ";" + column[i] + ";" + score[i]
						+ "\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Could not write to file");
		}
	}

	public void updateGame() {
		try {
			File file = new File("/Users/brianahart/Documents/submission.txt");
			Scanner sc = new Scanner(file);
			String nl = "";	
			int count = 0;
			
			while(sc.hasNext()) {
				nl = sc.nextLine();
				if(nl.contains(";")){
					count++;
				}
			}
			sc.close();
			
			ArrayList<String> newFile =	new ArrayList<String>();		
			Scanner scanner = new Scanner(file);
			String line = "";
			int k = 0;
			
			for(int i = 0; i < count; i++){
				nl = scanner.nextLine();
				String[] parsedLine = nl.split(";");
				
				if(Integer.parseInt(parsedLine[0]) != gameID) {
					newFile.add(nl);
				} 
			}		
			scanner.close();
			
			for(int i = 0; i < questions.size(); i++) {
				newFile.add(gameID + ";" + user + ";" + questions.get(i) + ";" + answers.get(i) + ";" + row[i] + ";" + column[i] + ";" + score[i]);
			}

			FileWriter fw = new FileWriter("/Users/brianahart/Documents/submission.txt");
			
			for (int i = 0; i < count; i++){
				if(newFile.get(i) != null){
					fw.write(newFile.get(i));
					fw.write("\n");
				}
			}		
			fw.close();
			
		} catch (IOException e) {
			System.out.println("Could not write to file");
		}
	}

}

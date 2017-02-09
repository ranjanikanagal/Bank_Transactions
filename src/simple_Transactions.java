import java.util.Scanner;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class simple_Transactions {

	public static void main(String[] args) throws IOException {
		do {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter in command Deposit,Withdraw,Balance,Exit");
			String mychoice = scanner.next().toLowerCase();

			switch (mychoice) {
			case "deposit":
				double amount;
				boolean valid;
				do {
					System.out.println("\n enter . deposit amount\n");
					String input = scanner.next();
					boolean validnumber = verify_input(input);
					if (validnumber) {
						valid = false;
						amount = Double.parseDouble(input);
						deposit_amount(amount);
					} else {
						valid = true;
					}
				} while (valid);
				break;
			case "withdraw":
				double amount2;
				do {
					System.out.println("\n enter . withdaw amount\n");
					String input = scanner.next();
					boolean validnumber = verify_input(input);
					if (validnumber) {
						valid = false;
						amount2 = Double.parseDouble(input);
						withdraw_amount(amount2);
					} else {
						valid = true;
					}
				} while (valid);
				break;

			case "balance":
				System.out.println("Your Balance is");
				get_balance();
				break;
			case "exit":
				System.exit(0);
			}
		} while (true);
		// prompt for the user's name

		// get their input as a String

		// TODO Auto-generated method stub

	}

	public static boolean verify_input(String input) {
		boolean validnum;
		String[] splitter = input.split("\\.");
		splitter[0].length(); // Before Decimal Count
		int decimalLength = splitter[1].length(); // After Decimal Count

		if (decimalLength == 2) {
			validnum = true;
		} else {
			validnum = false;
		}
		return validnum;
	}

	public static void withdraw_amount(double amount) throws IOException {

		Document htmlFile = readFile();
		String htmltxt = "<tr><td>" + Double.toString(amount * -1);

		htmlFile.select("table#transactions").select("tbody").last().append(htmltxt);
		System.out.println(htmlFile.body().html());
		BufferedWriter htmlWriter = null;
		try {
			htmlWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(".\\src\\log.html")), "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		htmlWriter.write(htmlFile.outerHtml());
		htmlWriter.flush();
		htmlWriter.close();
	}

	public static void deposit_amount(double amount) throws IOException {

		Document htmlFile = readFile();
		String htmltxt = "<tr><td>" + Double.toString(amount);

		htmlFile.select("table#transactions").select("tbody").last().append(htmltxt);
		System.out.println(htmlFile.body().html());
		BufferedWriter htmlWriter = null;
		try {
			htmlWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(".\\src\\log.html")), "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		htmlWriter.write(htmlFile.outerHtml());
		htmlWriter.flush();
		htmlWriter.close();
	}

	public static Document readFile() {
		Document htmlFile = null;
		try {
			htmlFile = Jsoup.parse(new File(".\\src\\log.html"), "ISO-8859-1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlFile;
	}

	public static void get_balance() {
		Document htmlFile = readFile();
		Elements table = htmlFile.select("table#transactions");
		double balance = 0.0f;
		for (Element row : table.select("tr")) {
			Elements tds = row.select("td");
			String s = tds.text();

			try {
				if (!s.isEmpty()) {
					balance += Double.parseDouble(s.trim());
				}
			} catch (NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
		}
		System.out.println(balance);
	}
}

package singlethread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> mailto = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		LinkedList<String> remainingURLs = new LinkedList<String>();
		String address = "http://cs.lth.se/eda095/";

		remainingURLs.add(address);
		url.add(address);
		int counter = 0;

		System.out.println("Checking pages: ");
		while (!remainingURLs.isEmpty() && url.size() + mailto.size() < 1000) {
			counter++;
			 address = remainingURLs.poll();
			System.out.print(counter + ", ");
			if (counter % 10 == 0) {
				System.out.println("");
			}
			Document doc = null;
			Elements links = null;
			try {
				doc = Jsoup.connect(address).get();
				links = doc.select("a[href]");
			} catch (IOException | NullPointerException e) {
				
			}
			
			if(links != null){
			for (Element link : links) {

				String linkAbsHref = link.attr("abs:href");

				if (linkAbsHref.contains("mailto:")) {
					if (!mailto.contains(linkAbsHref)) {
						mailto.add(linkAbsHref);
					}

				} else {

					if (!url.contains(linkAbsHref)) {
						url.add(linkAbsHref);
						remainingURLs.add(linkAbsHref);

					}
				}

			}
			}
		}
		System.out.print("done!");
		System.out.println(" ");
		System.out.println("-------");
		System.out.println("List of addresses (" + mailto.size() + "): ");
		for (String mail : mailto) {
			System.out.println(mail);
		}

		System.out.println("-------");

		System.out.println("List of URLs (" + url.size() + "): ");
		for (String link : url) {
			System.out.println(link);

		}
		System.out.println("-------");
	}
}

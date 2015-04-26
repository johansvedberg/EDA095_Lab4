package multithread;

import java.util.ArrayList;
import java.util.LinkedList;

public class Spider {

	private ArrayList<String> mailto;
	private ArrayList<String> url;
	private LinkedList<String> remainingURLs;
	private static final int LIMIT = 2000;

	public Spider(String address) {
		mailto = new ArrayList<String>();
		url = new ArrayList<String>();
		remainingURLs = new LinkedList<String>();
		addURL(address);

	}

	public synchronized void addURL(String address) {
		if (!url.contains(url)) {
			url.add(address);
			remainingURLs.add(address);
			notifyAll();
		}
	}

	public synchronized void addMail(String mail) {

		if (!mailto.contains(mail)) {
			mailto.add(mail);
		}

	}

	public synchronized String getURL() {
		while (remainingURLs.isEmpty()) {
			try {

				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return remainingURLs.poll();

	}

	public synchronized boolean getNbrLinks() {
		return url.size() + mailto.size() < LIMIT;
	}

	public void result() {
		System.out.println("done!");
		System.out
				.println("Found " + (url.size() + mailto.size()) + " links: ");
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
	}

}

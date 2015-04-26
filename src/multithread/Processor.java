package multithread;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor implements Runnable {

	private Spider spider;

	public Processor(Spider spider) {

		this.spider = spider;
	}

	public void run() {
		while (spider.getNbrLinks()) {
			System.out.print(".");

			String address = spider.getURL();

			Document doc = null;
			Elements links = null;
			// Elements frames = null;

			try {
				doc = Jsoup.connect(address).get();
				links = doc.select("a[href]");
				// links = doc.getElementsByAttribute("href");
				// links = doc.getElementsByTag("a");
				// frames = doc.getElementsByTag("frame");

			} catch (IOException e) {

			}
			if (links != null) {
				for (Element link : links) {

					String linkAbsHref = link.attr("abs:href");

					if (!spider.getNbrLinks()) {
						break;
					}

					if (link != null) {
						if (linkAbsHref.contains("mailto:")) {
							spider.addMail(linkAbsHref);

						} else if (linkAbsHref.contains("http")) {
							spider.addURL(linkAbsHref);

						}
					}
				}
			}

			// for (Element frame : frames) {
			// String object = frame.attr("src");
			// System.out.println(object);
			// }

		}

	}

}

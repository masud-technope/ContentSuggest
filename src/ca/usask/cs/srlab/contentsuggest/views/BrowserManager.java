package ca.usask.cs.srlab.contentsuggest.views;

import org.eclipse.swt.browser.Browser;
import contentextract.ContentManager;
import result.Result;
import core.StaticData;

public class BrowserManager {

	String pageURL;
	Browser browser;
	Result currentResult;

	public BrowserManager(Result currentResult, Browser browser) {
		this.currentResult = currentResult;
		this.browser = browser;
		System.out.println(this.currentResult.resultURL);
	}

	public BrowserManager(Result currentResult, ContentBrowserView contentView) {
		this.currentResult = currentResult;
		this.browser = contentView.browser;
		System.out.println(this.currentResult.resultURL);
	}

	protected void showSuggestion() {
		// showing suggestion
		ContentManager cmanager = new ContentManager(
				this.currentResult.pageContent);
		String suggestedContent = cmanager.getSuggestedContent();
		if (suggestedContent.trim().isEmpty()) {
			// if failed to extract show the original content
			suggestedContent = currentResult.pageContent;
		}
		this.browser.setText(suggestedContent);
	}

	protected void showMainContent() {
		// showing main content of the page
		ContentManager cmanager = new ContentManager(
				this.currentResult.pageContent);
		String maincontent = cmanager.getMainContent();
		if (maincontent.trim().isEmpty()) {
			// if failed to extract show the original content
			maincontent = currentResult.pageContent;
		}
		this.browser.setText(maincontent);
	}
}

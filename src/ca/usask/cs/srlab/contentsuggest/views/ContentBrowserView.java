package ca.usask.cs.srlab.contentsuggest.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ContentBrowserView extends ViewPart {

	public static final String ID = "ca.usask.cs.srlab.contentsuggest.views.ContentBrowserView";
	public Browser browser;

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 5;
		glayout.marginHeight = 5;
		parent.setLayout(glayout);
		GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true);
		parent.setLayoutData(gdata);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		// gridData.verticalIndent=5;
		// adding browser
		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(gridData);
		browser.setUrl("http://www.google.ca");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}

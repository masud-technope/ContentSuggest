package ca.usask.cs.srlab.contentsuggest.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class ShowContentSuggestHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		try {
			String SEviewID = "ca.usask.cs.srlab.contentsuggest.views.ContentSuggestView";
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(SEviewID);
			// String
			// SCBviewID="ca.usask.ca.srlab.surfclipse.client.views.SurfClipseBrowser";
			// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SCBviewID);

			System.out.println("Content Suggest windows shown successfully");

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

}

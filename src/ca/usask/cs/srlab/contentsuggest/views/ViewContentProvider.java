package ca.usask.cs.srlab.contentsuggest.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import result.Result;
import result.SearchResultProvider;

public class ViewContentProvider implements IStructuredContentProvider {

	String searchQuery;

	public ViewContentProvider() {
		// default constructor
		this.searchQuery = new String("");
	}

	public ViewContentProvider(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		if (this.searchQuery.isEmpty())
			return new Result[] {};
		SearchResultProvider provider = new SearchResultProvider();
		return provider.provideResults(searchQuery);
		// return new String[] { "How to send an email in Java?",
		// "Cannot send email with Javamail", "Easy step to send email in Java"
		// };
	}

}

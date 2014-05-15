package result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.naming.ldap.SortResponseControl;

import similarity.CosineSimilarityMeasure;

public class SearchResultProvider {
	public SearchResultProvider() {
		// default constructor
	}

	protected ArrayList<Result> updateRelevanceMeasures(
			ArrayList<Result> results, String searchQuery) {
		// code for updating the relevance measures
		for (Result result : results) {
			String title = result.title;
			String description = result.description;
			String pagecontent = title + " " + description;
			CosineSimilarityMeasure cosmeasure = new CosineSimilarityMeasure(
					searchQuery, pagecontent);
			double similarity = cosmeasure.get_cosine_similarity_score(true);
			result.relevance = similarity * 100;
		}
		return results;
	}

	public Result[] provideResults(String searchQuery) {
		if (searchQuery.isEmpty())
			return null;
		 //GoogleAPI gapi=new GoogleAPI();
		 //ArrayList<Result>
		// googleResults=gapi.find_Google_Results(searchQuery);
		BingAPI bapi = new BingAPI();
		ArrayList<Result> googleResults = bapi.find_Bing_Results(searchQuery);
		googleResults = updateRelevanceMeasures(googleResults, searchQuery);
		googleResults = sortSearchResults(googleResults);
		Result[] rescoll = new Result[googleResults.size()];
		return googleResults.toArray(rescoll);
	}

	protected ArrayList<Result> sortSearchResults(ArrayList<Result> results) {
		// sort the search results
		Collections.sort(results, new Comparator<Result>() {

			@Override
			public int compare(Result o1, Result o2) {
				// TODO Auto-generated method stub
				Double rel1 = o1.relevance;
				Double rel2 = o2.relevance;
				return rel2.compareTo(rel1);
			}
		});
		return results;
	}
}

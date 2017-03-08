/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Mihai
 */
public class YoutubeClient {

    private static long NUMBER_OF_VIDEOS_RETURNED = 30;

    static YouTube youtube;
    static String apiKey = "AIzaSyDES1yfPv94DchUzYnFmRgLXJfATDVrp6M";

    static {
        youtube = new YouTube.Builder (new NetHttpTransport (), new JacksonFactory (), new HttpRequestInitializer () {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName ("youtube-cmdline-search-sample").build ();
    }

    public static List<SearchResult> executeRequest(String queryTerm) {

        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.

            // Prompt the user to enter a query term.
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search ().list ("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setKey (apiKey);
            search.setQ (queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType ("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields ("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults (NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute ();
            List<SearchResult> searchResultList = searchResponse.getItems ();
            return searchResultList;
            /*   if (searchResultList != null) {
                prettyPrint (searchResultList.iterator (), queryTerm);
            }*/
        } catch (GoogleJsonResponseException e) {
            System.err.println ("There was a service error: " + e.getDetails ().getCode () + " : "
                    + e.getDetails ().getMessage ());
        } catch (IOException e) {
            System.err.println ("There was an IO error: " + e.getCause () + " : " + e.getMessage ());
        } catch (Throwable t) {
            t.printStackTrace ();
        }
        return null;
    }

    /*
    private static String getInputQuery() throws IOException {

        String inputQuery = "";

        System.out.print ("Please enter a search term: ");
        BufferedReader bReader = new BufferedReader (new InputStreamReader (System.in));
        inputQuery = bReader.readLine ();

        if (inputQuery.length () < 1) {
            // Use the string "YouTube Developers Live" as a default.
            inputQuery = "YouTube Developers Live";
        }
        return inputQuery;
    }
     */
    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println ("\n=============================================================");
        System.out.println (
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println ("=============================================================\n");

        if (!iteratorSearchResults.hasNext ()) {
            System.out.println (" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext ()) {

            SearchResult singleVideo = iteratorSearchResults.next ();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (singleVideo.getId ().getKind ().equals ("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet ().getThumbnails ().getDefault ();

                System.out.println (" Video Id" + singleVideo.getId ().getVideoId ());
                System.out.println (" Title: " + singleVideo.getSnippet ().getTitle ());
                System.out.println (" Thumbnail: " + thumbnail.getUrl ());
                System.out.println ("\n-------------------------------------------------------------\n");
            }
        }
    }

}

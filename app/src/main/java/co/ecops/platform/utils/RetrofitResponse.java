package co.ecops.platform.utils;

import java.util.List;

import co.ecops.platform.model.Post;

/**
 * Created by ZEID on 7/14/2015.
 */
public class RetrofitResponse {
    public List<Post> getResults() {
        return results;
    }

    public void setResults(List<Post> results) {
        this.results = results;
    }

    private List<Post> results;

    public Post getMessage() {
        return message;
    }

    public void setMessage(Post message) {
        this.message = message;
    }

    private Post message;

}

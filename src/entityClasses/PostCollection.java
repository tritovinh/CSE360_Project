package entityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******
 * <p> Title: PostCollection Class </p>
 * <p> Description: Stores posts and supports returning subsets (e.g., search results). </p>
 */
public class PostCollection {

	private List<Post> posts = new ArrayList<>();

	/** Adds a post to the collection. */
	public void add(Post post) {
		if (post != null) posts.add(post);
	}

	/** Returns all posts. */
	public List<Post> getAll() {
		return new ArrayList<>(posts);
	}

	/** Returns a subset of posts whose title or body contains the search term (case-insensitive). */
	public List<Post> getSubsetBySearch(String searchTerm) {
		if (searchTerm == null || searchTerm.trim().isEmpty())
			return getAll();
		String term = searchTerm.trim().toLowerCase();
		return posts.stream()
				.filter(p -> (p.getTitle() != null && p.getTitle().toLowerCase().contains(term))
						|| (p.getBody() != null && p.getBody().toLowerCase().contains(term)))
				.collect(Collectors.toList());
	}

	/** Returns the post with the given id, or null. */
	public Post getById(int id) {
		return posts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}

	/** Removes the post with the given id; returns true if removed. */
	public boolean removeById(int id) {
		return posts.removeIf(p -> p.getId() == id);
	}

	/** Replaces a post by id; returns true if found and updated. */
	public boolean update(Post post) {
		if (post == null) return false;
		for (int i = 0; i < posts.size(); i++) {
			if (posts.get(i).getId() == post.getId()) {
				posts.set(i, post);
				return true;
			}
		}
		return false;
	}
}

package entityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******
 * <p> Title: PostCollection Class </p>
 * <p> Description: Stores posts and supports returning subsets (e.g., search results). </p>
 */
public class PostCollection {
	
	/**********
	 * <p> Method: PostCollection() </p>
	 * 
	 * <p> Description: This default constructor is not used in this system. </p>
	 */
	public PostCollection() {
	}

	private List<Post> posts = new ArrayList<>();

	/*****
     * <p> Method: void add(Post post) </p>
     * 
     * <p> Description: Adds a post to the collection. </p>
     * 
     * @param post is what will be added to the collection
     * 
     */
	public void add(Post post) {
		if (post != null) posts.add(post);
	}

	
	/**********
	 * <p> Method: List getAll() </p>
	 * 
	 * <p> Description: Returns all posts. </p>
	 * 
	 * @return ArrayList of all posts
	 * 
	 */
	public List<Post> getAll() {
		return new ArrayList<>(posts);
	}
	

	/**********
	 * <p> Method: List getSubsetBySearch(String searchTerm) </p>
	 * 
	 * <p> Description: Returns a subset of posts whose title or body contains the search term (case-insensitive). </p>
	 * 
	 * @param searchTerm specifies the term that is being searched for
	 * 
	 * @return ArrayList of all posts with the searchTerm
	 * 
	 */
	public List<Post> getSubsetBySearch(String searchTerm) {
		if (searchTerm == null || searchTerm.trim().isEmpty())
			return getAll();
		String term = searchTerm.trim().toLowerCase();
		return posts.stream()
				.filter(p -> (p.getTitle() != null && p.getTitle().toLowerCase().contains(term))
						|| (p.getBody() != null && p.getBody().toLowerCase().contains(term)))
				.collect(Collectors.toList());
	}

	
	/**********
	 * <p> Method: Post getById(int id) </p>
	 * 
	 * <p> Description: Returns the post with the given id, or null. </p>
	 * 
	 * @param id specifies the id of the post
	 * 
	 * @return post that fits the id, or null
	 * 
	 */
	public Post getById(int id) {
		return posts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}

	
	/**********
	 * <p> Method: boolean removeById(int id) </p>
	 * 
	 * <p> Description: Removes the post with the given id. </p>
	 * 
	 * @param id specifies the id of the post
	 * 
	 * @return true if the post was removed, false if not
	 * 
	 */
	public boolean removeById(int id) {
		return posts.removeIf(p -> p.getId() == id);
	}

	
	/**********
	 * <p> Method: boolean update(Post post) </p>
	 * 
	 * <p> Description: Replaces a post by id; </p>
	 * 
	 * @param post that will be updated
	 * 
	 * @return true if found and updated
	 * 
	 */
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

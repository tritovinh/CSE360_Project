package entityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******
 * <p> Title: ReplyCollection Class </p>
 * <p> Description: Stores replies and supports returning subsets for specific posts. </p>
 */
public class ReplyCollection {
	
	/**********
	 * <p> Method: ReplyCollection() </p>
	 * 
	 * <p> Description: This default constructor is not used in this system. </p>
	 */
	public ReplyCollection() {
	}

	private List<Reply> replies = new ArrayList<>();

	/**********
	 * <p> Method: void add(Reply reply) </p>
	 * 
	 * <p> Description: Adds a reply to the collection. </p>
	 * 
	 * @param reply is the object added to the collection
	 * 
	 */
	public void add(Reply reply) {
		if (reply != null) replies.add(reply);
	}
	
	
	/**********
	 * <p> Method: List getAll() </p>
	 * 
	 * <p> Description: Returns all replies. </p>
	 * 
	 * @return ArrayList of all replies
	 * 
	 */
	public List<Reply> getAll() {
		return new ArrayList<>(replies);
	}

	
	/**********
	 * <p> Method: List getSubsetByPostId(int parentPostId) </p>
	 * 
	 * <p> Description: Returns the subset of replies for the given parent post id. </p>
	 * 
	 * @param parentPostId specifies the post the replies are attached to
	 * 
	 * @return ArrayList of all replies attached to parent post
	 * 
	 */
	public List<Reply> getSubsetByPostId(int parentPostId) {
		return replies.stream()
				.filter(r -> r.getParentPostId() == parentPostId)
				.collect(Collectors.toList());
	}

	
	/**********
	 * <p> Method: Reply getById(int id) </p>
	 * 
	 * <p> Description: Returns the reply with the given id, or null. </p>
	 * 
	 * @param id specifies the id of the reply
	 * 
	 * @return reply that fits the id, or null
	 * 
	 */
	public Reply getById(int id) {
		return replies.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
	}

	
	/**********
	 * <p> Method: boolean removeById(int id) </p>
	 * 
	 * <p> Description: Removes the reply with the given id. </p>
	 * 
	 * @param id specifies the id of the reply
	 * 
	 * @return true if the reply was removed, false if not
	 * 
	 */
	public boolean removeById(int id) {
		return replies.removeIf(r -> r.getId() == id);
	}

	
	/**********
	 * <p> Method: void removeByParentPostId(int parentPostId) </p>
	 * 
	 * <p> Description: Removes all replies for a given parent post id. </p>
	 * 
	 * @param parentPostId specifies the id of the parent post
	 * 
	 */
	public void removeByParentPostId(int parentPostId) {
		replies.removeIf(r -> r.getParentPostId() == parentPostId);
	}

	
	/**********
	 * <p> Method: boolean update(Reply reply) </p>
	 * 
	 * <p> Description: Replaces a reply by id; </p>
	 * 
	 * @param reply that will be updated
	 * 
	 * @return true if found and updated
	 * 
	 */
	public boolean update(Reply reply) {
		if (reply == null) return false;
		for (int i = 0; i < replies.size(); i++) {
			if (replies.get(i).getId() == reply.getId()) {
				replies.set(i, reply);
				return true;
			}
		}
		return false;
	}
}

package entityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******
 * <p> Title: ReplyCollection Class </p>
 * <p> Description: Stores replies and supports returning subsets for specific posts. </p>
 */
public class ReplyCollection {

	private List<Reply> replies = new ArrayList<>();

	/** Adds a reply to the collection. */
	public void add(Reply reply) {
		if (reply != null) replies.add(reply);
	}

	/** Returns all replies. */
	public List<Reply> getAll() {
		return new ArrayList<>(replies);
	}

	/** Returns the subset of replies for the given parent post id. */
	public List<Reply> getSubsetByPostId(int parentPostId) {
		return replies.stream()
				.filter(r -> r.getParentPostId() == parentPostId)
				.collect(Collectors.toList());
	}

	/** Returns the reply with the given id, or null. */
	public Reply getById(int id) {
		return replies.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
	}

	/** Removes the reply with the given id; returns true if removed. */
	public boolean removeById(int id) {
		return replies.removeIf(r -> r.getId() == id);
	}

	/** Removes all replies for a given parent post id. */
	public void removeByParentPostId(int parentPostId) {
		replies.removeIf(r -> r.getParentPostId() == parentPostId);
	}

	/** Replaces a reply by id; returns true if found and updated. */
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

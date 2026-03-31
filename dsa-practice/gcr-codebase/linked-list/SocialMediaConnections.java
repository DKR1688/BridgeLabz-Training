package LinkedList;

public class SocialMediaConnections {
	public static void main(String[] args) {

		SocialMedia sm = new SocialMedia();

		// Adding users
		sm.addUser(1, "Deepak", 21);
		sm.addUser(2, "Aman", 22);
		sm.addUser(3, "Riya", 20);
		sm.addUser(4, "Neha", 23);

		// Adding friend connections
		sm.addFriend(1, 2);
		sm.addFriend(1, 3);
		sm.addFriend(2, 3);
		sm.addFriend(3, 4);

		// Display friends
		sm.displayFriends(1);

		// Mutual friends
		sm.findMutualFriends(1, 2);

		// Search user
		sm.searchUser("Riya");

		// Count friends
		sm.countFriends();

		// Remove friend
		sm.removeFriend(1, 3);
		sm.displayFriends(1);
	}
}

//Node for Friend List (stores friend user IDs)
class FriendNode {
	int friendId;
	FriendNode next;

	FriendNode(int friendId) {
		this.friendId = friendId;
		this.next = null;
	}
}

//Node for User List
class UserNode {
	int userId;
	String name;
	int age;
	FriendNode friendHead; // Head of friend's linked list
	UserNode next;

	UserNode(int userId, String name, int age) {
		this.userId = userId;
		this.name = name;
		this.age = age;
		this.friendHead = null;
		this.next = null;
	}
}

//Main class to manage social media connections
class SocialMedia {

	private UserNode head = null;

	// Add a new user
	public void addUser(int id, String name, int age) {
		UserNode newUser = new UserNode(id, name, age);

		if (head == null) {
			head = newUser;
		} else {
			UserNode temp = head;
			while (temp.next != null) {
				temp = temp.next;
			}
			temp.next = newUser;
		}
		System.out.println(" User added: " + name);
	}

	// Find user by ID
	private UserNode findUserById(int id) {
		UserNode temp = head;
		while (temp != null) {
			if (temp.userId == id)
				return temp;
			temp = temp.next;
		}
		return null;
	}

	// Add friend connection (bidirectional)
	public void addFriend(int id1, int id2) {
		UserNode user1 = findUserById(id1);
		UserNode user2 = findUserById(id2);

		if (user1 == null || user2 == null) {
			System.out.println(" One or both users not found.");
			return;
		}

		addFriendToList(user1, id2);
		addFriendToList(user2, id1);

		System.out.println(" Friend connection added between " + user1.name + " and " + user2.name);
	}

	// Helper method to add friend ID
	private void addFriendToList(UserNode user, int friendId) {
		FriendNode newFriend = new FriendNode(friendId);

		if (user.friendHead == null) {
			user.friendHead = newFriend;
		} else {
			FriendNode temp = user.friendHead;
			while (temp.next != null) {
				temp = temp.next;
			}
			temp.next = newFriend;
		}
	}

	// Remove friend connection
	public void removeFriend(int id1, int id2) {
		UserNode user1 = findUserById(id1);
		UserNode user2 = findUserById(id2);

		if (user1 == null || user2 == null) {
			System.out.println(" User not found.");
			return;
		}

		removeFriendFromList(user1, id2);
		removeFriendFromList(user2, id1);

		System.out.println(" Friend connection removed.");
	}

	// Helper method to remove friend
	private void removeFriendFromList(UserNode user, int friendId) {
		FriendNode curr = user.friendHead;
		FriendNode prev = null;

		while (curr != null) {
			if (curr.friendId == friendId) {
				if (prev == null)
					user.friendHead = curr.next;
				else
					prev.next = curr.next;
				return;
			}
			prev = curr;
			curr = curr.next;
		}
	}

	// 4. Display all friends of a user
	public void displayFriends(int userId) {
		UserNode user = findUserById(userId);

		if (user == null) {
			System.out.println(" User not found.");
			return;
		}

		System.out.println("\n Friends of " + user.name + ":");
		FriendNode temp = user.friendHead;

		if (temp == null) {
			System.out.println("No friends yet.");
			return;
		}

		while (temp != null) {
			UserNode friend = findUserById(temp.friendId);
			System.out.println("- " + friend.name);
			temp = temp.next;
		}
	}

	// 5. Find mutual friends
	public void findMutualFriends(int id1, int id2) {
		UserNode user1 = findUserById(id1);
		UserNode user2 = findUserById(id2);

		if (user1 == null || user2 == null) {
			System.out.println(" User not found.");
			return;
		}

		System.out.println("\n Mutual friends between " + user1.name + " and " + user2.name + ":");

		FriendNode f1 = user1.friendHead;
		boolean found = false;

		while (f1 != null) {
			FriendNode f2 = user2.friendHead;
			while (f2 != null) {
				if (f1.friendId == f2.friendId) {
					UserNode mutual = findUserById(f1.friendId);
					System.out.println("- " + mutual.name);
					found = true;
				}
				f2 = f2.next;
			}
			f1 = f1.next;
		}

		if (!found) {
			System.out.println("No mutual friends found.");
		}
	}

	// 6. Search user by ID or Name
	public void searchUser(String key) {
		UserNode temp = head;

		while (temp != null) {
			if (temp.name.equalsIgnoreCase(key) || String.valueOf(temp.userId).equals(key)) {

				System.out.println(" User Found:");
				System.out.println("ID: " + temp.userId + ", Name: " + temp.name + ", Age: " + temp.age);
				return;
			}
			temp = temp.next;
		}

		System.out.println(" User not found.");
	}

	// 7. Count friends for each user
	public void countFriends() {
		UserNode temp = head;

		System.out.println("\n Friend Count:");
		while (temp != null) {
			int count = 0;
			FriendNode f = temp.friendHead;

			while (f != null) {
				count++;
				f = f.next;
			}

			System.out.println(temp.name + " has " + count + " friends.");
			temp = temp.next;
		}
	}
}

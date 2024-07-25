package hu.ait.attendancetracker.data

data class Post(
    var uid: String = "",
    var author: String = "",
    var title: String = "",
    var date: String = "",
    var location: String = "",
    var guests: MutableList<String> = mutableListOf("alice","bob")
) {
    fun addGuest(guestName: String) {
        guests.add(guestName)
    }
}

data class PostWithId(
    val postId: String,
    val post: Post
)


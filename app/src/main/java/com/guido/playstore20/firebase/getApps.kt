package com.guido.playstore20.firebase

import com.google.firebase.firestore.FirebaseFirestore

internal fun getApps(postListCallback: (List<Map<String, Any>>) -> Unit){
    val db = FirebaseFirestore.getInstance()
    db.collection("apps").addSnapshotListener { snapshot, exception ->
        if(exception != null) return@addSnapshotListener
        val posts = mutableListOf<Map<String, Any>>()
        snapshot?.documents?.forEach { document ->
            val postData = document.data
            postData?.let { posts.add(it) }
        }
        postListCallback(posts)
    }
}
package com.guido.playstore20.firebase

import com.google.firebase.firestore.FirebaseFirestore

fun getAppsProfile(user: String, postListCallback: (List<Map<String, Any>>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("apps")
        .whereEqualTo("empresa", user) // Suponiendo que tienes un campo "username" en tus documentos de aplicación
        .addSnapshotListener { snapshot, exception ->
        if (exception != null) return@addSnapshotListener
        val posts = mutableListOf<Map<String, Any>>()
        snapshot?.documents?.forEach { document ->
            val postData = document.data
            postData?.let { posts.add(it) }
        }
        postListCallback(posts)
    }
}
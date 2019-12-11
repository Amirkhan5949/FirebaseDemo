package com.example.learnfireways;

public class Post {
   String Title;
   String author;
   String discription;
   int Like;

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "Title='" + Title + '\'' +
                ", author='" + author + '\'' +
                ", discription='" + discription + '\'' +
                '}';
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}

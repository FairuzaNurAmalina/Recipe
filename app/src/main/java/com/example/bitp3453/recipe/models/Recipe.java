package com.example.bitp3453.recipe.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {


    private String title;
    private String cover_photo;
    private String description;
    private String category;
    private String ingredient;
    private String username;
    private String steps;
    private String photo;
    private String video;
    private String ID;
    private String Recepiuid;
    private String Rating;
    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }



    public String getRecepiuid() {
        return Recepiuid;
    }

    public void setRecepiuid(String recepiuid) {
        Recepiuid = recepiuid;
    }

    public Recipe(String title, String description, String cover_photo, String username,
                  String category, String ingredient, String steps, String photo, String video) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.ingredient = ingredient;
        this.cover_photo = cover_photo;
        this.username = username;
        this.steps = steps;
        this.photo = photo;
        this.video = video;
    }

    public Recipe() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    protected Recipe(Parcel in) {

        description = in.readString();
        title = in.readString();
        cover_photo = in.readString();
        username = in.readString();
        ingredient = in.readString();
        category = in.readString();
        steps = in.readString();
        photo = in.readString();
        video = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe [size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_photo() {return cover_photo;}

    public void setCover_photo(String cover_photo) {this.cover_photo = cover_photo;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", cover_photo='" + cover_photo + '\'' +
                ", username='" + username + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", steps='" + steps + '\'' +
                ", photo='" + photo+ '\'' +
                ", video='" + video + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(cover_photo);
        dest.writeString(username);
        dest.writeString(category);
        dest.writeString(ingredient);
        dest.writeString(steps);
        dest.writeString(photo);
        dest.writeString(video);
    }
}

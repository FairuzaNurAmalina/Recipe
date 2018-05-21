package com.example.bitp3453.recipe.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccountSettings implements Parcelable {

    private String description;
    private String display_name;
    private long likes;
    private long posts;
    private String profile_photo;
    private String username;
    private String website;
    private String instagram;
    private String facebook;
    private String place;
    private String user_id;

    public UserAccountSettings(String description, String display_name, long likes,
                               long posts, String profile_photo, String username,
                               String website, String instagram, String facebook, String place, String user_id) {
        this.description = description;
        this.display_name = display_name;
        this.likes = likes;
        this.posts = posts;
        this.profile_photo = profile_photo;
        this.username = username;
        this.website = website;
        this.instagram = instagram;
        this.facebook = facebook;
        this.place = place;
        this.user_id = user_id;
    }

    public UserAccountSettings() {

    }

    protected UserAccountSettings(Parcel in) {
        description = in.readString();
        display_name = in.readString();
        likes = in.readLong();
        posts = in.readLong();
        profile_photo = in.readString();
        username = in.readString();
        website = in.readString();
        instagram = in.readString();
        facebook = in.readString();
        place = in.readString();
        user_id = in.readString();
    }

    public static final Creator<UserAccountSettings> CREATOR = new Creator<UserAccountSettings>() {
        @Override
        public UserAccountSettings createFromParcel(Parcel in) {
            return new UserAccountSettings(in);
        }

        @Override
        public UserAccountSettings[] newArray(int size) {
            return new UserAccountSettings[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }


    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "description='" + description + '\'' +
                ", display_name='" + display_name + '\'' +
                ", likes=" + likes +
                ", posts=" + posts +
                ", profile_photo='" + profile_photo + '\'' +
                ", username='" + username + '\'' +
                ", website='" + website + '\'' +
                ", instagram='" + instagram + '\'' +
                ", facebook='" + facebook + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(display_name);
        dest.writeLong(likes);
        dest.writeLong(posts);
        dest.writeString(profile_photo);
        dest.writeString(username);
        dest.writeString(website);
        dest.writeString(instagram);
        dest.writeString(facebook);
        dest.writeString(place);
        dest.writeString(user_id);
    }
}

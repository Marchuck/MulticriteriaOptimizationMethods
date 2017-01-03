package pl.marczak.view;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public class ProfileViewHolder {

    private final SimpleStringProperty profile1;
    private final SimpleStringProperty profile2;

    public ProfileViewHolder(List<String> list) {
        profile1 = new SimpleStringProperty(list.get(0));
        profile2 = new SimpleStringProperty(list.get(1));
    }

    public String getProfile1() {
        return profile1.get();
    }

    public SimpleStringProperty profile1Property() {
        return profile1;
    }

    public void setProfile1(String profile1) {
        this.profile1.set(profile1);
    }

    public String getProfile2() {
        return profile2.get();
    }

    public SimpleStringProperty profile2Property() {
        return profile2;
    }

    public void setProfile2(String profile2) {
        this.profile2.set(profile2);
    }
}

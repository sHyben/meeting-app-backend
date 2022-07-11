package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

//TODO Save image to our DB instead of link

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String type;
    private String title;
    private String text;
    private String answer;
    @Column(name="img_url")
    private String imgUrl;

    @ManyToMany(mappedBy = "activities")
    @ToString.Exclude
    Set<Meeting> meetings;

    public Activity(ActivityRequest request) {
        this.type = request.getType();
        this.title = request.getTitle();
        this.text = request.getText();
        this.answer = request.getAnswer();
        this.imgUrl = request.getImgUrl();
        this.meetings = new HashSet<>();
    }

    public void addMeeting(Meeting meeting) throws NotFoundException {
        if(meeting != null) {
            this.meetings.add(meeting);
        } else {
            throw new NotFoundException();
        }
    }

    public void removeMeeting(Meeting meeting) {
        if(meeting != null) {
            this.meetings.remove(meeting);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return id != null && Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.sample.doorservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name ="door_events")
public class DoorEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String eventType;
    public int employeeId;
    public Date serverTime;
    public Date localTime;
    public String doorUID = "";

    public boolean isValid() {
        return this.employeeId > 0 && localTime != null && eventType != null && isEventTypeValid(eventType.toUpperCase(Locale.ROOT)) && !doorUID.isEmpty();
    }//isValid

    public static boolean isEventTypeValid(String ev) {
        for (DoorEventType c : DoorEventType.values()) {
            if (c.name().equals(ev)) {
                return true;
            }
        }
        return false;
    }

}




package org.twspring.exercisevalidation_event.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.exercisevalidation_event.ApiResponse.ApiResponse;
import org.twspring.exercisevalidation_event.Model.Event;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/event_system")
public class EventController {
    ArrayList<Event> events = new ArrayList<>();

    //---------------------Get---------------------

    @GetMapping("/get/events")
    public ResponseEntity getEvents() {
        if (events.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("404","No events found"));
        }else {
            return ResponseEntity.status(200).body(events);
        }
    }

    @GetMapping("get/event/{id}")
    public ResponseEntity getEvent(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equals(id)){
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No event found with id: "+id));

    }
    //---------------------Post--------------------

    @PostMapping("/post/event")
       public ResponseEntity postEvent(@Valid @RequestBody Event event, Errors errors) {
        if (event.start_date==null||event.end_date==null) {
            return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid data, dates are null."));
        }
         if (event.start_date.isAfter(event.end_date)) {
            return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid data, event start date cannot be after end date."));

        }
        if (errors.hasErrors()) {
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        events.add(event);
        return ResponseEntity.status(200).body(new ApiResponse("200","Event added"));
    }

    //---------------------Put---------------------
    @PutMapping("/update/event/{id}")
    public ResponseEntity updateEvent(@PathVariable String id,@Valid @RequestBody Event event, Errors errors) {
        if (event.start_date==null||event.end_date==null) {
            return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid data, dates are null."));
        }
        if (event.start_date.isAfter(event.end_date)) {
            return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid data, event start date cannot be after end date."));

        }
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (Event e : events) {
            if (e.getId().equals(id)){
                events.set(events.indexOf(e), event);
                return ResponseEntity.status(200).body(new ApiResponse("200","Event updated")) ;
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No event found with id: "+id));
    }

    @PutMapping("/update/event/{id}/capacity/{new_capacity}")
    public ResponseEntity updateEventCapacity(@PathVariable String id,@PathVariable int new_capacity) {
        if (new_capacity<26){
            return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid capacity, must be grater than or equal to 26"));
        }
        for (Event e : events) {
            if (e.getId().equals(id)){
                e.setCapacity(new_capacity);
                return ResponseEntity.status(200).body(new ApiResponse("200","Event capacity updated"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No event found with id: "+id)) ;
    }
    //---------------------Delete------------------

    @DeleteMapping("/delete/event/{id}")
    public ResponseEntity deleteEvent(@PathVariable String id) {
        for (Event e : events) {
            if (e.getId().equals(id)){
                events.remove(e);
                return ResponseEntity.status(200).body(new ApiResponse("200","Event deleted"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No event found with id: "+id));
    }

}


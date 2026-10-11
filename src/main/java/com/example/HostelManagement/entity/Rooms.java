package com.example.HostelManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false,unique = true)
    private Long roomNo;

    @OneToMany(mappedBy = "room")
    private List<Student> students=new ArrayList<>();

    public Rooms(int floor, Long roomNo, int capacity) {
        this.floor = floor;
        this.roomNo = roomNo;
        this.capacity = capacity;
    }

    @Transient
    public boolean isFull(){
        return students.size()>=capacity;
    }

    @Transient
    public boolean isAccomodated(){
        return !students.isEmpty();
    }

    public void addStudent(Student student) {
        if (isFull()) {
            throw new IllegalStateException("Room " + roomNo + " is full");
        }
        students.add(student);
        student.setRoom(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setRoom(null);
    }
}

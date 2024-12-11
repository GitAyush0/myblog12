package com.blogapp12.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(name = "message" ,unique = true )
    private String message;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

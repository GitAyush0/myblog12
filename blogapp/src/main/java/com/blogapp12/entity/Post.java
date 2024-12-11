package com.blogapp12.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @Column(name = "description" ,unique = true )
    private String description;
    private String content;

    @OneToMany(mappedBy = "post" ,orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();


}

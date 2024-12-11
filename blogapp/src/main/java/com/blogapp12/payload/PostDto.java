package com.blogapp12.payload;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;


@Data
public class PostDto {




    @Id
    private long id;

    @NotEmpty
    @Size( min = 2,message = "Title should be atlest 3 characters")
    private String title;


    private String description;
    private String content;

}

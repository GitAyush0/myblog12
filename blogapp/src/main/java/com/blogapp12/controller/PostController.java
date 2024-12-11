package com.blogapp12.controller;

import com.blogapp12.entity.Post;
import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.service.PostService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto , BindingResult bindingResult){

        if(bindingResult.hasErrors()){            //has errors returns boolean
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
//        if(postDto.getDescription().length()<5) {
//            return new ResponseEntity<>("Description must be of minimum 5 character", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
            PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//    http://localhost:8080/api/posts/2
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deletePost( @PathVariable long id){

        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
    }

         //http://localhost:8080/api/posts?id=1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto , HttpStatus.OK);

    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=10
    @GetMapping
    public ResponseEntity<ListPostDto> fetchAllPosts(
            @RequestParam(name="pageNo" ,defaultValue = "1",required = false) int pageNo,
            @RequestParam(name="pageSize" ,defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="sortBy" , defaultValue ="id" , required = false) String sortBy,
            @RequestParam(name="sortDir" , defaultValue ="asc" , required = false) String sortDir

    ){     //get all post using stream api
        ListPostDto listPostDtos = postService.fetchAllPost(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(listPostDtos,HttpStatus.OK);


    }
}

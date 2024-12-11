package com.blogapp12.service;

import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public PostDto createPost(PostDto postDto);

    void deletePost(long id);

    ListPostDto fetchAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto getPostById(long id);
}

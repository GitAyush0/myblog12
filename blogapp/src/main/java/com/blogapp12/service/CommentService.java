package com.blogapp12.service;

import com.blogapp12.payload.CommentDto;
import com.blogapp12.payload.PostWithCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createcomment(CommentDto commentDto, long postId);

    PostWithCommentDto getAllCommentByPostId(long postId);
}

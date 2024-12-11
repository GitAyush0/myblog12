package com.blogapp12.service;

import com.blogapp12.entity.Comment;
import com.blogapp12.entity.Post;
import com.blogapp12.exception.ResourceNotFound;
import com.blogapp12.payload.CommentDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.payload.PostWithCommentDto;
import com.blogapp12.repository.CommentRepository;
import com.blogapp12.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    @Override
    public CommentDto createcomment(CommentDto commentDto,long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    @Override
    public PostWithCommentDto getAllCommentByPostId(long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id :" +id)
        );

        PostDto dto = new PostDto();
        PostDto dto1 = mapToDto(post);
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());


        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());

        PostWithCommentDto postWithCommentDto = new PostWithCommentDto();

        postWithCommentDto.setCommentDto(dtos);
        postWithCommentDto.setPost(dto1);

        return postWithCommentDto;
    }


    Comment mapToEntity(CommentDto dto){
        Comment comment = modelMapper.map(dto, Comment.class);
        return comment;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    PostDto mapToDto(Post post){
        PostDto dto1 = modelMapper.map(post, PostDto.class);

        return dto1;
    }

}


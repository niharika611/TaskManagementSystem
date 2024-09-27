package com.codewithprojects.services.admin;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codewithprojects.dto.CommentDto;
import com.codewithprojects.dto.LikesRatingsDto;
import com.codewithprojects.dto.PostDto;
import com.codewithprojects.entities.Comment;
import com.codewithprojects.entities.CommentStatus;
import com.codewithprojects.entities.LikesRatings;
import com.codewithprojects.entities.Post;
import com.codewithprojects.entities.User;
import com.codewithprojects.repositories.CommentRepository;
import com.codewithprojects.repositories.CommentStatusRepository;
import com.codewithprojects.repositories.LikesRatingsRepository;
import com.codewithprojects.repositories.PostRepository;
import com.codewithprojects.repositories.UserRepository;
import com.codewithprojects.utils.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
private final UserRepository userRepository;
	
	private final PostRepository postRepository;
	
	private final CommentRepository commentRepository;
	
	private final JwtUtil jwtUtil;
	
	private final LikesRatingsRepository likesRatingsRepository;
	
	private final CommentStatusRepository commentStatusRepository;

	@Override
	public PostDto createPost(PostDto postDto) {
			Post post=new Post();
			post.setTitle(postDto.getTitle());
			post.setDescription(postDto.getDescription());
			post.setLiked(false);
			post.setRating((long) 0);
			post.setUserId(postDto.getUserId());
			post.setImage(postDto.getImage());
			Post savedPost = postRepository.save(post);
			List<User> users = userRepository.findAll();
			for (User u : users) {
		        LikesRatings likesRatings = new LikesRatings();
		        likesRatings.setPostId(post.getId()); 
		        likesRatings.setUserId(u.getId());   
		        likesRatings.setLiked(false);
		        likesRatings.setRating((long) 0);
		        likesRatingsRepository.save(likesRatings); 
		    }

			return savedPost.getPostDto();
	}

	@Override
	public List<PostDto> getAllPosts() {
	    return postRepository.findAll().stream()
	            .sorted(Comparator.comparing(Post::getId).reversed())
	            .map(Post::getPostDto)
	            .collect(Collectors.toList());
	}

	@Override
	public void likePost(Long postId, Long userId) {
	    postRepository.likePostById(postId);
	    LikesRatings likesRatings = likesRatingsRepository.findByPostIdAndUserId(postId, userId);
	    if (likesRatings != null) {
	        likesRatings.setLiked(true); 
	        likesRatingsRepository.save(likesRatings);
	    }
	}

	@Override
	public void ratePost(Long postId,Long rating,Long userId) {
		postRepository.ratePostById(postId,rating);
		LikesRatings likesRatings = likesRatingsRepository.findByPostIdAndUserId(postId, userId);
	    if (likesRatings != null) {
	        likesRatings.setRating(rating); 
	        likesRatingsRepository.save(likesRatings);
	    }
		
	}
	
	@Override
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		Optional<Post> optionalPost= postRepository.findById(id);
		if(optionalPost.isPresent()) {
			Post existingPost=optionalPost.get();
			existingPost.setTitle(postDto.getTitle());
			existingPost.setDescription(postDto.getDescription());
			existingPost.setImage(postDto.getImage());
			return postRepository.save(existingPost).getPostDto();
		}
		return null;
	}

	@Override
	public PostDto getPostById(Long id) {
		Optional<Post> optionalPost= postRepository.findById(id);
		return optionalPost.map(Post::getPostDto).orElse(null);
	}

	@Override
	public List<PostDto> searchPostByTitle(String name) {
		//List<Post> posts=postRepository.findAllByTitleContaining(name);
		List<Post> posts=postRepository.findAllByTitleContainingOrDescriptionContaining(name,name);
		System.out.println(posts.size());
		return posts.stream().map(Post::getPostDto).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto createComment(Long userId, Long postId, String content) {
		Optional<Post> optionalPost=postRepository.findById(postId);
		Optional<User> user=userRepository.findById(userId);
		if(optionalPost.isPresent() && user.isPresent()) {
			Comment comment =new Comment();
			comment.setCreatedAt(new Date());
			comment.setContent(content);
			comment.setPost(optionalPost.get());
			comment.setUser(user.get());
			CommentDto savedCommentDto= commentRepository.save(comment).getCommentDto();
			CommentStatus cs=new CommentStatus();
			cs.setContent(content);
			cs.setPostId(postId);
			cs.setCommentedUserId(userId);
			cs.setPostCreatedUserId(optionalPost.get().getUserId());
			cs.setCommentedUserName(user.get().getName());
			cs.setReadStatus(false);
			commentStatusRepository.save(cs);
			return savedCommentDto;
		}
		throw new EntityNotFoundException("User or Task not found");
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		return commentRepository.findAllByPostId(postId).stream()
				.sorted(Comparator.comparing(Comment::getId).reversed())
	            .map(Comment::getCommentDto).collect(Collectors.toList());
	}
	
	public List<LikesRatingsDto> getAllLikesRatings(){
		List<LikesRatings> list=likesRatingsRepository.findAll();
		return list.stream().map(LikesRatings::getLikesRatingsDto).collect(Collectors.toList());
	}
	
}

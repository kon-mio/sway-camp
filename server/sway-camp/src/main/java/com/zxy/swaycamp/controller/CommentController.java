package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.service.CommentReplyService;
import com.zxy.swaycamp.service.CommentService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentReplyService commentReplyService;
    public CommentController(CommentReplyService commentReplyService,
                             CommentService commentService){
        this.commentReplyService = commentReplyService;
        this.commentService = commentService;
    }


    /**
     * 发表评论
     * @param commentDTO 评论信息
     * @return 空
     */
    @LoginCheck
    @PostMapping("/upload")
    public SwayResult uploadComment(@RequestBody @Validated CommentDTO commentDTO){
        commentService.uploadComment(commentDTO);
        return SwayResult.success();
    }


    /**
     * 回复评论
     * @param replyDTO 评论信息
     * @return 空
     */
    @LoginCheck
    @PostMapping("/reply/upload")
    public SwayResult uploadReply(@RequestBody @Validated ReplyDTO replyDTO){
        commentReplyService.uploadReply(replyDTO);
        return SwayResult.success();
    }


    @GetMapping("/list")
    public SwayResult listComment(){
        return SwayResult.success(commentService.listComment(1,10));
    }

    @GetMapping("/reply/list")
    public SwayResult listReply(){
        return SwayResult.success(commentReplyService.listReplyPage(1, 10, 1));
    }
}


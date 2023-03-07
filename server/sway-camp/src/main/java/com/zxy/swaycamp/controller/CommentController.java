package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.enums.Action;
import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.domain.vo.comment.CommentVO;
import com.zxy.swaycamp.domain.vo.comment.ReplyVO;
import com.zxy.swaycamp.service.CommentReplyService;
import com.zxy.swaycamp.service.CommentService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@Validated
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
    @Log(title = "发表评论", action = Action.INSERT)
    @PostMapping("/upload")
    public SwayResult<CommentVO> uploadComment(@RequestBody @Validated CommentDTO commentDTO){
        return SwayResult.success(commentService.uploadComment(commentDTO));
    }


    /**
     * 回复评论
     * @param replyDTO 评论信息
     * @return 空
     */
    @LoginCheck
    @Log(title = "发表评论回复", action = Action.INSERT)
    @PostMapping("/reply/upload")
    public SwayResult<ReplyVO> uploadReply(@RequestBody @Validated ReplyDTO replyDTO){
        return SwayResult.success(commentReplyService.uploadReply(replyDTO));
    }

    /**
     * 分页查询评论
     * @param index 页码
     * @param size 大小
     * @param articleId 文章ID
     * @return 评论列表
     */
    @Log(title = "查询评论", action = Action.SELECT)
    @GetMapping("/list")
    public SwayResult<PageVO<CommentVO>> listComment(@RequestParam @NotNull Integer index,
                                                     @RequestParam @NotNull Integer size,
                                                     @RequestParam @NotNull Integer articleId){
        return SwayResult.success(commentService.listComment(index, size, articleId));
    }

    /**
     * 分页查询评论回复
     * @param index 页码
     * @param size 大小
     * @param commentId 评论ID
     * @return 评论列表
     */
    @Log(title = "查询评论回复", action = Action.SELECT)
    @GetMapping("/reply/list")
    public SwayResult<PageVO<ReplyVO>>  listReply(@RequestParam @NotNull Integer index,
                                                  @RequestParam @NotNull Integer size,
                                                  @RequestParam @NotNull Integer commentId){
        return SwayResult.success(commentReplyService.listReplyPage(index, size, commentId));
    }

    /**
     * 删除评论
     * @param commentId 评论ID
     * @return 空
     */
    @LoginCheck
    @Log(title = "删除评论", action = Action.DELETE)
    @PostMapping("/remove")
    public SwayResult removeComment(@RequestParam Integer commentId){
        commentService.removeComment(commentId);
        return SwayResult.success();
    }

    /**
     * 删除回复
     * @param replyId 回复ID
     * @return 空
     */
    @LoginCheck
    @Log(title = "删除评论回复", action = Action.DELETE)
    @PostMapping("/reply/remove")
    public SwayResult removeReply(@RequestParam Integer replyId){
        commentReplyService.removeReply(replyId);
        return SwayResult.success();
    }
}


package com.zxy.swaycamp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.domain.vo.comment.CommentVO;

import java.util.List;

/**
 * <p>
 * 文章评论表 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
public interface CommentService extends IService<Comment> {

    /**
     * 上传评论
     * @param commentDTO 评论信息
     * @return 新增评论
     */
    CommentVO uploadComment(CommentDTO commentDTO);


    /**
     * 分页查询评论
     * @param index 页码
     * @param size 分页大小
     * @param articleId 文章Id
     * @return 评论列表
     */
    PageVO<CommentVO> listComment(Integer index, Integer size, Integer articleId);
}

package com.xuan.vueblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName m_blog
 */
@TableName(value ="m_blog")
@Data
public class Blog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 
     */
    @NotBlank(message = "摘要不能为空")
    private String description;

    /**
     * 
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;

    /**
     * 
     */
    private Integer status;

    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
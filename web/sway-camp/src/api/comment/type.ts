/**请求参数 */

// 评论参数
export interface CommentDTO {
  articleId: number
  content: string
}
/**响应/接收参数 */

// 共同信息
export interface CommonInfo {
  id: number
  content: string
  date: string
  likeCount: number
  userId: number
  username: string
  avatar: string
}
// 评论信息
export interface Comment extends CommonInfo {
  id: number
  userId: number
  username: string
  avatar: string
  content: string
  likeCount: number
  date: string
  replies: Reply[]
  replyCount: number
}

// 回复信息
export interface Reply extends CommonInfo {
  id: number
  commentId: number
  content: string
  date: string
  likeCount: number
  userId: number
  username: string
  avatar: string
  replyUserId: number
  replyUsername: string
  replyAvatar: string
}

// 评论列表
export interface CommentPage {
  list: Comment[]
  total: number
}
